package com.thetestingacademy.pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;

public class LegalRequestInitiationPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public LegalRequestInitiationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.js = (JavascriptExecutor) driver;
    }

    // Locators
    private By initiateBtn =
            By.xpath("//button[.//span[text()='Initiate Legal Request']]");

    private By formHeader =
            By.xpath("//span[contains(text(),'Initiate Legal Matter Request')]");

    private By banner =
            By.xpath("//span[contains(text(),'Hello')]");

    // Actions
    /*public void clickInitiateLegalRequest() {

        // Log step + screenshot
        Allure.step("Locate Initiate Legal Request button", () -> {
            System.out.println("Looking for Initiate Legal Request button...");
            SceenshotUtil.takeScreenshot(driver, "InitiateLegalRequestButtonLocated");
        });

        // Wait until button is visible
        WebElement btn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(initiateBtn)
        );

        // Scroll into view
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(btn));

        // Log ready state
        Allure.step("Initiate button found and ready to click", () -> {
            System.out.println("Button found. Clicking now...");
            SceenshotUtil.takeScreenshot(driver, "InitiateLegalRequestButtonReady");
        });

        // Click with fallback
        try {
            btn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", btn);
        }

        // Wait for form to load
        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(formHeader)
        );

        // Log success
        Allure.step("Form loaded successfully", () -> {
            System.out.println("Form loaded: " + header.getText());
            SceenshotUtil.takeScreenshot(driver, "InitiateLegalRequestFormLoaded");
        });
    } */

    // ACTION: Click Initiate Legal Request
    // -------------------------
    // VALIDATION: Banner Visible
    // -------------------------
    public boolean isBannerDisplayed() {
        return Allure.step("Verify banner is displayed", () -> {
            try {
                // Try to find the element and verify its visibility
                boolean isDisplayed = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(banner)
                ).isDisplayed();

                if (isDisplayed) {
                    // Capture and attach screenshot on success
                    captureScreenshot("Success: banner is displayed");
                }
                return isDisplayed;
            } catch (Exception e) {
                // In case of failure, attach failure details including screenshot
                attachFailureDetails(e);  // Passing exception to include message
                return false;
            }
        });
    }
    public LegalRequestInitiationPage clickInitiateLegalRequest() {

        return Allure.step("Click Initiate Legal Request button", () -> {

            WebElement btn = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(initiateBtn)
            );

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

            wait.until(ExpectedConditions.elementToBeClickable(btn));

            btn.click();

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(formHeader)
            );

            return this;
        });
    }
    // VALIDATION: Form Loaded
    // -------------------------
    public boolean isFormLoaded() {

        return Allure.step("Verify Legal Request form is loaded", () -> {
            try {
                // Try to find the element and verify its visibility
                boolean isLoaded = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(formHeader)
                ).isDisplayed();

                if (isLoaded) {
                    // Capture and attach screenshot on success
                    captureScreenshot("Form Loaded Screenshot");
                }
                return isLoaded;
            } catch (Exception e) {
                // In case of failure, attach failure details including screenshot
                attachFailureDetails(e);  // Passing exception to include message
                return false;
            }
        });
    }
    public void captureScreenshot(String screenshotName) {
        // Capture the screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the screenshot to Allure
        Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshot), ".png");
    }

    // COMMON FAILURE HANDLER
    // -------------------------
    private void attachFailureDetails(Exception e) {
        // Capture the screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach the failure screenshot to Allure
        Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");

        // Attach the exception message to Allure
        Allure.addAttachment("Error Message", "text/plain", e.getMessage(), ".txt");

        // Optionally, you can attach the stack trace if you want more details
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Allure.addAttachment("Stack Trace", "text/plain", sw.toString(), ".txt");

        // Optionally, attach the current URL if you want more context
        Allure.addAttachment("Failure URL", driver.getCurrentUrl());
    }
}