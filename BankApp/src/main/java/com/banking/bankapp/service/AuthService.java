package com.banking.bankapp.service;

import com.banking.bankapp.model.Bank;
import com.banking.bankapp.model.User;

public class AuthService {

    private Bank bank = Bank.getInstance(); // Access the bank's user data

    public User authenticate(String username, String password) {
        User user = bank.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Potentially add methods for user registration, password reset, etc.
}
