package com.thetestingacademy.tests.OldSuites;

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
@Feature("Regression Suite - NEW OC End-to-End EDGE Flow")
public class RegressionLegalRequest_NewOC_Test extends BaseTest {

    @Test(groups = {"Regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Regression Edge Flow - NEW OC Request + Navigation + Task 1 Reject + Task 1 Approve + Task 2 Reject Flow")
    public void regression_new_oc_flow() {

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

        // - RELOGIN AS LEGAL SENIOR MANAGER
        reloginAsSeniorLegal(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
        navigateToSummaryAndTasks();

        // =========================
        // REVIEW NEW VENDOR REJECT FLOW TASK EXECUTION
        // =========================
        executeTaskNewVendorEdge();

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
        // REQUEST RESUBMISSION TASK
        // =========================
        executeTaskrequestResub();

        // - RELOGIN AS LEGAL SENIOR MANAGER
        reloginAsSeniorLegal(
                ConfigReader.getData("newSrLegal.username"),
                ConfigReader.getData("newSrLegal.password")
        );

        // - Navigate to Home + Teams tab + Open request
        openRequestTeamsTab();

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
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
        // REVIEW OFAC NEW VENDOR TASK EDGE
        // =========================
        executeTaskNewOFACVendorEdge();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Regression Flow Completed for NEW OC",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("Regression Flow Completed for NEW OC Successfully");
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
    // NEW OC REQUEST CREATION
    // =========================================================
    private void performNewOCRequest() {

        LegalRequestNewOCPage2 newOCPage2 =
                new LegalRequestNewOCPage2(driver);

        Allure.step("Executing NEW OC Request Flow",
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
                    By.id("un")));

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
    // REVIEW NEW VENDOR REJECT FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskNewVendorEdge() {

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
        // EDGE FLOW HANDLING
        // -------------------------
        Allure.step("REVIEW NEW VENDOR TASK - Validating EDGE Flow", () -> {

            VendorPage.handleReviewNewVendorEdgeFlow();
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
    // RE LOGIN AS SENIOR LEGAL MANAGER
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

            System.out.println("✅ Relogin successful as Legal Manager");
        });
    }

    // =========================================================
    // REQUEST RESUBMISSION TASK EXECUTION
    // =========================================================
    private void executeTaskrequestResub() {

        RequestResubmissionPage ResubPage =
                new RequestResubmissionPage(driver);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("REQUEST RESUBMISSION TASK - Navigation and opening task", () -> {

            ResubPage.openAndEnterRequestResubmissionTask();
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
        Allure.step("REQUEST RESUBMISSION TASK - Validating EDGE Flow", () -> {

            ResubPage.handleRequestResubmissionFields();
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
    // REVIEW OFAC NEW VENDOR REJECT FLOW TASK EXECUTION
    // =========================================================
    private void executeTaskNewOFACVendorEdge() {

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
        // EDGE FLOW HANDLING
        // -------------------------
        Allure.step("REVIEW OFAC NEW VENDOR TASK - Validating EDGE Flow", () -> {

            OFACPage.handleReviewOFACNewVendorEdgeFlow();
        });
    }
}
