package com.banking.bankapp.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static Bank instance;
    private List<User> users;

    private Bank() {
        users = new ArrayList<>();
        users.add(new User("Sita", "sita", "sita123"));
        // Initialize with a default user for demonstration
        User defaultUser = new User("Nishchal", "user", "pass");
        defaultUser.addAccount(new SavingsAccount("Nishchal", "SAV123", 1000.0));
        defaultUser.addAccount(new CurrentAccount("Nishchal", "CUR456", 500.0));
        users.add(defaultUser);
    }

    public static synchronized Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
