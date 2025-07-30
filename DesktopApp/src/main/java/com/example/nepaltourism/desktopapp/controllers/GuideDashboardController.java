package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.ChangeScene;
import com.example.nepaltourism.desktopapp.models.Attraction;
import com.example.nepaltourism.desktopapp.models.Booking;
import com.example.nepaltourism.desktopapp.models.Tourist;
import com.example.nepaltourism.desktopapp.models.User;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class GuideDashboardController {
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> bookingIdColumn, touristColumn, attractionColumn, dateColumn, statusColumn;
    @FXML private Button acceptButton, rejectButton, logoutButton;
    @FXML private Label welcomeLabel;

    private CSVDataManager dataManager = new CSVDataManager();
    private ChangeScene changeScene = new ChangeScene();
    private String currentUserId;

    public void setUserId(String userId) {
        this.currentUserId = userId;
        initialize();
    }

    @FXML
    private void initialize() {
        bookingIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        touristColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getTouristName(cellData.getValue().getTouristId())));
        attractionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getAttractionName(cellData.getValue().getAttractionId())));
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        updateLanguage();
        loadBookings();
    }

    private void updateLanguage() {
        welcomeLabel.setText(LanguageManager.getString("welcome_guide"));
        acceptButton.setText(LanguageManager.getString("accept"));
        rejectButton.setText(LanguageManager.getString("reject"));
        logoutButton.setText(LanguageManager.getString("logout"));
    }

    private void loadBookings() {
        List<Booking> bookings = dataManager.loadBookings();
        bookingTable.setItems(FXCollections.observableArrayList(bookings.stream()
                .filter(b -> b.getGuideId().equals(currentUserId))
                .toList()));
    }

    private String getTouristName(String touristId) {
        return dataManager.loadUsers().stream()
                .filter(u -> u instanceof Tourist && u.getId().equals(touristId))
                .findFirst()
                .map(User::getName)
                .orElse("Unknown");
    }

    private String getAttractionName(String attractionId) {
        return dataManager.loadAttractions().stream()
                .filter(a -> a.getId().equals(attractionId))
                .findFirst()
                .map(Attraction::getName)
                .orElse("Unknown");
    }

    @FXML
    private void handleAccept() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus("Accepted");
            dataManager.saveBookings(dataManager.loadBookings());
            loadBookings();
        }
    }

    @FXML
    private void handleReject() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus("Rejected");
            dataManager.saveBookings(dataManager.loadBookings());
            loadBookings();
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) bookingTable.getScene().getWindow();
        changeScene.switchScene(stage, "login.fxml", "Nepal Tourism - Login");
    }
}