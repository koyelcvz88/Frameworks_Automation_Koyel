package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
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

        loginPage
                .enterUsername("AM_CMSTest")
                .enterPassword("Maantic@2026");

        loginPage.clickSignin();

        // 📸 Screenshot at validation point
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
    }
}