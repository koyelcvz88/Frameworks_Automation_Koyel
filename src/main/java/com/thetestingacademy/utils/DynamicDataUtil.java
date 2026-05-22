package com.thetestingacademy.utils;

import java.io.*;

public class DynamicDataUtil {

    private static final String COUNTER_FILE = "counter.txt";

    // Reads + increments persistent counter
    public static synchronized int getNextCounter() {

        int counter = 0;

        try {
            File file = new File(COUNTER_FILE);

            // Read existing value
            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));
                String value = br.readLine();
                br.close();

                if (value != null && !value.trim().isEmpty()) {
                    counter = Integer.parseInt(value.trim());
                }
            }

            // Increment counter
            counter++;

            // Write back updated value
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(String.valueOf(counter));
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return counter;
    }

    // Shared counter per test execution
    public static int getSharedCounter() {
        return getNextCounter();
    }

    // OC Firm Name → Auto New OC Firm n - test n
    public static String getOCFirmName(String baseValue, int count) {
        return baseValue + " " + count;
    }

    // OC Email → autouser@mail.com n - test n
    public static String getOCEmail(String email, int count) {

        String[] parts = email.split("@");

        return parts[0] + count + "@" + parts[1];
    }

    // OC Justification → Auto justification added by automation for Firm n - test n
    public static String getOCJustification(String baseValue, int count) {
        return baseValue + " " + count;
    }

    // OC Attorney → Auto Attorney n - test n
    public static String getOCAttorney(String baseValue, int count) {
        return baseValue + " " + count;
    }
}