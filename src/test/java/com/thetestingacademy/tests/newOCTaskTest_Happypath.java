package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("VenReq Automation Suite")
@Feature("NEW OC Request + Task Workflow")
public class newOCTaskTest_Happypath extends BaseTest {

    // =========================================================
    // SANITY
    // =========================================================
    @Test(groups = {"E2E"})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E - NEW OC Flow")
    public void tc_sanity_new_oc_flow() {

        // STEP 1 - CREATE NEW OC REQUEST
        createNewOCRequest(
                ConfigReader.getData("new.username"),
                ConfigReader.getData("new.password")
        );

        // =========================
        // STEP 2 - NAVIGATE HOME + OPEN REQUEST
        // =========================
        Allure.step("Navigate Home + Open NEW OC Request", () -> {

            new New_OC_Request_NavigatorPage(driver).newOCRequest();
        });

        // STEP 3 -  NAVIGATE TO SUMMARY AND TASK TAB
        navigateToSummaryAndTasks();

        // STEP 4 - RELOGIN AS LEGAL SENIOR MANAGER
        reloginToApplication(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // STEP 5 - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // STEP 6 -  NAVIGATE TO SUMMARY AND TASK TAB
        navigateToSummaryAndTasks();

        // =========================
        // REVIEW NEW VENDOR APPROVE FLOW TASK EXECUTION
        // =========================
        executeTaskNewVendor();

        // - RELOGIN AS VENDOR MANAGER
        reloginAsVendorLegal(
                ConfigReader.getData("newVenMgr.username"),
                ConfigReader.getData("newVenMgr.password")
        );

        // - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // -  NAVIGATE TO SUMMARY AND TASK TAB
        navigateToSummaryAndTasks();

        // =========================
        // REVIEW OFAC NEW VENDOR TASK APPROVE FLOW TASK EXECUTION
        // =========================
        executeTaskNewOFACVendor();

        // - RELOGIN AS CMS INTERNAL COUNSEL
        reloginAsIternalCounsel(
                ConfigReader.getData("newCounsel.username"),
                ConfigReader.getData("newCounsel.password")
        );

        // - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // -  NAVIGATE TO SUMMARY AND TASK TAB
        navigateToSummaryAndTasks();

        // =========================
        // REVIEW OFAC NEW VENDOR TASK APPROVE FLOW TASK EXECUTION
        // =========================
        executeTaskConflictWaiver();

        // STEP 4 - RELOGIN AS LEGAL SENIOR MANAGER
        reloginToApplication(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // STEP 5 - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // STEP 6 -  NAVIGATE TO SUMMARY AND TASK TAB
        navigateToSummaryAndTasks();

        // =========================
        // EXECUTE AND UPLOAD ENGAGEMENT LETTER TASK APPROVE FLOW TASK EXECUTION
        // =========================
        executeTaskExecuteEL();

        // - RELOGIN AS LEGAL MANAGER
        reloginAsLegal(
                ConfigReader.getData("new.username"),
                ConfigReader.getData("new.password")
        );

        // STEP 5 - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
        navigateToSummaryAndTasks();

        // =========================
        // CONFIRM WORK COMPLETION TASK  FLOW TASK EXECUTION
        // =========================
        executeTask1();
        ;

        // =========================================================
        // ENTER INVOICE PAYMENT CONFIRMATION FLOW TASK EXECUTION
        // =========================================================
        executeTask2();


        /*// =========================
        // REQUEST RESUBMISSION TASK
        // =========================
        executeTaskrequestResub();

        // - RELOGIN AS LEGAL SENIOR MANAGER
        reloginToApplication(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // -  NAVIGATE TO SUMMARY AND TASK TAB
        navigateToSummaryAndTasks(); */
    /*
    relatedAction();*/

        Allure.step("SANITY FLOW COMPLETED SUCCESSFULLY");
    }

    // =========================================================
    // COMMON FLOW
    // =========================================================
    private void createNewOCRequest(String username, String password) {

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

            new LegalRequestNewOCPage2(driver).fillNewOCRequest();
        });

        Allure.step("Capture NEW OC Request Number", () -> {

            String newOCRequestNumber = TestData.newOCRequestNumber;

            System.out.println("NEWOC Request Number : " + newOCRequestNumber);

            Allure.addAttachment("NEWOC Request Number", newOCRequestNumber);
        });

    }

    // =========================================================
    // RE LOGIN AS SENIOR LEGAL MANAGER
    // =========================================================
    private void reloginToApplication(String username,
                                      String password) {

        Allure.step("Relaunch application and relogin", () -> {

            // IMPORTANT
            // Navigate back to login page first

            driver.get(ConfigReader.getData("base.url"));

            WebDriverWait wait =
                    new WebDriverWait(driver,
                            Duration.ofSeconds(30));

            // WAIT FOR LOGIN PAGE
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                   // By.id("un")));
                    By.xpath("//input[@placeholder='Username']")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful AS SENIOR LEGAL MANAGER");
        });
    }

    // =========================================================
    //Navigate to Home + Teams tab + Open request
    // =========================================================
    private void openRequestTeamsTab() {

        Allure.step("Search and open generated request", () -> {

            String newOCRequestNumber = TestData.newOCRequestNumber;
            NewOC_Team_RequestPage teamPage =
                    new NewOC_Team_RequestPage(driver);

            teamPage.openRequestTeamsTab(
                    newOCRequestNumber);

            SceenshotUtil.takeScreenshot(driver,
                    "Request_Reopened_After_Relogin");
        });
    }

