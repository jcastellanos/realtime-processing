package com.jcastellanos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcastellanos.domain.Summary;
import com.jcastellanos.domain.TransactionRecord;
import com.jcastellanos.domain.TransactionService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class TransactionServiceKafkaImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceKafkaImpl.class);

    private final KafkaReceiver<String,String> kafkaReceiver;
    private final Sinks.Many<Summary> sink;
    private final Summary summary;

    public TransactionServiceKafkaImpl(final KafkaReceiver<String,String> kafkaReceiver) {
        this.summary = new Summary();
        this.kafkaReceiver = kafkaReceiver;
        this.sink = Sinks.many().replay().all();
    }

    @PostConstruct
    public void init() {
        Flux<ReceiverRecord<String, String>> kafkaFlux = kafkaReceiver.receive();
        kafkaFlux
                .checkpoint("Messages are started being consumed")
                .log()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value)
                .map(data -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        return objectMapper.readValue(data, TransactionRecord.class);
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage());
                        return new TransactionRecord();
                    }
                })
                .map(data -> {
                    summary.cleanHourly();
                    summary.transactionsCountIncrement();
                    summary.totalAmountAdd(data.getAmount());
                    if("success".equals(data.getStatus())) {
                        summary.transactionsSuccessfulCountIncrement();
                    }
                    if("failure".equals(data.getStatus())) {
                        summary.transactionsFailedCountIncrement();
                    }
                    return summary;
                })
                .checkpoint("Messages are done consumed")
                .subscribe(sink::tryEmitNext);
    }

    @Override
    public Flux<Summary> getTransactions() {
        return sink.asFlux();
    }
}
