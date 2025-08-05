package com.banking.bankapp.model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String accountId;
    private String type; // e.g., "Deposit", "Withdrawal", "Interest"
    private double amount;
    private LocalDateTime timestamp;
    private double balanceAfter;

    public Transaction(String transactionId, String accountId, String type, double amount, double balanceAfter) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.balanceAfter = balanceAfter;
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getBalanceAfter() { return balanceAfter; }

    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f (Balance: %.2f)",
                timestamp.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                type, amount, balanceAfter);
    }
}
