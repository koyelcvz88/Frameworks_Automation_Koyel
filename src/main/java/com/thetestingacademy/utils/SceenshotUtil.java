package com.thetestingacademy.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class SceenshotUtil {

    // Primary method (NEW STANDARD)
    public static void takeScreenshot(WebDriver driver, String stepName) {
        attach(driver, stepName);
    }

    // Backward compatibility (OLD CODE SUPPORT)
    public static void capture(WebDriver driver, String stepName) {
        attach(driver, stepName);
    }

    // Core implementation
    private static void attach(WebDriver driver, String stepName) {
        try {
            if (driver == null) return;

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(stepName, new ByteArrayInputStream(screenshot));

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}