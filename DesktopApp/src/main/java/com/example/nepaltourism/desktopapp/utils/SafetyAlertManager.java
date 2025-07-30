package com.example.nepaltourism.desktopapp.utils;
import com.example.nepaltourism.desktopapp.models.Attraction;
import javafx.scene.control.Alert;

public class SafetyAlertManager {
    public static void checkAltitudeSafety(Attraction attraction) {
        if (attraction.getDifficulty() > 3000) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Altitude Warning");
            alert.setHeaderText("High Altitude Trek Warning");
            alert.setContentText("This trek involves altitudes above 3,000m. Ensure you are prepared for altitude sickness risks.");
            alert.showAndWait();
        }
    }

    public static void showMonsoonWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Monsoon Season Warning");
        alert.setHeaderText("Booking Restriction");
        alert.setContentText("High-altitude treks are restricted during the monsoon season (June-August).");
        alert.showAndWait();
    }
}