package com.example.nepaltourism.desktopapp.controllers;
import com.example.nepaltourism.desktopapp.CSVDataManager;
import com.example.nepaltourism.desktopapp.models.*;
import com.example.nepaltourism.desktopapp.utils.FestivalManager;
import com.example.nepaltourism.desktopapp.utils.LanguageManager;
import com.example.nepaltourism.desktopapp.utils.SafetyAlertManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookingDialogController {
    @FXML private ComboBox<String> attractionComboBox, guideComboBox;
    @FXML private DatePicker datePicker;
    @FXML private Label attractionLabel, guideLabel, dateLabel, festivalLabel;
    private CSVDataManager dataManager = new CSVDataManager();
    private String touristId;

    public void setTouristId(String touristId) {
        this.touristId = touristId;
        initialize();
    }

    @FXML
    private void initialize() {
        List<Attraction> attractions = dataManager.loadAttractions();
        attractionComboBox.getItems().addAll(attractions.stream().map(Attraction::getName).toList());
        List<Guide> guides = dataManager.loadUsers().stream()
                .filter(u -> u instanceof Guide)
                .map(u -> (Guide) u)
                .toList();
        guideComboBox.getItems().addAll(guides.stream().map(Guide::getName).toList());

        updateLanguage();
    }

    private void updateLanguage() {
        attractionLabel.setText(LanguageManager.getString("attraction"));
        guideLabel.setText(LanguageManager.getString("guide"));
        dateLabel.setText(LanguageManager.getString("date"));
        festivalLabel.setText("");
    }

    @FXML
    private void handleBook() {
        String attractionName = attractionComboBox.getValue();
        String guideName = guideComboBox.getValue();
        LocalDate date = datePicker.getValue();

        if (attractionName == null || guideName == null || date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(LanguageManager.getString("fill_all_fields"));
            alert.showAndWait();
            return;
        }

        Attraction selectedAttraction = dataManager.loadAttractions().stream()
                .filter(a -> a.getName().equals(attractionName))
                .findFirst()
                .orElse(null);
        if (selectedAttraction == null) return;

        if (FestivalManager.isMonsoonSeason(date) && selectedAttraction.getDifficulty() > 3000) {
            SafetyAlertManager.showMonsoonWarning();
            return;
        }

        SafetyAlertManager.checkAltitudeSafety(selectedAttraction);

        String guideId = dataManager.loadUsers().stream()
                .filter(u -> u instanceof Guide && u.getName().equals(guideName))
                .findFirst()
                .map(User::getId)
                .orElse(null);
        if (guideId == null) return;

        List<Booking> bookings = dataManager.loadBookings();
        bookings.add(new Booking(UUID.randomUUID().toString(), touristId, guideId, selectedAttraction.getId(), date.toString(), "Pending"));
        dataManager.saveBookings(bookings);

        if (FestivalManager.isFestivalPeriod(date)) {
            festivalLabel.setText(LanguageManager.getString("festival_discount"));
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(LanguageManager.getString("booking_success"));
        alert.showAndWait();

        Stage stage = (Stage) attractionComboBox.getScene().getWindow();
        stage.close();
    }
}
