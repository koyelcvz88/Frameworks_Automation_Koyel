package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@Epic("VenReq Automation Suite")
@Feature("E2E Suite - NEW OC End-to-End Task Flow")
public class E2ELegalRequest_NewOC_Test extends BaseTest {

    @Test(groups = {"E2E"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Flow - NEW OC Request + Navigation + All Task Approve Flow")
    public void e2e_new_oc_flow() {

        // =========================
        // OPEN APPLICATION
        // =========================
        openApplication();

        // =========================
        // LOGIN
        // =========================
        DashboardPage dashboard = loginAs("new");

        Assert.assertTrue(dashboard.isLoaded(), "NEW OC Login failed");

        // =========================
        // INITIATION
        // =========================
        performInitiation();

        // =========================
        // COMMON FORM
        // =========================
        performFormFill();

        // =========================
        // CREATE NEW OC REQUEST
        // =========================
        performNewOCRequest();

        // =========================
        // NAVIGATE HOME + OPEN REQUEST
        // =========================
        Allure.step("Navigate Home + Open NEW OC Request", () -> {
            new New_OC_Request_NavigatorPage(driver).newOCRequest();
        });

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
        navigateToSummaryAndTasks();

        // STEP - RELOGIN AS LEGAL SENIOR MANAGER
        reloginAsSeniorLegal(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // STEP - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
        navigateToSummaryAndTasks();

        // =========================
        // REVIEW NEW VENDOR TASK EXECUTION
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

        // STEP - RELOGIN AS LEGAL SENIOR MANAGER
        reloginAsSeniorLegal(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // STEP - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
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

        // =========================================================
        // CONFLICT WORK COMPLETION FLOW TASK EXECUTION
        // =========================================================
        executeTaskCC();

        // =========================================================
        // ENTER INVOICE PAYMENT CONFIRMATION FLOW TASK EXECUTION
        // =========================================================
        executeTaskPC();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "E2E Flow Completed for NEW OC",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("E2E Flow Completed for NEW OC Successfully");
    }

    // =========================================================
    // INITIATION
    // =========================================================
    private void performInitiation() {

        LegalRequestInitiationPage page =
                new LegalRequestInitiationPage(driver);

        page.clickInitiateLegalRequest();

        Assert.assertTrue(
                page.isFormLoaded() && page.isBannerDisplayed(),
                "Initiation failed"
        );
    }

    // =========================================================
    // COMMON FORM
    // =========================================================
    private void performFormFill() {

        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver);

        formPage.fillLegalRequestForm();

        Allure.step("Common form filled successfully");
    }

    // =========================================================
    // EX OC REQUEST CREATION
    // =========================================================
    private void performNewOCRequest() {

        LegalRequestNewOCPage2 newOCPage2 =
                new LegalRequestNewOCPage2(driver);

        Allure.step("Executing EX OC Request Flow",
                newOCPage2::fillNewOCRequest);

        Assert.assertNotNull(
                TestData.newOCRequestNumber,
                "NEW OC Request Number not generated"
        );

        Allure.step("NEW OC Request Created: " + TestData.newOCRequestNumber);
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
    // RE LOGIN AS SENIOR LEGAL MANAGER
    // =========================================================
    private void reloginAsSeniorLegal(String username,
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
                    //By.id("un")));
            By.xpath("//input[@placeholder='Username']")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful as Sr. legal Manager");
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
    // REVIEW NEW VENDOR TASK EXECUTION
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
        // TASK FIELDS HANDLING
        // -------------------------
        Allure.step("REVIEW NEW VENDOR TASK - Validating and selecting fields", () -> {

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
                    // By.id("un")));
            By.xpath("//input[@placeholder='Username']")));

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

            OFACPage. openAndEnterReviewOFACNewVendorTask();
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
                    //By.id("un")));
            By.xpath("//input[@placeholder='Username']")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful as Internal Counsel");
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
                    //By.id("un")));
            By.xpath("//input[@placeholder='Username']")));

            LoginPage loginPage = new LoginPage(driver);

            DashboardPage dashboard = loginPage
                    .enterUsername(username)
                    .enterPassword(password)
                    .clickSignin();

            Assert.assertTrue(dashboard.isLoaded(),
                    "Relogin failed for user: " + username);

            System.out.println("✅ Relogin successful as Legal Manager");
        });
    }

    // =========================================================
    // CONFLICT WORK COMPLETION FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskCC() {

        ConfirmWorkCompletionPage taskPage = new ConfirmWorkCompletionPage(driver);

        // -------------------------------
        // TASK NAVIGATION
        // -------------------------------
        Allure.step("CONFLICT WORK COMPLETION Task  - Navigation and opening task", () -> {

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
        Allure.step("CONFLICT WORK COMPLETION Task - Validating and selecting fields", () -> {

            taskPage.handleConfirmWorkCompletionFields();
            //taskPage.handleConfirmWorkCompletionEdgeFlow();
        });
    }

    // =========================================================
    // ENTER INVOICE PAYMENT CONFIRMATION FLOW TASK EXECUTION
    // =========================================================

    private void executeTaskPC() {

        EnterInvoice_PaymentConfirmationPage task2Page =
                new EnterInvoice_PaymentConfirmationPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("ENTER INVOICE PAYMENT CONFIRMATION Task  - Navigation and opening task", () -> {

            task2Page.openAndEnterInvoicepaymentConfirmationTask();
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
        Allure.step("ENTER INVOICE PAYMENT CONFIRMATION Task  - Validating and selecting fields", () -> {

            task2Page.handleEnterInvoiceandPaymentConfirmationFields();
        });
    }
}