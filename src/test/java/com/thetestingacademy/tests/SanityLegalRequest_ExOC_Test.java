package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.enums.FlowType;
import com.thetestingacademy.enums.SuiteType;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.pages.Ex_OC_Request_NavigatorPage;
import com.thetestingacademy.pages.ConfirmWorkCompletionPage;
import com.thetestingacademy.pages.ExOCSummaryPage;
import com.thetestingacademy.utils.TestDataManager;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@Epic("VenReq Automation Suite")
@Feature("Sanity Suite - EX OC End-to-End Task Flow")
public class SanityLegalRequest_ExOC_Test extends BaseTest {

    @Test(groups = {"Sanity"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Sanity Flow - EX OC Request + Navigation + Task 1 Execution")
    public void sanity_ex_oc_flow() {

        // =========================
        // OPEN APPLICATION
        // =========================
        openApplication();

        // =========================
        // LOGIN
        // =========================
        DashboardPage dashboard = loginAs("existing");
        Assert.assertTrue(dashboard.isLoaded(), "EX OC Login failed");

        // =========================
        // INIT DATA
        // =========================
        TestDataManager testDataManager = TestDataManager.getInstance();

        testDataManager.initForSuite(SuiteType.E2E, FlowType.EXISTING_OC);

        dataModel = testDataManager.getNextData();

        Assert.assertNotNull(dataModel, "DataModel is NULL from TestDataManager");

        System.out.println("===== SELECTED DATA =====");
        System.out.println(dataModel);

        // =========================
        // INITIATION
        // =========================
        performInitiation();

        // =========================
        // COMMON FORM
        // =========================
        performFormFill();

        // =========================
        // CREATE EX OC REQUEST
        // =========================
        performExistingOCRequest();

        // =========================
        // NAVIGATE HOME + OPEN REQUEST
        // =========================
        Allure.step("Navigate Home + Open EX OC Request", () -> {
            new Ex_OC_Request_NavigatorPage(driver, dataModel).exOCRequest();
        });

        // =========================
        // SUMMARY + TASK NAVIGATION
        // =========================
        navigateToSummaryAndTasks();

        // =========================
        // TASK 1 EXECUTION
        // =========================
        executeTask1();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Sanity Flow Completed for EX OC",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("Sanity Flow Completed for EX OC Successfully");
    }

    // =========================================================
    // INITIATION
    // =========================================================
    private void performInitiation() {

        LegalRequestInitiationPage page =
                new LegalRequestInitiationPage(driver);

        page.clickInitiateLegalRequest();

        boolean ok = page.isFormLoaded() && page.isBannerDisplayed();
        Assert.assertTrue(ok, "Initiation failed");

        /*Assert.assertTrue(
                page.isFormLoaded() && page.isBannerDisplayed(),
                "Initiation failed"
        ); */
    }

    // =========================================================
    // COMMON FORM
    // =========================================================
    private void performFormFill() {

        System.out.println("===== DATA PASSED =====");
        System.out.println(dataModel);

        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver, dataModel);

        formPage.fillLegalRequestForm();

        Allure.step("Common form filled successfully");
    }

    // =========================================================
    // EX OC REQUEST CREATION
    // =========================================================
    private void performExistingOCRequest() {

        dataModel.validateOrFail();

        LegalRequestExistingOCPage existingOCPage =
                new LegalRequestExistingOCPage(driver, dataModel);

        Allure.step("Executing Existing OC Flow", existingOCPage::fillExistingOCRequest);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        boolean flowCompleted;

        try {
            flowCompleted = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(text(),'Legal Matter Request Initiated!')]")
                    )
            ).isDisplayed();
        } catch (Exception e) {
            flowCompleted = false;
        }

        Assert.assertTrue(flowCompleted, "EX OC Flow failed");
    }

    // =========================================================
    // SUMMARY NAVIGATION
    // =========================================================
    private void navigateToSummaryAndTasks() {

        ExOCSummaryPage summary = new ExOCSummaryPage(driver);

        boolean summaryLoaded = summary.isSummaryPageLoaded();

        SceenshotUtil.takeScreenshot(driver, summaryLoaded ? "Summary Page Loaded"
                : "Summary Page Not Loaded");

        Assert.assertTrue(summaryLoaded, "Summary page not loaded");

        summary.navigateToTasksTab();

        SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");

        Allure.step("Summary page loaded and Tasks tab opened successfully");
        SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");
    }

    // =========================================================
    // TASK 1 EXECUTION
    // =========================================================
    private void executeTask1() {

        ConfirmWorkCompletionPage taskPage = new ConfirmWorkCompletionPage(driver, dataModel);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("Task 1 - Navigation and opening task", () -> {

            taskPage.openAndEnterConfirmWorkCompletionTask();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        Allure.step("Validate Task URL opened", () -> {

            String currentUrl = driver.getCurrentUrl();

            boolean taskOpened =
                    currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(
                    driver,
                    taskOpened
                            ? "Task URL Verified"
                            : "Task URL Mismatch"
            );

            Assert.assertTrue(
                    taskOpened,
                    "Task URL not opened correctly. URL: " + currentUrl
            );
        });

        // -------------------------
        // TASK FIELDS HANDLING
        // -------------------------
        Allure.step("Task 1 - Validating and selecting fields", () -> {

            taskPage.handleConfirmWorkCompletionFields();
        });

        // -------------------------
        // Final Step
        // -------------------------
        Allure.step("Confirm Work Completion task executed successfully", () -> {

            SceenshotUtil.takeScreenshot(driver,
                    "Confirm Work Completion task executed successfully"
            );
        });
    }
}