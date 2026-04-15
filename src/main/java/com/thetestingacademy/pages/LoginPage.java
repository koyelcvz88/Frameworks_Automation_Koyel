package com.thetestingacademy.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // ✅ FIXED LOCATORS (from working old code)
    private By usernameField = By.id("un");
    private By passwordField = By.id("pw");
    private By signInButton = By.id("jsLoginButton");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Enter Username: {username}")
    public LoginPage enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        return this;
    }

    @Step("Enter Password: ********")
    public LoginPage enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Click Sign In button")
    public void clickSignin() {
        driver.findElement(signInButton).click();
    }
}