package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.ChangeScene;
import com.example.nepaltourism.desktopapp.models.*;
import com.example.nepaltourism.desktopapp.utils.FestivalManager;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import com.example.nepaltourism.desktopapp.utils.SafetyAlertManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TouristDashboardController {
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> bookingIdColumn, attractionColumn, guideColumn, dateColumn, statusColumn;
    @FXML private PieChart nationalityChart;
    @FXML private Button bookButton, emergencyButton, logoutButton;
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
        attractionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getAttractionName(cellData.getValue().getAttractionId())));
        guideColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(getGuideName(cellData.getValue().getGuideId())));
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        updateLanguage();
        loadBookings();
        loadNationalityChart();
    }

    private void updateLanguage() {
        welcomeLabel.setText(LanguageManager.getString("welcome_tourist"));
        bookButton.setText(LanguageManager.getString("book"));
        emergencyButton.setText(LanguageManager.getString("report_emergency"));
        logoutButton.setText(LanguageManager.getString("logout"));
    }

    private void loadBookings() {
        List<Booking> bookings = dataManager.loadBookings();
        bookingTable.setItems(FXCollections.observableArrayList(bookings.stream()
                .filter(b -> b.getTouristId().equals(currentUserId))
                .toList()));
    }

    private void loadNationalityChart() {
        List<User> users = dataManager.loadUsers();
        var nationalityCount = users.stream()
                .filter(u -> u instanceof Tourist)
                .map(u -> ((Tourist) u).getNationality())
                .collect(java.util.stream.Collectors.groupingBy(n -> n, java.util.stream.Collectors.counting()));

        nationalityChart.getData().clear();
        nationalityCount.forEach((nationality, count) ->
                nationalityChart.getData().add(new PieChart.Data(nationality, count)));
    }

    private String getAttractionName(String attractionId) {
        return dataManager.loadAttractions().stream()
                .filter(a -> a.getId().equals(attractionId))
                .findFirst()
                .map(Attraction::getName)
                .orElse("Unknown");
    }

    private String getGuideName(String guideId) {
        return dataManager.loadUsers().stream()
                .filter(u -> u instanceof Guide && u.getId().equals(guideId))
                .findFirst()
                .map(User::getName)
                .orElse("Unknown");
    }

    @FXML
    private void handleBook() {
        Stage stage = new Stage();
        changeScene.switchScene(stage, "booking-dialog.fxml", "New Booking");
        BookingDialogController controller = (BookingDialogController) stage.getScene().getRoot().getProperties().get("controller");
        controller.setTouristId(currentUserId);
        stage.setOnHiding(e -> loadBookings());
    }

    @FXML
    private void handleEmergency() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(LanguageManager.getString("select_booking"));
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        changeScene.switchScene(stage, "emergency-dialog.fxml", "Report Emergency");
        EmergencyDialogController controller = (EmergencyDialogController) stage.getScene().getRoot().getProperties().get("controller");
        controller.setBookingId(selected.getId());
        stage.setOnHiding(e -> loadBookings());
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) bookingTable.getScene().getWindow();
        changeScene.switchScene(stage, "login.fxml", "Nepal Tourism - Login");
    }
}