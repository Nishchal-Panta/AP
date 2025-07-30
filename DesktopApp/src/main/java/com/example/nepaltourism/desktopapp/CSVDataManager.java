package com.example.nepaltourism.desktopapp;

import com.example.nepaltourism.desktopapp.models.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVDataManager {
    private static final String USERS_FILE = "data/users.csv";
    private static final String ATTRACTIONS_FILE = "data/attractions.csv";
    private static final String BOOKINGS_FILE = "data/bookings.csv";
    private static final String EMERGENCY_REPORTS_FILE = "data/emergency_reports.csv";

    public <CSVReader> List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(USERS_FILE))) {
            reader.readNext(); // Skip header
            for (String[] row : reader) {
                String role = row[0];
                switch (role) {
                    case "Tourist":
                        users.add(new Tourist(row[1], row[2], row[3], row[4], row[5]));
                        break;
                    case "Guide":
                        users.add(new Guide(row[1], row[2], row[3], row[4], row[5]));
                        break;
                    case "Admin":
                        users.add(new Admin(row[1], row[2], row[3]));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUsers(List<User> users) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(USERS_FILE))) {
            writer.writeNext(new String[]{"Role", "ID", "Name", "Password", "Contact", "Extra"});
            for (User user : users) {
                if (user instanceof Tourist) {
                    Tourist t = (Tourist) user;
                    writer.writeNext(new String[]{"Tourist", t.getId(), t.getName(), t.getPassword(), t.getContact(), t.getNationality() + ";" + t.getEmergencyContact()});
                } else if (user instanceof Guide) {
                    Guide g = (Guide) user;
                    writer.writeNext(new String[]{"Guide", g.getId(), g.getName(), g.getPassword(), g.getContact(), g.getLanguages() + ";" + g.getExperience()});
                } else if (user instanceof Admin) {
                    Admin a = (Admin) user;
                    writer.writeNext(new String[]{"Admin", a.getId(), a.getName(), a.getPassword(), "", ""});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Attraction> loadAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(ATTRACTIONS_FILE))) {
            reader.readNext(); // Skip header
            for (String[] row : reader) {
                attractions.add(new Attraction(row[0], row[1], row[2], row[3], Double.parseDouble(row[4])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attractions;
    }

    public void saveAttractions(List<Attraction> attractions) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(ATTRACTIONS_FILE))) {
            writer.writeNext(new String[]{"ID", "Name", "Type", "Location", "Difficulty"});
            for (Attraction attr : attractions) {
                writer.writeNext(new String[]{attr.getId(), attr.getName(), attr.getType(), attr.getLocation(), String.valueOf(attr.getDifficulty())});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(BOOKINGS_FILE))) {
            reader.readNext(); // Skip header
            for (String[] row : reader) {
                bookings.add(new Booking(row[0], row[1], row[2], row[3], row[4], row[5]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void saveBookings(List<Booking> bookings) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(BOOKINGS_FILE))) {
            writer.writeNext(new String[]{"ID", "TouristID", "GuideID", "AttractionID", "Date", "Status"});
            for (Booking booking : bookings) {
                writer.writeNext(new String[]{booking.getId(), booking.getTouristId(), booking.getGuideId(), booking.getAttractionId(), booking.getDate(), booking.getStatus()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<EmergencyReport> loadEmergencyReports() {
        List<EmergencyReport> reports = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(EMERGENCY_REPORTS_FILE))) {
            reader.readNext(); // Skip header
            for (String[] row : reader) {
                reports.add(new EmergencyReport(row[0], row[1], row[2], row[3]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    public void saveEmergencyReports(List<EmergencyReport> reports) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(EMERGENCY_REPORTS_FILE))) {
            writer.writeNext(new String[]{"ID", "BookingID", "Description", "Timestamp"});
            for (EmergencyReport report : reports) {
                writer.writeNext(new String[]{report.getId(), report.getBookingId(), report.getDescription(), report.getTimestamp()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}