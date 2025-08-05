package com.banking.bankapp.model; // Added package declaration

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
