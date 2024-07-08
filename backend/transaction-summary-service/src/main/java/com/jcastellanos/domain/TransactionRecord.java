package com.jcastellanos.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TransactionRecord {
    @JsonProperty("transaction_id")
    private String transactionId;
    private String user;
    private Integer amount = 0;
    private String status;
    private String txtime;
    private String location;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxtime() {
        return txtime;
    }

    public void setTxtime(String txtime) {
        this.txtime = txtime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}