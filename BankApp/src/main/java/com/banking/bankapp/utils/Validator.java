package com.banking.bankapp.utils;

// Placeholder for input validation methods
public class Validator {
    public static boolean isValidAmount(String amount) {
        try {
            double val = Double.parseDouble(amount);
            return val > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // public static boolean isValidUsername(String username) { ... }
    // public static boolean isValidPassword(String password) { ... }
}
