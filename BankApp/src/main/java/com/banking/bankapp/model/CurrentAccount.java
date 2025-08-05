package com.banking.bankapp.model;

public class CurrentAccount extends Account { // Extends Account
    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(String name, String number, double balance) {
        super(name, number, balance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > this.balance + OVERDRAFT_LIMIT) {
            throw new InsufficientBalanceException("Withdrawal failed: Overdraft limit exceeded!");
        } else {
            this.balance -= amount;
        }
    }

    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }

    public double getAvailableBalance() {
        return this.balance + OVERDRAFT_LIMIT;
    }
}
