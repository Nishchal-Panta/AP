package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.ChangeScene;
import com.example.nepaltourism.desktopapp.models.Tourist;
import com.example.nepaltourism.desktopapp.models.User;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

public class SignupController {
    @FXML private TextField nameField, contactField, nationalityField, emergencyContactField, passwordField;
    @FXML private Label nameLabel, contactLabel, nationalityLabel, emergencyContactLabel, passwordLabel;
    private CSVDataManager dataManager = new CSVDataManager();
    private ChangeScene changeScene = new ChangeScene();

    @FXML
    private void initialize() {
        updateLanguage();
    }

    private void updateLanguage() {
        nameLabel.setText(LanguageManager.getString("name"));
        contactLabel.setText(LanguageManager.getString("contact"));
        nationalityLabel.setText(LanguageManager.getString("nationality"));
        emergencyContactLabel.setText(LanguageManager.getString("emergency_contact"));
        passwordLabel.setText(LanguageManager.getString("password"));
    }

    @FXML
    private void handleSignup() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String nationality = nationalityField.getText();
        String emergencyContact = emergencyContactField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || contact.isEmpty() || nationality.isEmpty() || emergencyContact.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("fill_all_fields"));
            alert.showAndWait();
            return;
        }

        List<User> users = dataManager.loadUsers();
        String id = UUID.randomUUID().toString();
        users.add(new Tourist(id, name, password, contact, nationality, emergencyContact));
        dataManager.saveUsers(users);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(LanguageManager.getString("signup_success"));
        alert.showAndWait();

        Stage stage = (Stage) nameField.getScene().getWindow();
        changeScene.switchScene(stage, "login.fxml", "Nepal Tourism - Login");
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        changeScene.switchScene(stage, "login.fxml", "Nepal Tourism - Login");
    }
}
