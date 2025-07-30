package com.example.nepaltourism.desktopapp.models;

public class Guide extends User {
    private String languages;
    private String experience;

    public Guide(String id, String name, String password, String contact, String languages, String experience) {
        super(id, name, password, contact);
        this.languages = languages;
        this.experience = experience;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}