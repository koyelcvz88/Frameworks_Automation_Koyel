/*package com.thetestingacademy.tests.Removed;

import com.thetestingacademy.base.BaseTest;
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

public class LoginTest extends BaseTest {

    @Test
    @Feature("Login Module")
    @Story("Valid Login Flow")
    @Description("Verify user can login successfully")
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);

        // Perform Login
        // -------------------------
        DashboardPage dashboard = Allure.step("Perform login", () -> {
            return loginPage.enterUsername("AM_CMSTest")
                    .enterPassword("Maantic@2026")
                    .clickSignin();
        });

        // Validate Login
        // -------------------------
        Allure.step("Validate login success", () -> {

            boolean result = dashboard.isLoaded(); // 👈 FIXED

            // Screenshot
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment("Screenshot", "image/png",
                    new ByteArrayInputStream(screenshot), ".png");

            if (!result) {
                Allure.addAttachment("Failure URL", driver.getCurrentUrl());
            }

            Assert.assertTrue(result, "Login failed - Vendor Req page not opened");
        });

        /*Allure.step("Validate login success and capture screenshot", () -> {

            // 📸 Screenshot
            Allure.addAttachment(
                    "Login Validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );

            // ✅ Assertion
            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "Login failed - Vendor Req page not opened"
            );
        });
    }
} */