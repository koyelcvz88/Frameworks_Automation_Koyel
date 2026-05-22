/*package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

public class LegalRequestInitiationTest2 extends BaseTest {
    //@Test(groups = {"Smoke", "Sanity", "E2E", "Regression"})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC2 - Verify user can initiate legal request")

    @Test(groups = {"Smoke"})
    public void tc1_smoke_initiate_legal_request() {

        runFlow("Smoke");
    }

    @Test(groups = {"Sanity", "E2E", "Regression"})
    public void tc1_full_initiate_legal_request() {

        runFlow("FULL");
    }


    // =========================
    // =========================
    private void runFlow(String mode) {

        openApplication();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard;

        // =========================
        // SMOKE → ONLY EXISTING OC
        // =========================
        if ("Smoke".equalsIgnoreCase(mode)) {

            dashboard = loginPage
                    .enterUsername(ConfigReader.getData("existing.username"))
                    .enterPassword(ConfigReader.getData("existing.password"))
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(), "Smoke login failed");

            performInitiation();
            return;
        }

        // =========================
        // SANITY / E2E / REGRESSION
        // =========================

        // STEP 1: Existing OC
        dashboard = loginPage
                .enterUsername(ConfigReader.getData("existing.username"))
                .enterPassword(ConfigReader.getData("existing.password"))
                .clickSignin();

        Assert.assertTrue(dashboard.isLoaded(), "Existing OC login failed");

        performInitiation();

        // STEP 2: New OC (same session flow as per design)
        WebDriver driver2 = new EdgeDriver();
        driver2.manage().window().maximize();
        driver2.get(ConfigReader.getData("base.url"));

        LoginPage loginPage2 = new LoginPage(driver2);

        DashboardPage dashboard2 = loginPage2
                .enterUsername(ConfigReader.getData("new.username"))
                .enterPassword(ConfigReader.getData("new.password"))
                .clickSignin();

        Assert.assertTrue(dashboard2.isLoaded(), "New OC login failed");
    }

    // =========================
    // COMMON FLOW
    // =========================
    private void performInitiation() {

        LegalRequestInitiationPage page =
                new LegalRequestInitiationPage(driver);

        page.clickInitiateLegalRequest();

        boolean result = page.isFormLoaded() && page.isBannerDisplayed();

        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Initiation Screenshot",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Assert.assertTrue(result, "Initiation failed");
    }
} */