package com.example.nepaltourism.desktopapp.models;
public abstract class User {
    protected String id;
    protected String name;
    protected String password;
    protected String contact;

    public User(String id, String name, String password, String contact) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}