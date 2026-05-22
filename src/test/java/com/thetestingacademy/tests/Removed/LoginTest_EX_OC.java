/*package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.DashboardPage;
import com.thetestingacademy.pages.LoginPage;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

public class LoginTest_EX_OC extends BaseTest {

    @Test(groups = {"Smoke", "Sanity", "E2E", "Regression"})
    @Feature("Login Module")
    @Story("Existing OC Login Flow")
    @Description("Verify login works for Existing OC user only")
    public void loginTest_existingOC() {

        LoginPage loginPage = new LoginPage(driver);
        openApplication();

        // Fetch credentials from properties
        String username = ConfigReader.getData("existing.username");
        String password = ConfigReader.getData("existing.password");

        // Perform login
        DashboardPage dashboard = Allure.step("Login as Existing OC user: ", () -> {
            return loginPage.enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();
        });

        // Validation
        Allure.step("Validate login for role: ", () -> {

            boolean result = dashboard.isLoaded();

            // Screenshot
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment("Existing OC Login Screenshot - " , "image/png",
                    new ByteArrayInputStream(screenshot), ".png");

            if (!result) {
                Allure.addAttachment("Failure URL", driver.getCurrentUrl());
            }

            Assert.assertTrue(result,
                    "Login failed for Existing OC : ");
        });
    }
} */