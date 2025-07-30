package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.ChangeScene;
import com.example.nepaltourism.desktopapp.models.*;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {
    @FXML private TextField idField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private Label idLabel, passwordLabel, languageLabel;

    private CSVDataManager dataManager = new CSVDataManager();
    private ChangeScene changeScene = new ChangeScene();

    @FXML
    private void initialize() {
        languageComboBox.getItems().addAll("English", "Nepali");
        languageComboBox.setValue("English");
        languageComboBox.setOnAction(e -> updateLanguage());
        updateLanguage();
    }

    private void updateLanguage() {
        String lang = languageComboBox.getValue().equals("English") ? "en" : "np";
        LanguageManager.setLanguage(lang);
        idLabel.setText(LanguageManager.getString("id"));
        passwordLabel.setText(LanguageManager.getString("password"));
        languageLabel.setText(LanguageManager.getString("language"));
    }

    @FXML
    private void handleLogin() {
        String id = idField.getText();
        String password = passwordField.getText();
        List<User> users = dataManager.loadUsers();

        for (User user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                Stage stage = (Stage) idField.getScene().getWindow();
                if (user instanceof Tourist) {
                    changeScene.switchScene(stage, "tourist-dashboard.fxml", "Tourist Dashboard");
                } else if (user instanceof Guide) {
                    changeScene.switchScene(stage, "guide-dashboard.fxml", "Guide Dashboard");
                } else if (user instanceof Admin) {
                    changeScene.switchScene(stage, "admin-dashboard.fxml", "Admin Dashboard");
                }
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(LanguageManager.getString("login_error"));
        alert.showAndWait();
    }

    @FXML
    private void handleSignup() {
        Stage stage = (Stage) idField.getScene().getWindow();
        changeScene.switchScene(stage, "signup.fxml", "Sign Up");
    }
}
