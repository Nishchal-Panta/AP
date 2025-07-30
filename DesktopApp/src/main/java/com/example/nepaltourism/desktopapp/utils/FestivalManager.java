package com.example.nepaltourism.desktopapp.utils;
import java.time.LocalDate;
import java.time.Month;

public class FestivalManager {
    public static boolean isFestivalPeriod(LocalDate date) {
        // Dashain: Mid-October
        // Tihar: Early November
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        return (month == Month.OCTOBER && day >= 10 && day <= 20) ||
                (month == Month.NOVEMBER && day <= 10);
    }

    public static double getFestivalDiscount() {
        return 0.1; // 10% discount during festivals
    }

    public static boolean isMonsoonSeason(LocalDate date) {
        Month month = date.getMonth();
        return month == Month.JUNE || month == Month.JULY || month == Month.AUGUST;
    }
}