    // =========================================================
    // SUMMARY NAVIGATION
    // =========================================================
    private void navigateToSummaryAndTasks() {

        Allure.step("Navigate to Summary page and open Tasks tab", () -> {

            NewOCSummaryPage summary = new NewOCSummaryPage(driver);

            Assert.assertTrue(summary.isSummaryPageLoaded(),
                    "Summary page not loaded");

            summary.navigateToTasksTab();

            SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");
        });
    }

    // =========================================================
    // TASK Review new vendor EXECUTION
    // =========================================================
    /*private void executeTaskA() {

        ReviewNewVendorPage VendorPage =
                new ReviewNewVendorPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("Task 1 - Navigation and opening task", () -> {

            VendorPage.openAndEnterReviewNewVendorTask();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
        });

        // -------------------------
        // TASK FIELDS HANDLING
        // -------------------------
        Allure.step("Task 1 - Validating and selecting fields", () -> {

            //VendorPage.handleReviewNewVendorFields();
            VendorPage.handleReviewNewVendorEdgeFlow();
        });
    } */


    // =========================================================
    // RE LOGIN AS  LEGAL MANAGER
    // =========================================================
    private void reloginAsLegal(String username, String password) {

        Allure.step("Relaunch application and relogin", () -> {

            // IMPORTANT
            // Navigate back to login page first

            driver.get(ConfigReader.getData("base.url"));

            WebDriverWait wait =
                    new WebDriverWait(driver,
                            Duration.ofSeconds(30));

            // WAIT FOR LOGIN PAGE
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("un")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful as Vendor Manager");
        });
    }


    // =========================================================
    // REVIEW NEW VENDOR APPROVE FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskNewVendor() {

        ReviewNewVendorPage VendorPage =
                new ReviewNewVendorPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("REVIEW NEW VENDOR TASK - Navigation and opening task", () -> {

            VendorPage.openAndEnterReviewNewVendorTask();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
        });

        // -------------------------
        // Approve FLOW HANDLING
        // -------------------------
        Allure.step("REVIEW NEW VENDOR TASK - Validating APPROVE E2E Flow", () -> {

            VendorPage.handleReviewNewVendorFields();
        });
    }

    // =========================================================
    // RE LOGIN AS VENDOR MANAGER
    // =========================================================
    private void reloginAsVendorLegal(String username, String password) {

        Allure.step("Relaunch application and relogin", () -> {

            // IMPORTANT
            // Navigate back to login page first

            driver.get(ConfigReader.getData("base.url"));

            WebDriverWait wait =
                    new WebDriverWait(driver,
                            Duration.ofSeconds(30));

            // WAIT FOR LOGIN PAGE
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("un")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful as Vendor Manager");
        });
    }

    // =========================================================
    // REVIEW OFAC NEW VENDOR APPROVE FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskNewOFACVendor() {

        ReviewOFACNewVendorPage OFACPage =
                new ReviewOFACNewVendorPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("REVIEW OFAC NEW VENDOR TASK - Navigation and opening task", () -> {

            OFACPage.openAndEnterReviewOFACNewVendorTask();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
        });

        // -------------------------
        // Approve FLOW HANDLING
        // -------------------------
        Allure.step("REVIEW OFAC NEW VENDOR TASK - Validating APPROVE E2E Flow", () -> {

            OFACPage.handleReviewOFACNewVendorFields();
        });
    }

    // =========================================================
    // RE LOGIN AS CMS OUTSIDE COUNSEL
    // =========================================================
    private void reloginAsIternalCounsel(String username, String password) {

        Allure.step("Relaunch application and relogin", () -> {

            // IMPORTANT
            // Navigate back to login page first

            driver.get(ConfigReader.getData("base.url"));

            WebDriverWait wait =
                    new WebDriverWait(driver,
                            Duration.ofSeconds(30));

            // WAIT FOR LOGIN PAGE
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("un")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful");
        });
    }

    // =========================================================
    // REVIEW CONFLICT WAIVER APPROVE FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskConflictWaiver() {

        ReviewConflictWaiverPage ConflictPage =
                new ReviewConflictWaiverPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("REVIEW CONFLICT WAIVER TASK - Navigation and opening task", () -> {

            ConflictPage.openAndEnterReviewConflictWaiverTask();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
        });

        // -------------------------
        // Approve FLOW HANDLING
        // -------------------------
        Allure.step("REVIEW CONFLICT WAIVER TASK - Validating APPROVE E2E Flow", () -> {

            ConflictPage.handleReviewConflictWaiverFields();
        });
    }

    // =========================================================
    // EXECUTE AND UPLOAD ENGAGEMENT LETTER APPROVE FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskExecuteEL() {

        ExecuteAndUploadELPage ELPage =
                new ExecuteAndUploadELPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("EXECUTE AND UPLOAD ENGAGEMENT LETTER TASK - Navigation and opening task", () -> {

            ELPage.openAndEnterExecuteUploadELTask();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
        });

        // -------------------------
        // Approve FLOW HANDLING
        // -------------------------
        Allure.step("REVIEW NEW VENDOR TASK - Validating APPROVE E2E Flow", () -> {

            ELPage.handleExecuteUploadELFields();
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
    }
}
        // =========================================================
        // Update and Cancel Request
        // =========================================================
    /*private void relatedAction() {

        Update_CancelRequestPage actionPage = new Update_CancelRequestPage(driver);
        Allure.step("Cancel Request Fields", () -> {

            //actionPage.handleEnterUpdateRequestFields();
            actionPage.handleEnterCancelRequestFields();
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
    } */