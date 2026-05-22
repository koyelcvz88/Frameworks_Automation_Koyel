/*package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;

import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC3 - Existing OC Flow")

public class LegalRequestExistingOCTest2 extends BaseTest {

    //private String suiteType;

    // =========================
    // CAPTURE SUITE TYPE
    // =========================
    /*@BeforeMethod(alwaysRun = true)
    public void setSuiteType(ITestContext context) {
        if (context.getIncludedGroups().length > 0) {
            suiteType = context.getIncludedGroups()[0];
        }
        System.out.println("Running suite = " + suiteType);
    }

    // =========================
    // MAIN TEST
    // =========================
    @Test(groups = {"Smoke", "Sanity", "E2E", "Regression"})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC4 - Existing OC Flow")

    public void tc4_existing_oc_flow() {

        //if ("Smoke".equalsIgnoreCase(suiteType)) {

            runExistingOCFlow(
                    ConfigReader.getData("existing.username"),
                    ConfigReader.getData("existing.password")
            );

        /*} else {

            runExistingOCFlow(
                    ConfigReader.getData("existing.username"),
                    ConfigReader.getData("existing.password")
            );
        }

        Allure.step("TC4 Completed Successfully");
    }

    // =========================
    // COMMON FLOW
    // =========================
    private void runExistingOCFlow(String username, String password) {

        openApplication();

        // -------------------------
        // LOGIN
        // -------------------------
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboard = loginPage
                .enterUsername(username)
                .enterPassword(password)
                .clickSignin();

        Assert.assertTrue(dashboard.isLoaded(),
                "Login failed for user: " + username);

        // -------------------------
        // INITIATION
        // -------------------------
        LegalRequestInitiationPage initiationPage =
                new LegalRequestInitiationPage(driver);

        initiationPage.clickInitiateLegalRequest();

        Assert.assertTrue(
                initiationPage.isFormLoaded() &&
                        initiationPage.isBannerDisplayed(),
                "Initiation failed"
        );

        // -------------------------
        // COMMON FORM
        // -------------------------
        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver);

        Allure.step("Filling Common Form", formPage::fillLegalRequestForm);

        // -------------------------
        // EXISTING OC FLOW
        // -------------------------
        LegalRequestExistingOCPage existingOCPage =
                new LegalRequestExistingOCPage(driver);

        Allure.step("Executing Existing OC Flow", existingOCPage::fillExistingOCRequest);

        // -------------------------
        // VALIDATION (TEST CLASS ONLY)
        // -------------------------
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        boolean flowCompleted;

        try {
            flowCompleted = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Legal Matter Request Initiated!')]")
                    )
            ).isDisplayed();

            if (flowCompleted) {
                System.out.println("Existing OC flow completed successfully");

                Allure.step("Existing OC flow completed successfully");
            }

        } catch (Exception e) {
            flowCompleted = false;
        }

        Assert.assertTrue(
                flowCompleted,
                "Existing OC flow not completed successfully"
        );
        // Confirmation screen changes
        // STEP: Capture EXOC Request Number
        String exOCRequestNumber = TestData.exOCRequestNumber;
        System.out.println("Using EXOC Request Number: " + exOCRequestNumber);

        // Store for next test usage
        TestData.exOCRequestNumber = exOCRequestNumber;

        // Logging (in test)
        System.out.println("EXOC Request Number stored: " + exOCRequestNumber);

        Allure.step("EXOC Request Number stored for next test: " + exOCRequestNumber);
    }
} */