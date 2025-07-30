package com.example.nepaltourism.desktopapp.models;
public class EmergencyReport {
    private String id;
    private String bookingId;
    private String description;
    private String timestamp;

    public EmergencyReport(String id, String bookingId, String description, String timestamp) {
        this.id = id;
        this.bookingId = bookingId;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
