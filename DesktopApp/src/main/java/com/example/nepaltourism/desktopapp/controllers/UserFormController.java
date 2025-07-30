package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.models.*;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

public class UserFormController {
    @FXML private TextField nameField, contactField, extraField, passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label nameLabel, roleLabel, contactLabel, extraLabel, passwordLabel;
    private CSVDataManager dataManager = new CSVDataManager();

    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll("Tourist", "Guide", "Admin");
        roleComboBox.setOnAction(e -> updateExtraLabel());
        updateLanguage();
    }

    private void updateLanguage() {
        nameLabel.setText(LanguageManager.getString("name"));
        roleLabel.setText(LanguageManager.getString("role"));
        contactLabel.setText(LanguageManager.getString("contact"));
        passwordLabel.setText(LanguageManager.getString("password"));
        updateExtraLabel();
    }

    private void updateExtraLabel() {
        String role = roleComboBox.getValue();
        if (role != null && role.equals("Tourist")) {
            extraLabel.setText(LanguageManager.getString("nationality_emergency"));
        } else if (role != null && role.equals("Guide")) {
            extraLabel.setText(LanguageManager.getString("languages_experience"));
        } else {
            extraLabel.setText("");
        }
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String role = roleComboBox.getValue();
        String contact = contactField.getText();
        String extra = extraField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || role == null || password.isEmpty() || (!role.equals("Admin") && (contact.isEmpty() || extra.isEmpty()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("fill_all_fields"));
            alert.showAndWait();
            return;
        }

        List<User> users = dataManager.loadUsers();
        String id = UUID.randomUUID().toString();
        switch (role) {
            case "Tourist":
                String[] touristExtra = extra.split(";");
                if (touristExtra.length != 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(LanguageManager.getString("invalid_extra"));
                    alert.showAndWait();
                    return;
                }
                users.add(new Tourist(id, name, password, contact, touristExtra[0], touristExtra[1]));
                break;
            case "Guide":
                String[] guideExtra = extra.split(";");
                if (guideExtra.length != 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(LanguageManager.getString("invalid_extra"));
                    alert.showAndWait();
                    return;
                }
                users.add(new Guide(id, name, password, contact, guideExtra[0], guideExtra[1]));
                break;
            case "Admin":
                users.add(new Admin(id, name, password));
                break;
        }
        dataManager.saveUsers(users);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(LanguageManager.getString("user_added"));
        alert.showAndWait();

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}