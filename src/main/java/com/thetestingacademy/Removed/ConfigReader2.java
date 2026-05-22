/*package com.thetestingacademy.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader2 {

    private static final Properties properties = new Properties();

    static {
        try {
            String path = System.getProperty("user.dir") +
                    "/src/main/java/com/thetestingacademy/config/data.properties";

            InputStream input = new FileInputStream(path);
            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load data.properties", e);
        }
    }

    // Generic getter with override support
    public static String get(String key) {
        String value = System.getProperty(key); // 1st priority (CLI)

        if (value == null) {
            value = System.getenv(key); // 2nd priority (ENV)
        }

        if (value == null) {
            value = properties.getProperty(key); // fallback
        }

        if (value == null) {
            throw new RuntimeException("❌ Key not found: " + key);
        }

        return value;
    }

    // Role-based helper
    public static String getUser(String role, String field) {
        return get(role + "." + field);
    }

    private static final Properties newOcProperties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("newoc.properties");

            if (input != null) {
                newOcProperties.load(input);
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load newoc.properties", e);
        }
    }
    public static String getNewOC(String key) {
        String value = newOcProperties.getProperty(key);

        if (value == null) {
            throw new RuntimeException("❌ Key not found in newoc.properties: " + key);
        }

        return value;
    }
} */