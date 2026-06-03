/*package com.thetestingacademy.pagesHC;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    // -------------------------
    // Locators
    // -------------------------
    // Update this if your app has a better stable identifier
    private By dashboardHeader = By.xpath("//span[contains(text(),'Hello')]");

    // Optional: Example user label or menu (more stable than greeting text)
    private By userProfileIcon = By.id("userProfile");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // -------------------------
    // Validate Dashboard Loaded
    // -------------------------
    public boolean isLoaded() {

        return Allure.step("Verify Dashboard is loaded", () -> {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
                );
                return element.isDisplayed();

            } catch (Exception e) {
                Allure.addAttachment(
                        "Dashboard Load Failure",
                        "Dashboard not loaded. Current URL: " + driver.getCurrentUrl()
                );
                return false;
            }
        });
    }

    // -------------------------
    // Example: Get logged-in user text
    // -------------------------
    public String getLoggedInUser() {

        return Allure.step("Fetch logged in user name", () -> {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
            );
            return element.getText();
        });
    }

    // -------------------------
    // Example: Check user profile icon
    // -------------------------
    public boolean isUserProfileVisible() {

        return Allure.step("Check user profile icon visibility", () -> {
            try {
                return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(userProfileIcon)
                ).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        });
    }

    // -------------------------
    // Optional: Logout action (if available in app)
    // -------------------------
    public void logout() {

        Allure.step("Logout from application", () -> {
            driver.findElement(userProfileIcon).click();
            // Add logout locator if available
            // driver.findElement(By.id("logout")).click();
        });
    }
}*/