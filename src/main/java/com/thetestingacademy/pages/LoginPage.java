package com.thetestingacademy.pages;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    // WebDriver driver;
    WebDriver driver;
    WebDriverWait wait;

    // ✅ FIXED LOCATORS (from working old code)
    private By usernameField = By.xpath("//input[@placeholder='Username']");
    //By.id("un");
    private By passwordField = By.xpath("//input[@placeholder='Password']");
    //By.id("pw");
    private By signInButton = By.xpath("//input[@value='Sign In']");
    //By.id("jsLoginButton");

    // BEST: use banner OR dashboard element
    private By dashboardElement = By.xpath("//span[contains(text(),'Hello')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /* public LoginPage enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        return this;
    } */
    public LoginPage enterUsername(String username) {
        Allure.step("Enter Username: " + username, () -> {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).clear();
            driver.findElement(usernameField).sendKeys(username);
        });
        return this;
    }

    /*public LoginPage enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        return this;
    } */
        public LoginPage enterPassword(String password) {
            Allure.step("Enter Password: [PROTECTED]", () -> {
                wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).clear();
                driver.findElement(passwordField).sendKeys(password);
            });
            return this;
        }

    /* public void clickSignin() {
        driver.findElement(signInButton).click();
    }
    public LoginPage clickSignin() {
        Allure.step("Click Sign In button", () -> {
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        });
        return this;
    } */

    public DashboardPage clickSignin() {

        Allure.step("Click Sign In button", () -> {
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        });

        // Optional but IMPORTANT: wait for navigation
        Allure.step("Wait for login to complete", () -> {
            wait.until(ExpectedConditions.urlContains("vendor-req"));
        });

        return new DashboardPage(driver);
    }

    // ✅ Validation method (USED IN TEST ASSERTION)
    /* public boolean isLoginSuccessful() {
        return driver.getCurrentUrl()
                .equals("https://trimont-test.appiancloud.com/suite/sites/vendor-req");
    }
    // ✅ Combined login action (BEST PRACTICE)
    @Step("Login with valid credentials")
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignin();
        return this;
    } */
    public DashboardPage login(String username, String password) {

        return Allure.step("Login with valid credentials", () -> {
            return enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();
        });
    }
    // ✅ Robust login validation

        /*public boolean isLoginSuccessful() {
        try {
            // Wait until URL changes to post-login page
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.urlContains("/suite/sites/vendor-req"));

            return true;

        } catch (Exception e) {
            System.out.println("Login failed. Current URL: " + driver.getCurrentUrl());
            return false;
        } */
        public boolean isLoginSuccessful() {
            return Allure.step("Verify login is successful", () -> {
                try {
                    WebElement element = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(dashboardElement)
                    );
                    return element.isDisplayed();
                } catch (Exception e) {
                    Allure.addAttachment(
                            "Failure Details",
                            "Login failed. Current URL: " + driver.getCurrentUrl()
                    );
                    return false;
                }
            });
    }
}