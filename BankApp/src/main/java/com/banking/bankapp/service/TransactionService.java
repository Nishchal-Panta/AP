package com.banking.bankapp.service;

import com.banking.bankapp.model.Account;
import com.banking.bankapp.model.InsufficientBalanceException;
import com.banking.bankapp.model.SavingsAccount;
import com.banking.bankapp.model.User;

public class TransactionService {

    public void deposit(Account account, double amount) {
        account.deposit(amount);
        // In a real app, you'd also record this transaction
    }

    public void withdraw(Account account, double amount) throws InsufficientBalanceException {
        account.withdraw(amount);
        // In a real app, you'd also record this transaction
    }

    public boolean addInterestToSavingsAccounts(User user) {
        boolean interestAdded = false;
        for (Account account : user.getAccounts()) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).addInterest();
                interestAdded = true;
                // In a real app, you'd also record this transaction
            }
        }
        return interestAdded;
    }
}
