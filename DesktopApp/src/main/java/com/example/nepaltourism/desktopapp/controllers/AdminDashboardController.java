package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.ChangeScene;
import com.example.nepaltourism.desktopapp.models.*;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminDashboardController {
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> userIdColumn, userNameColumn, userRoleColumn;
    @FXML private TableView<Attraction> attractionTable;
    @FXML private TableColumn<Attraction, String> attractionIdColumn, attractionNameColumn, attractionTypeColumn;
    @FXML private BarChart<String, Number> bookingChart;
    @FXML private Button addUserButton, addAttractionButton, exportButton, logoutButton;
    @FXML private Label welcomeLabel;

    private CSVDataManager dataManager = new CSVDataManager();
    private ChangeScene changeScene = new ChangeScene();

    @FXML
    private void initialize() {
        userIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        userNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        userRoleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue() instanceof Tourist ? "Tourist" : cellData.getValue() instanceof Guide ? "Guide" : "Admin"));
        attractionIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        attractionNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        attractionTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));

        updateLanguage();
        loadUsers();
        loadAttractions();
        loadBookingChart();
    }

    private void updateLanguage() {
        welcomeLabel.setText(LanguageManager.getString("welcome_admin"));
        addUserButton.setText(LanguageManager.getString("add_user"));
        addAttractionButton.setText(LanguageManager.getString("add_attraction"));
        exportButton.setText(LanguageManager.getString("export_report"));
        logoutButton.setText(LanguageManager.getString("logout"));
    }

    private void loadUsers() {
        userTable.setItems(FXCollections.observableArrayList(dataManager.loadUsers()));
    }

    private void loadAttractions() {
        attractionTable.setItems(FXCollections.observableArrayList(dataManager.loadAttractions()));
    }

    private void loadBookingChart() {
        List<Booking> bookings = dataManager.loadBookings();
        Map<String, Long> bookingCount = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getAttractionId, Collectors.counting()));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings by Attraction");
        bookingCount.forEach((attractionId, count) -> {
            String attractionName = dataManager.loadAttractions().stream()
                    .filter(a -> a.getId().equals(attractionId))
                    .findFirst()
                    .map(Attraction::getName)
                    .orElse("Unknown");
            series.getData().add(new XYChart.Data<>(attractionName, count));
        });
        bookingChart.getData().clear();
        bookingChart.getData().add(series);
    }

    @FXML
    private void handleAddUser() {
        Stage stage = new Stage();
        changeScene.switchScene(stage, "user-form.fxml", "Add User");
        stage.setOnHiding(e -> loadUsers());
    }

    @FXML
    private void handleAddAttraction() {
        Stage stage = new Stage();
        changeScene.switchScene(stage, "attraction-form.fxml", "Add Attraction");
        stage.setOnHiding(e -> loadAttractions());
    }

    @FXML
    private void handleExportReport() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("report.csv"))) {
            writer.writeNext(new String[]{"Attraction", "Booking Count"});
            List<Booking> bookings = dataManager.loadBookings();
            Map<String, Long> bookingCount = bookings.stream()
                    .collect(Collectors.groupingBy(Booking::getAttractionId, Collectors.counting()));
            for (Map.Entry<String, Long> entry : bookingCount.entrySet()) {
                String attractionName = dataManager.loadAttractions().stream()
                        .filter(a -> a.getId().equals(entry.getKey()))
                        .findFirst()
                        .map(Attraction::getName)
                        .orElse("Unknown");
                writer.writeNext(new String[]{attractionName, entry.getValue().toString()});
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(LanguageManager.getString("report_exported"));
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("export_error") + ": " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) userTable.getScene().getWindow();
        changeScene.switchScene(stage, "login.fxml", "Nepal Tourism - Login");
    }
}
