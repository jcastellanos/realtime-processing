package com.jcastellanos.service;

import com.jcastellanos.dto.Mapper;
import com.jcastellanos.dto.TransactionRecord;
import com.jcastellanos.entity.Transaction;
import com.jcastellanos.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionRecord> findAll(String filter, String value) {
        List<Transaction> transactions = new ArrayList<>();
        if(filter.isEmpty()) {
            transactions = transactionRepository.findAll();
        }
        switch(filter) {
            case "user":
                transactions = transactionRepository.findAllByUser(value);
                break;
            case "status":
                transactions = transactionRepository.findAllByStatus(value);
                break;
            case "id":
                transactions = transactionRepository.findAllByTransactionId(value);
                break;
        }
        return transactions.stream()
                .map(Mapper::TransactionToTransactionRecord)
                .collect(Collectors.toList());
    }
}
