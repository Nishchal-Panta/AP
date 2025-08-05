package com.banking.bankapp.model;

import java.util.ArrayList;
import java.util.List;

public class User { // Renamed from Customer
    private String name;
    private String username; // Added for login
    private String password; // Added for login
    private List<Account> accounts; // Changed to List for more flexibility

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account getAccount(int index) {
        if (index >= 0 && index < accounts.size()) {
            return accounts.get(index);
        }
        return null;
    }

    public int getAccountCount() {
        return accounts.size();
    }
}
