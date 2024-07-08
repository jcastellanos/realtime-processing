package com.jcastellanos.dto;

import com.jcastellanos.entity.Transaction;

public class Mapper {

    public static TransactionRecord TransactionToTransactionRecord(Transaction tx) {
        TransactionRecord record = new TransactionRecord();
        record.setTransactionId(tx.getTransactionId());
        record.setAmount(tx.getAmount());
        record.setLocation(tx.getLocation());
        record.setUser(tx.getUser());
        record.setTxtime(tx.getTxTime());
        record.setStatus(tx.getStatus());
        return record;
    }
}
