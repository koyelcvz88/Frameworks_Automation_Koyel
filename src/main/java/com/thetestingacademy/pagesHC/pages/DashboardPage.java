package com.thetestingacademy.pagesHC.pages;

import com.thetestingacademy.actions.CommonUIActions;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends CommonUIActions {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    private final By dashboardHeader =
            By.xpath("//span[contains(text(),'Hello')]");

    private final By userProfileIcon =
            By.id("userProfile");

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
    // Get logged-in user
    // -------------------------
    public String getLoggedInUser() {

        return Allure.step("Fetch logged in user name", () -> {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
            ).getText();
        });
    }

    // -------------------------
    // Profile visibility
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
    // Logout
    // -------------------------
    public void logout() {

        Allure.step("Logout from application", () -> {
            click(userProfileIcon);
        });
    }
}