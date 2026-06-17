package com.thetestingacademy.utils;

import com.thetestingacademy.config.ConfigReader;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DynamicDataUtil {

    private static final String COUNTER_FILE = "counter.txt";
    private static final Random random = new Random();

    // Reads + increments persistent counter
    public static synchronized int getNextCounter() {

        int counter = 0;

        try {
            File file = new File(COUNTER_FILE);

            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));
                String value = br.readLine();
                br.close();

                if (value != null && !value.trim().isEmpty()) {
                    counter = Integer.parseInt(value.trim());
                }
            }

            counter++;

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(String.valueOf(counter));
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return counter;
    }

    public static int getSharedCounter() {
        return getNextCounter();
    }

    // =========================
    // OC FIRM HELPERS
    // =========================
    public static String getOCFirmName(String baseValue, int count) {
        return baseValue + " " + count;
    }

    public static String getOCEmail(String email, int count) {

        String[] parts = email.split("@");
        return parts[0] + count + "@" + parts[1];
    }

    public static String getOCJustification(String baseValue, int count) {
        return baseValue + " " + count;
    }

    public static String getOCAttorney(String baseValue, int count) {
        return baseValue + " " + count;
    }

    // =========================
    // 🔥 NEW: OC CITY HANDLING
    // =========================

    // RANDOM CITY PICKER (recommended)
    public static String getOCCityRandom() {

        String raw = ConfigReader.getNewOC("ocCities");

        if (raw == null || raw.trim().isEmpty()) {
            throw new RuntimeException("❌ ocCities not found in properties file");
        }

        List<String> cities = Arrays.asList(raw.split("\\s*,\\s*"));

        return cities.get(random.nextInt(cities.size()));
    }

    // SEQUENTIAL CITY PICKER (optional for regression stability)
    public static String getOCCitySequential(int index) {

        String raw = ConfigReader.getNewOC("ocCities");

        if (raw == null || raw.trim().isEmpty()) {
            throw new RuntimeException("❌ ocCities not found in properties file");
        }

        List<String> cities = Arrays.asList(raw.split("\\s*,\\s*"));

        return cities.get(index % cities.size());
    }
}