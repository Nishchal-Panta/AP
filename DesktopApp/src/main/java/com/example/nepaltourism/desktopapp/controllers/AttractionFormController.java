package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.models.Attraction;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

public class AttractionFormController {
    @FXML private TextField nameField, locationField, difficultyField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private Label nameLabel, typeLabel, locationLabel, difficultyLabel;
    private CSVDataManager dataManager = new CSVDataManager();

    @FXML
    private void initialize() {
        typeComboBox.getItems().addAll("Trek", "Heritage", "Adventure");
        updateLanguage();
    }

    private void updateLanguage() {
        nameLabel.setText(LanguageManager.getString("name"));
        typeLabel.setText(LanguageManager.getString("type"));
        locationLabel.setText(LanguageManager.getString("location"));
        difficultyLabel.setText(LanguageManager.getString("difficulty"));
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String type = typeComboBox.getValue();
        String location = locationField.getText();
        String difficultyText = difficultyField.getText();

        if (name.isEmpty() || type == null || location.isEmpty() || difficultyText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("fill_all_fields"));
            alert.showAndWait();
            return;
        }

        double difficulty;
        try {
            difficulty = Double.parseDouble(difficultyText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("invalid_difficulty"));
            alert.showAndWait();
            return;
        }

        List<Attraction> attractions = dataManager.loadAttractions();
        attractions.add(new Attraction(UUID.randomUUID().toString(), name, type, location, difficulty));
        dataManager.saveAttractions(attractions);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(LanguageManager.getString("attraction_added"));
        alert.showAndWait();

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}