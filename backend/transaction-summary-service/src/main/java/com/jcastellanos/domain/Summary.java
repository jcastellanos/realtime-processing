package com.jcastellanos.domain;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Summary {
    private LocalTime time;
    private final AtomicInteger transactionsCount;
    private final AtomicInteger totalAmount;
    private final AtomicInteger transactionsFailedCount;
    private final AtomicInteger transactionsSuccessfulCount;

    public Summary() {
        time = LocalTime.now();
        transactionsCount = new AtomicInteger(0);
        totalAmount = new AtomicInteger(0);
        transactionsFailedCount = new AtomicInteger(0);
        transactionsSuccessfulCount = new AtomicInteger(0);
    }

    public void cleanHourly() {
        LocalTime now = LocalTime.now();
        if(now.getHour() > time.getHour()) {
            transactionsCount.set(0);
            totalAmount.set(0);
            transactionsFailedCount.set(0);
            transactionsSuccessfulCount.set(0);
            time = now;
        }
    }

    public void transactionsCountIncrement() {
        transactionsCount.incrementAndGet();
    }

    public void totalAmountAdd(int amount) {
        totalAmount.addAndGet(amount);
    }

    public void transactionsFailedCountIncrement() {
        transactionsFailedCount.incrementAndGet();
    }

    public void transactionsSuccessfulCountIncrement() {
        transactionsSuccessfulCount.incrementAndGet();
    }

    public String getTime() {
        return time.toString();
    }

    public int getTransactionsCount() {
        return transactionsCount.get();
    }

    public int getTotalAmount() {
        return totalAmount.get();
    }

    public int getTransactionsFailedCount() {
        return transactionsFailedCount.get();
    }

    public int getTransactionsSuccessfulCount() {
        return transactionsSuccessfulCount.get();
    }
}
