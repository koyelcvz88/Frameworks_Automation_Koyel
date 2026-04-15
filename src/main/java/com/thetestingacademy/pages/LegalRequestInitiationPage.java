package com.thetestingacademy.pages;

import io.qameta.allure.Allure;
import com.thetestingacademy.utils.SceenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    // Actions
    public void clickInitiateLegalRequest() {

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
    }
}