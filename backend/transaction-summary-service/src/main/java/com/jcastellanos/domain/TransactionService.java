package com.jcastellanos.domain;

import reactor.core.publisher.Flux;

public interface TransactionService {
    Flux<Summary> getTransactions();
}
