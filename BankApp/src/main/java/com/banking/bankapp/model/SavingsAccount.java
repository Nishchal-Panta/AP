package com.banking.bankapp.model;

public class SavingsAccount extends Account { // Extends Account
    private static final double INTEREST_RATE = 0.05;

    public SavingsAccount(String name, String number, double balance) {
        super(name, number, balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > this.balance) {
            throw new InsufficientBalanceException("Withdrawal failed: Insufficient balance!");
        } else {
            this.balance -= amount;
        }
    }

    public void addInterest() {
        this.balance += this.balance * INTEREST_RATE;
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
