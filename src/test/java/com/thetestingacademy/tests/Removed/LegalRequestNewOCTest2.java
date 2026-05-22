/* package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.By;
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
@Story("TC4 - New OC Flow")

public class LegalRequestNewOCTest2 extends BaseTest {

    /*private String suiteType;

    // =========================
    // CAPTURE SUITE TYPE
    // =========================
     @BeforeMethod(alwaysRun = true)
    public void setSuiteType(ITestContext context) {
        if (context.getIncludedGroups().length > 0) {
            suiteType = context.getIncludedGroups()[0];
        }
        System.out.println("Running suite = " + suiteType);
    }

    // =========================
    // MAIN TEST
    // =========================
    @Test(groups = {"Sanity", "E2E", "Regression"})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC4 - New OC Flow")
    public void tc4_new_oc_flow() {

        /* if ("Sanity".equalsIgnoreCase(suiteType)
                || "E2E".equalsIgnoreCase(suiteType)
                || "Regression".equalsIgnoreCase(suiteType))  {

            runNewOCFlow(
                    ConfigReader.getData("new.username"),
                    ConfigReader.getData("new.password")
            );

        // } else
        /* {
            // skip everything else (including Smoke)
            System.out.println("Skipping suite type: " + suiteType);
        } */

        /*Allure.step("TC - New OC Flow Completed Successfully");
    }

        // =========================
        // COMMON FLOW
        // =========================
        private void runNewOCFlow(String username, String password) {

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
            // New OC FLOW
            // -------------------------

            // STEP 1: Initialize Page Object
            LegalRequestNewOCPage2 newOCPage2 = new LegalRequestNewOCPage2(driver);

            // STEP 2: Execute TC4 Flow
            /* Allure.step("TC4 - Fill and Submit New OC Request", () -> {
                newOCPage2.fillNewOCRequest();
            }); */
            /*Allure.step("Executing New OC Flow", newOCPage2::fillNewOCRequest);

            // -------------------------
            // VALIDATION (TEST CLASS ONLY)
            // -------------------------
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); */

            /* boolean flowCompleted;

            try {
                flowCompleted = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Legal Matter Request Initiated!')]")
                        )
                ).isDisplayed();

                if (flowCompleted) {
                    System.out.println("New OC flow completed successfully");

                    Allure.step("New OC flow completed successfully");
                }

            } catch (Exception e) {
                flowCompleted = false;
            }

            Assert.assertTrue(
                    flowCompleted,
                    "New OC flow not completed successfully"
            );

            Assert.assertTrue(
                    wait.until(
                            ExpectedConditions.textToBePresentInElementLocated(
                                    org.openqa.selenium.By.xpath(
                                            "//*[contains(text(),'Legal Matter Request Initiated!')]"
                                    ),
                                    "Legal Matter Request Initiated!"
                            )
                    ),
                    "New OC flow not completed successfully"
            );

            System.out.println("New OC flow completed successfully");
            Allure.step("New OC flow completed successfully"); */
            // Confirmation screen changes
            // STEP: Capture NewOC Request Number
            /* String newOCRequestNumber = TestData.newOCRequestNumber;
            System.out.println("Using NewOC Request Number: " + newOCRequestNumber);

            // Store for next test usage
            TestData.newOCRequestNumber = newOCRequestNumber;

            // Logging (in test)
            System.out.println("NewOC Request Number stored: " + newOCRequestNumber);

            Allure.step("NewOC Request Number stored for next test: " + newOCRequestNumber);
        }
            Assert.assertNotNull(
                    TestData.newOCRequestNumber,
                    "New OC Request Number is NULL"
            );

            System.out.println("NewOC Request Number: " + TestData.newOCRequestNumber);

            Allure.step("NewOC Request Number captured: " + TestData.newOCRequestNumber);
        }


        // STEP 6: Final Assertion / Logging
        /*System.out.println("✅ TC4 PASSED");
        Allure.step("TC4 PASSED SUCCESSFULLY");
    }
} */