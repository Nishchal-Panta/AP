package com.banking.bankapp.model;

public abstract class Account { // Renamed from BankAccount
    private String accountHolderName;
    private String accountNumber;
    protected double balance;

    public Account(String name, String number, double balance) {
        this.accountHolderName = name;
        this.accountNumber = number;
        this.balance = balance;
    }

    public abstract void withdraw(double amount) throws InsufficientBalanceException;

    public void deposit(double amount) {
        if (amount > 0.0) {
            this.balance += amount;
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    public String getAccountDetails() {
        return String.format("Account Holder: %s\nAccount Number: %s\nBalance: %.2f",
                accountHolderName, accountNumber, balance);
    }
}
