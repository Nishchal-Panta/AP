package com.banking.bankapp.controller;

import com.banking.bankapp.model.Account;
import com.banking.bankapp.model.CurrentAccount;
import com.banking.bankapp.model.SavingsAccount;
import com.banking.bankapp.model.User;
import com.banking.bankapp.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private TextArea accountDisplayArea;
    @FXML
    private ComboBox<String> accountSelector;

    private User currentUser;
    private TransactionService transactionService = new TransactionService();

    public void initData(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + currentUser.getName());
        updateAccountDisplay();
        updateAccountSelector();
    }

    @FXML
    private void updateAccountDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("============================================================\n");
        sb.append(String.format("  ACCOUNT SUMMARY FOR: %s\n", currentUser.getName().toUpperCase()));
        sb.append("============================================================\n\n");

        for (int i = 0; i < currentUser.getAccounts().size(); i++) {
            Account account = currentUser.getAccounts().get(i);
            String accountType = account.getClass().getSimpleName();

            sb.append(String.format("--- Account %d: %s ---\n", i + 1, accountType)).append("\n");
            sb.append(account.getAccountDetails()).append("\n");

            if (account instanceof CurrentAccount) {
                CurrentAccount ca = (CurrentAccount) account;
                sb.append(String.format("  Overdraft Limit: %.2f\n", ca.getOverdraftLimit()));
                sb.append(String.format("  Available Balance: %.2f\n", ca.getAvailableBalance()));
            } else if (account instanceof SavingsAccount) {
                SavingsAccount sa = (SavingsAccount) account;
                sb.append(String.format("  Interest Rate: %.1f%%\n", sa.getInterestRate() * 100));
            }

            sb.append("\n"); // Add an extra newline for better separation between accounts
        }

        accountDisplayArea.setText(sb.toString());
    }

    private void updateAccountSelector() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.getAccounts().size(); i++) {
            Account account = currentUser.getAccounts().get(i);
            String displayText = String.format("%d. %s (%s)",
                    i + 1,
                    account.getClass().getSimpleName(),
                    account.getAccountNumber()
            );
            items.add(displayText);
        }
        accountSelector.setItems(items);
        if (!items.isEmpty()) {
            accountSelector.getSelectionModel().selectFirst();
        }
    }

    private Account getSelectedAccount() {
        int selectedIndex = accountSelector.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            showAlert(Alert.AlertType.WARNING, "No Account Selected", "Please select an account from the dropdown.");
            return null;
        }
        return currentUser.getAccounts().get(selectedIndex);
    }

    @FXML
    private void handleDeposit() {
        openTransactionWindow("Deposit");
    }

    @FXML
    private void handleWithdraw() {
        openTransactionWindow("Withdraw");
    }

    private void openTransactionWindow(String operationType) {
        try {
            Account selectedAccount = getSelectedAccount();
            if (selectedAccount == null) return;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banking/bankapp/view/transaction.fxml"));
            Parent transactionRoot = loader.load();

            TransactionController transactionController = loader.getController();
            transactionController.initData(selectedAccount, operationType, this); // Pass reference to dashboard

            Stage transactionStage = new Stage();
            transactionStage.setTitle(operationType + " Funds");
            transactionStage.setScene(new Scene(transactionRoot));
            transactionStage.initModality(Modality.APPLICATION_MODAL); // Block parent window
            transactionStage.setResizable(false);
            transactionStage.showAndWait(); // Wait for transaction window to close
            updateAccountDisplay(); // Refresh dashboard after transaction
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open transaction window.");
        }
    }

    @FXML
    private void handleAddInterest() {
        boolean interestAdded = transactionService.addInterestToSavingsAccounts(currentUser);
        if (interestAdded) {
            showAlert(Alert.AlertType.INFORMATION, "Interest Added", "Interest has been added to your savings account(s).");
            updateAccountDisplay();
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Action", "No savings accounts found to add interest.");
        }
    }

    @FXML
    private void handleViewTransactions() {
        showAlert(Alert.AlertType.INFORMATION, "View Transactions", "This feature is not yet implemented.");
        // In a real app, you'd open a new window/scene to display transaction history
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
