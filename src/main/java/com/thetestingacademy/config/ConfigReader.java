package com.thetestingacademy.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties dataProperties = new Properties();
    private static final Properties newOcProperties = new Properties();

    static {

        try {
            // Load data.properties
            try (InputStream dataStream = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("data.properties")) {

                if (dataStream == null) {
                    throw new RuntimeException("❌ data.properties not found in src/main/resources");
                }
                dataProperties.load(dataStream);
            }

            // Load newoc.properties
            try (InputStream newOcStream = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("newoc.properties")) {

                if (newOcStream == null) {
                    throw new RuntimeException("❌ newoc.properties not found in src/main/resources");
                }
                newOcProperties.load(newOcStream);
            }

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load properties files", e);
        }
    }

    // =========================
    // GENERIC GETTERS
    // =========================

    public static String getData(String key) {
        return fetch(dataProperties, key, "data.properties");
    }

    public static String getNewOC(String key) {
        return fetch(newOcProperties, key, "newoc.properties");
    }

    private static String fetch(Properties props, String key, String fileName) {
        String value = props.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("❌ Key not found in " + fileName + ": " + key);
        }

        return value.trim();
    }

    // =========================
    // SPECIFIC METHODS (optional convenience)
    // =========================

    public static String getOCFirmName() {
        return getNewOC("ocFirmName");
    }
}