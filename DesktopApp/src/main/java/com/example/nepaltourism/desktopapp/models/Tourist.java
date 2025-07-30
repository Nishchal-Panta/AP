package com.example.nepaltourism.desktopapp.models;
public class Tourist extends User {
    private String nationality;
    private String emergencyContact;

    public Tourist(String id, String name, String password, String contact, String nationality, String emergencyContact) {
        super(id, name, password, contact);
        this.nationality = nationality;
        this.emergencyContact = emergencyContact;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
