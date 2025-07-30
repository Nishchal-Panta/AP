package com.example.nepaltourism.desktopapp.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    static {
        setLanguage("en");
    }

    public static void setLanguage(String languageCode) {
        currentLocale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("com.example.nepaltourism.lang.messages", currentLocale);
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }
}