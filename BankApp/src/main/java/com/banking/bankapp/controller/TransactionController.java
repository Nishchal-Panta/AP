package com.banking.bankapp.controller;

import com.banking.bankapp.model.Account;
import com.banking.bankapp.model.InsufficientBalanceException;
import com.banking.bankapp.service.TransactionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionController {

    @FXML
    private Label operationLabel;
    @FXML
    private TextField amountField;
    @FXML
    private Label currentBalanceLabel;

    private Account selectedAccount;
    private String operationType;
    private DashboardController dashboardController; // Reference to refresh dashboard
    private TransactionService transactionService = new TransactionService();

    public void initData(Account account, String type, DashboardController dashboardController) {
        this.selectedAccount = account;
        this.operationType = type;
        this.dashboardController = dashboardController;
        operationLabel.setText(type + " Funds for " + selectedAccount.getClass().getSimpleName() + " (" + selectedAccount.getAccountNumber() + ")");
        updateBalanceLabel();
    }

    private void updateBalanceLabel() {
        currentBalanceLabel.setText(String.format("Current Balance: %.2f", selectedAccount.getBalance()));
    }

    @FXML
    private void handleConfirm() {
        try {
            double amount = getAmountFromField();

            if (amount <= 0) {
                showAlert(Alert.AlertType.WARNING, "Invalid Amount", "Please enter a positive amount.");
                return;
            }

            if ("Deposit".equals(operationType)) {
                transactionService.deposit(selectedAccount, amount);
                showAlert(Alert.AlertType.INFORMATION, "Deposit Successful", String.format("Successfully deposited %.2f.", amount));
            } else if ("Withdraw".equals(operationType)) {
                transactionService.withdraw(selectedAccount, amount);
                showAlert(Alert.AlertType.INFORMATION, "Withdrawal Successful", String.format("Successfully withdrew %.2f.", amount));
            }

            closeWindow();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric amount.");
        } catch (InsufficientBalanceException ex) {
            showAlert(Alert.AlertType.ERROR, "Transaction Failed", ex.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private double getAmountFromField() throws NumberFormatException {
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            throw new NumberFormatException("Amount field is empty");
        }
        return Double.parseDouble(amountText);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }
}
