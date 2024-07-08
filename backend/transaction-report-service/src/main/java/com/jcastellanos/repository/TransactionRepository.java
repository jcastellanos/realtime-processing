package com.jcastellanos.repository;

import com.jcastellanos.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByStatus(String status);
    List<Transaction> findAllByUser(String user);
    List<Transaction> findAllByTransactionId(String transactionId);
}
