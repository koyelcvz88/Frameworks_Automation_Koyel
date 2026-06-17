package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends CommonUIActions {

    // =========================
    // LOCATORS
    // =========================
    private By usernameField = By.xpath("//input[@placeholder='Username']");
    private By passwordField = By.xpath("//input[@placeholder='Password']");
    private By signInButton = By.xpath("//input[@value='Sign In']");
    private By dashboardElement = By.xpath("//span[contains(text(),'Hello')]");

    // =========================
    // CONSTRUCTOR
    // =========================
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // =========================
    // USERNAME
    // =========================
    public LoginPage enterUsername(String username) {

        Allure.step("Enter Username: " + username, () -> {
            type(usernameField, username);
        });

        return this;
    }

    // =========================
    // PASSWORD
    // =========================
    public LoginPage enterPassword(String password) {

        Allure.step("Enter Password", () -> {
            type(passwordField, password);
        });

        return this;
    }

    // =========================
    // CLICK SIGN IN
    // =========================
    public DashboardPage clickSignin() {

        Allure.step("Click Sign In button", () -> {
            click(signInButton);
        });

        Allure.step("Wait for login to complete", () -> {
            wait.until(ExpectedConditions.urlContains("vendor-req"));
        });

        return new DashboardPage(driver);
    }

    // =========================
    // COMBINED LOGIN FLOW
    // =========================
    public DashboardPage login(String username, String password) {

        return Allure.step("Login with valid credentials", () -> {
            return enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();
        });
    }

    // =========================
    // LOGIN VALIDATION
    // =========================
    public boolean isLoginSuccessful() {

        return Allure.step("Verify login is successful", () -> {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardElement));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}