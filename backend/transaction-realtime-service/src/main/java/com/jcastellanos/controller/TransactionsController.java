package com.jcastellanos.controller;

import com.jcastellanos.domain.TransactionRecord;
import com.jcastellanos.domain.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path = "/realtime")
public class TransactionsController {

    private static final Logger log = LoggerFactory.getLogger(TransactionsController.class);

    private final TransactionService transactionService;

    public TransactionsController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<TransactionRecord> getEventsFlux() {
        return this.transactionService.getTransactions();
    }
}