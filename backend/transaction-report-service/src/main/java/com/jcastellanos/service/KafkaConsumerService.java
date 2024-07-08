package com.jcastellanos.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcastellanos.dto.TransactionRecord;
import com.jcastellanos.entity.Transaction;
import com.jcastellanos.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @KafkaListener(topics = "transactions", groupId = "report-service")
    public void consume(String message) {
        try {
            TransactionRecord record = objectMapper.readValue(message, TransactionRecord.class);
            Transaction tx = new Transaction();
            tx.setTransactionId(record.getTransactionId());
            tx.setAmount(record.getAmount());
            tx.setLocation(record.getLocation());
            tx.setStatus(record.getStatus());
            tx.setUser(record.getUser());
            tx.setTxTime(record.getTxtime());
            transactionRepository.save(tx);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
