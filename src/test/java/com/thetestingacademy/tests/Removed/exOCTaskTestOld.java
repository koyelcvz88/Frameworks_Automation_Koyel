package com.thetestingacademy.tests.Removed;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("VenReq Automation Suite")
@Feature("EX OC Request + Task Workflow")

public class exOCTaskTestOld extends BaseTest {

    // =========================================================
    // SANITY
    // =========================================================
    @Test(groups = {"Sanity"})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Sanity - EX OC Flow till Task 1")
    public void tc_sanity_ex_oc_flow() {

        createExOCRequest(
                ConfigReader.getData("existing.username"),
                ConfigReader.getData("existing.password")
        );

        navigateToSummaryAndTasks();

        executeTask1();

        Allure.step("SANITY FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // E2E
    // =========================================================
    @Test(groups = {"E2E"})
    @Owner("Koyel")
    @Severity(SeverityLevel.BLOCKER)
    @Description("E2E - EX OC Full Task Workflow")
    public void tc_e2e_ex_oc_flow() {

        createExOCRequest(
                ConfigReader.getData("existing.username"),
                ConfigReader.getData("existing.password")
        );

        navigateToSummaryAndTasks();

        executeTask1();
        // executeTask2();
        // executeTask3();

        Allure.step("E2E FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // REGRESSION
    // =========================================================
    @Test(groups = {"Regression"})
    @Owner("Koyel")
    @Severity(SeverityLevel.NORMAL)
    @Description("Regression - EX OC Full Flow + Validations")
    public void tc_regression_ex_oc_flow() {

        createExOCRequest(
                ConfigReader.getData("existing.username"),
                ConfigReader.getData("existing.password")
        );

        navigateToSummaryAndTasks();

        executeTask1();
        // executeTask2();
        // executeTask3();

        Allure.step("REGRESSION FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // COMMON FLOW METHODS
    // =========================================================

    private void createExOCRequest(String username, String password) {

        openApplication();

        // -------------------------
        // LOGIN
        // -------------------------
        Allure.step("TC1 - Login to Application", () -> {

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Login failed for user: " + username);
        });

        // -------------------------
        // INITIATION
        // -------------------------
        Allure.step("TC2 - Initiate Legal Request", () -> {

            LegalRequestInitiationPage initiationPage =
                    new LegalRequestInitiationPage(driver);

            initiationPage.clickInitiateLegalRequest();

            Assert.assertTrue(
                    initiationPage.isFormLoaded() &&
                            initiationPage.isBannerDisplayed(),
                    "Initiation failed");
        });

        // -------------------------
        // COMMON FORM
        // -------------------------
        Allure.step("TC3 - Fill Common Form", () -> {

            LegalRequestFormPage formPage =
                    new LegalRequestFormPage(driver);

            formPage.fillLegalRequestForm();
        });

        // -------------------------
        // EXISTING OC FORM
        // -------------------------
        Allure.step("TC4 - Fill Existing OC Request Details", () -> {

            LegalRequestExistingOCPage existingOCPage =
                    new LegalRequestExistingOCPage(driver);

            existingOCPage.fillExistingOCRequest();
        });

        // Confirmation screen changes
        Allure.step("TC5 - Create EX OC Request", () -> {
            // STEP: Capture EXOC Request Number

            String exOCRequestNumber = TestData.exOCRequestNumber;
            System.out.println("Using EXOC Request Number: " + exOCRequestNumber);

            // Store for next test usage
            TestData.exOCRequestNumber = exOCRequestNumber;

            // Logging (in test)
            System.out.println("EXOC Request Number : " + exOCRequestNumber);

            Allure.step("EXOC Request Number: " + exOCRequestNumber);
        });
        // Open and click on request under Homepage Request grid
        Allure.step("TC6 - Navigate Home + Open Latest EX OC Request", () -> {
            Ex_OC_Request_NavigatorPage navigatorPage =
                    new Ex_OC_Request_NavigatorPage(driver);

            navigatorPage.exOCRequest();   // ✅ no parameter
        });

    }
        // =========================================================
        // SUMMARY NAVIGATION
        // =========================================================
        private void navigateToSummaryAndTasks() {
        // ===== SUMMARY PAGE =====
        Allure.step("Navigate to Summary page and open Tasks tab", () -> {

            ExOCSummaryPage summary = new ExOCSummaryPage(driver);

            Assert.assertTrue(summary.isSummaryPageLoaded(), "Summary page not loaded");

            summary.navigateToTasksTab();
        });
    }

        // =========================================================
        // TASK 1
        // =========================================================
        private void executeTask1() {

            ConfirmWorkCompletionPage taskPage = new ConfirmWorkCompletionPage(driver);

            // =========================================================
            // TASK 1 - NAVIGATION + OPEN TASK
            // =========================================================
            Allure.step("Task 1 - Navigation and opening task", () -> {

                taskPage.openAndEnterConfirmWorkCompletionTask();
            });

            // =========================================================
            // TASK 1 - VALIDATION
            // =========================================================
            /*Allure.step("Verify task page opened via URL", () -> {

                //boolean isOpened = taskPage.isTaskOpened();

                Assert.assertTrue(isOpened, "Task URL not opened correctly");

                if (isOpened) {
                    System.out.println("✅ Task URL verified: " + driver.getCurrentUrl());
                    SceenshotUtil.takeScreenshot(driver, "Task URL Verified");
                } else {
                    System.out.println("❌ Task URL mismatch");
                    SceenshotUtil.takeScreenshot(driver, "Task URL Mismatch");
                }
            }); */
        }

            /*// =========================================================
            Execute Task 1 - Confirm Work Completion
            // STEP 3 - VALIDATE UI LOADED
            // =========================================================
            Allure.step("Validate task UI is fully loaded", () -> {

                Assert.assertTrue(taskPage.isPageLoaded(),
                        "Task page UI not loaded properly");

                SceenshotUtil.takeScreenshot(driver,
                        "Assert Passed - Task UI Loaded");
            });

            // Future steps:
            // taskPage.openRequestCloseStatusDropdown();
            // taskPage.selectDropdownValue("Yes");
            // taskPage.clickSubmit();
        });
    } */
}