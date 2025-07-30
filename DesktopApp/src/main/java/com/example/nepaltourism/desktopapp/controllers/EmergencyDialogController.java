package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.models.EmergencyReport;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EmergencyDialogController {
    @FXML private TextArea descriptionArea;
    @FXML private Label descriptionLabel;
    private CSVDataManager dataManager = new CSVDataManager();
    private String bookingId;

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
        initialize();
    }

    @FXML
    private void initialize() {
        updateLanguage();
    }

    private void updateLanguage() {
        descriptionLabel.setText(LanguageManager.getString("description"));
    }

    @FXML
    private void handleReport() {
        String description = descriptionArea.getText();
        if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("fill_all_fields"));
            alert.showAndWait();
            return;
        }

        List<EmergencyReport> reports = dataManager.loadEmergencyReports();
        reports.add(new EmergencyReport(UUID.randomUUID().toString(), bookingId, description, LocalDateTime.now().toString()));
        dataManager.saveEmergencyReports(reports);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(LanguageManager.getString("emergency_reported"));
        alert.showAndWait();

        Stage stage = (Stage) descriptionArea.getScene().getWindow();
        stage.close();
    }
}