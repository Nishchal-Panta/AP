package com.example.nepaltourism.desktopapp.models;
public class Attraction {
    private String id;
    private String name;
    private String type;
    private String location;
    private double difficulty;

    public Attraction(String id, String name, String type, String location, double difficulty) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public double getDifficulty() {
        return difficulty;
    }
}