/*package com.thetestingacademy.tests.OldSuites;

import com.thetestingacademy.Removed.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.pagesHC.*;
import com.thetestingacademy.pagesHC.LegalRequestExistingOCPage;
import com.thetestingacademy.pagesHC.LegalRequestFormPage;
import com.thetestingacademy.pagesHC.LegalRequestInitiationPage;
import com.thetestingacademy.pagesHC.LoginPage;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("VenReq Automation Suite")
@Feature("EX OC Request + Task Workflow")
public class exOCTaskTest extends BaseTest {

    // =========================================================
    // SANITY
    // =========================================================
    @Test(groups = {""})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Sanity - EX OC Flow till Task 1")
    public void tc_sanity_ex_oc_flow() {

        createExOCRequest(
                ConfigReader.getData("existing.username"),
                ConfigReader.getData("existing.password")
        );

        /*navigateToSummaryAndTasks();
        executeTask1();
        executeTask2();
        relatedAction();

        Allure.step("SANITY FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // E2E
    // =========================================================
    /*@Test(groups = {"E2E"})
    public void tc_e2e_ex_oc_flow() {

        createExOCRequest(
                ConfigReader.getData("existing.username"),
                ConfigReader.getData("existing.password")
        );

        navigateToSummaryAndTasks();
        executeTask1();

        Allure.step("E2E FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // REGRESSION
    // =========================================================
    @Test(groups = {"Regression"})
    public void tc_regression_ex_oc_flow() {

        createExOCRequest(
                ConfigReader.getData("existing.username"),
                ConfigReader.getData("existing.password")
        );

        navigateToSummaryAndTasks();
        executeTask1();
        //public void tc_confirm_work_completion_edge_flow() {

            ConfirmWorkCompletionPage page =
                    new ConfirmWorkCompletionPage(driver);

            page.handleConfirmWorkCompletionEdgeFlow();
        //}

        Allure.step("REGRESSION FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // COMMON FLOW
    // =========================================================
    private void createExOCRequest(String username, String password) {

        openApplication();

        Allure.step("Login to Application", () -> {

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Login failed for user: " + username);
        });

        Allure.step("Initiate Legal Request", () -> {

            LegalRequestInitiationPage initiationPage =
                    new LegalRequestInitiationPage(driver);

            initiationPage.clickInitiateLegalRequest();

            Assert.assertTrue(
                    initiationPage.isFormLoaded() &&
                            initiationPage.isBannerDisplayed(),
                    "Initiation failed"
            );
        });

        Allure.step("Fill Common Form", () -> {

            new LegalRequestFormPage(driver).fillLegalRequestForm();
        });

        Allure.step("Fill Existing OC Request", () -> {

            new LegalRequestExistingOCPage(driver).fillExistingOCRequest();
        });

        Allure.step("Capture EX OC Request Number", () -> {

            String exOCRequestNumber = TestData.exOCRequestNumber;

            System.out.println("EXOC Request Number : " + exOCRequestNumber);

            Allure.addAttachment("EXOC Request Number", exOCRequestNumber);
        });

        Allure.step("Navigate Home + Open EX OC Request", () -> {

            new Ex_OC_Request_NavigatorPage(driver).exOCRequest();
        });
    }

    // =========================================================
    // SUMMARY NAVIGATION
    // =========================================================
    /*private void navigateToSummaryAndTasks() {

        Allure.step("Navigate to Summary page and open Tasks tab", () -> {

            ExOCSummaryPage summary = new ExOCSummaryPage(driver);

            Assert.assertTrue(summary.isSummaryPageLoaded(),
                    "Summary page not loaded");

            summary.navigateToTasksTab();

            SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");
        });
    }

    // =========================================================
    // TASK 1
    // =========================================================
    private void executeTask1() {

        ConfirmWorkCompletionPage taskPage = new ConfirmWorkCompletionPage(driver);

        // -------------------------------
        // TASK NAVIGATION
        // -------------------------------
        Allure.step("Task 1 - Navigation and opening task", () -> {

            taskPage.openAndEnterConfirmWorkCompletionTask();
        });

        // -------------------------------
        // VALIDATION
        // -------------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "❌ Task URL not opened correctly. Current URL: " + currentUrl);

            System.out.println("✅ Task URL verified: " + currentUrl);
        });
        Allure.step("Task 1 - Validating and selecting fields", () -> {

            taskPage.handleConfirmWorkCompletionFields();
            //taskPage.handleConfirmWorkCompletionEdgeFlow();
        });
    }

        private void executeTask2() {

            EnterInvoice_PaymentConfirmationPage task2Page = new EnterInvoice_PaymentConfirmationPage(driver);

            // -------------------------------
            // TASK NAVIGATION
            // -------------------------------
            Allure.step("Task 2 - Navigation and opening task", () -> {

                task2Page.openAndEnterInvoicepaymentConfirmationTask();
            });

            // -------------------------------
            // VALIDATION
            // -------------------------------
            Allure.step("Validate Task URL opened", () -> {

                String currentUrl = driver.getCurrentUrl();

                boolean isOpened = currentUrl.contains("/start-process/");

                SceenshotUtil.takeScreenshot(driver,
                        isOpened ? "Task URL Verified" : "Task URL Mismatch");

                Assert.assertTrue(isOpened,
                        "❌ Task URL not opened correctly. Current URL: " + currentUrl);

                System.out.println("✅ Task URL verified: " + currentUrl);
            });
            Allure.step("Task 2 - Validating and selecting fields", () -> {

                task2Page.handleEnterInvoiceandPaymentConfirmationFields();
            });
    // =========================================================
    // Update and Cancel Request
    // =========================================================
    private void relatedAction() {

        Update_CancelRequestPage actionPage = new Update_CancelRequestPage(driver);
        Allure.step("Cancel Request Fields", () -> {

            actionPage.handleEnterUpdateRequestFields();
            //actionPage.handleEnterCancelRequestFields();
            /*EnterInvoice_PaymentConfirmationPage task2Page = new EnterInvoice_PaymentConfirmationPage(driver);

            // -------------------------------
            // TASK NAVIGATION
            // -------------------------------
            Allure.step("Task 2 - Navigation and opening task", () -> {

                task2Page.openAndEnterInvoicepaymentConfirmationTask();
            });

            // -------------------------------
            // VALIDATION
            // -------------------------------
            Allure.step("Validate Task URL opened", () -> {

                String currentUrl = driver.getCurrentUrl();

                boolean isOpened = currentUrl.contains("/start-process/");

                SceenshotUtil.takeScreenshot(driver,
                        isOpened ? "Task URL Verified" : "Task URL Mismatch");

                Assert.assertTrue(isOpened,
                        "❌ Task URL not opened correctly. Current URL: " + currentUrl);

                System.out.println("✅ Task URL verified: " + currentUrl);
            });
        });
    }
} */