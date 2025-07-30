package com.example.nepaltourism.desktopapp.models;
public class Booking {
    private String id;
    private String touristId;
    private String guideId;
    private String attractionId;
    private String date;
    private String status;

    public Booking(String id, String touristId, String guideId, String attractionId, String date, String status) {
        this.id = id;
        this.touristId = touristId;
        this.guideId = guideId;
        this.attractionId = attractionId;
        this.date = date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTouristId() {
        return touristId;
    }

    public String getGuideId() {
        return guideId;
    }

    public String getAttractionId() {
        return attractionId;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}