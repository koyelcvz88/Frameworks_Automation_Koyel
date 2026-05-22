/*package com.thetestingacademy.tests.Removed;

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

public class LoginTest_New_OC extends BaseTest {

    @Test(groups = {"Sanity", "E2E", "Regression"})
    @Feature("Login Module")
    @Story("New OC Login Flow")
    @Description("Verify login works for New OC user only")
    public void loginTest_newOC() {

        LoginPage loginPage = new LoginPage(driver);
        openApplication();

        // Fetch credentials from properties
        String username = ConfigReader.getData("new.username");
        String password = ConfigReader.getData("new.password");

        // Perform login
        DashboardPage dashboard = Allure.step("Login as New OC user: ", () -> {
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

            Allure.addAttachment("New OC Login Screenshot - " , "image/png",
                    new ByteArrayInputStream(screenshot), ".png");

            if (!result) {
                Allure.addAttachment("Failure URL", driver.getCurrentUrl());
            }

            Assert.assertTrue(result,
                    "Login failed for New OC : ");
        });
    }
} */