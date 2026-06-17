package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.enums.FlowType;
import com.thetestingacademy.enums.SuiteType;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.pages.Ex_OC_Request_NavigatorPage;
import com.thetestingacademy.pages.ConfirmWorkCompletionPage;
import com.thetestingacademy.pages.ExOCSummaryPage;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestDataManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.ByteArrayInputStream;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

@Epic("VenReq Automation Suite")
@Feature("E2E Suite - EX OC End-to-End Task Flow")
public class E2ELegalRequest_ExOC_Test extends BaseTest {

    @Test(groups = {"E2E"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Flow - EX OC Request + Navigation + Task 1 Execution + Task 2 Execution")
    public void e2e_ex_oc_flow() {

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
        // TASK 2 EXECUTION
        // =========================
        executeTask2();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "E2E Flow Completed for EX OC",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("E2E Flow Completed for EX OC Successfully");
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

        Allure.step("Navigate to Summary page and open Tasks tab", () -> {

            ExOCSummaryPage summary = new ExOCSummaryPage(driver);

            boolean summaryLoaded = summary.isSummaryPageLoaded();

            SceenshotUtil.takeScreenshot(driver, summaryLoaded ? "Summary Page Loaded"
                    : "Summary Page Not Loaded");

            Assert.assertTrue(summaryLoaded, "Summary page not loaded");

            summary.navigateToTasksTab();

            SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");

            Allure.step("Summary page loaded and Tasks tab opened successfully");
            SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");
        });
    }

    // =========================================================
    // TASK 1 EXECUTION
    // =========================================================
    private void executeTask1() {

        ConfirmWorkCompletionPage taskPage = new ConfirmWorkCompletionPage(driver, dataModel);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("CONFLICT WORK COMPLETION Task  - Navigation and opening task", () -> {

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
            System.out.println("✅ Task URL verified: " + currentUrl);
        });

        // -------------------------
        // TASK FIELDS HANDLING
        // -------------------------
        Allure.step("CONFLICT WORK COMPLETION Task - Validating and selecting fields", () -> {

            taskPage.handleConfirmWorkCompletionFields();
        });
    }

    // =========================================================
    // TASK 2 EXECUTION
    // =========================================================
    private void executeTask2() {

        EnterInvoice_PaymentConfirmationPage task2Page =
                new EnterInvoice_PaymentConfirmationPage(driver, dataModel);

        // -------------------------
        // TASK NAVIGATION
        // -------------------------
        Allure.step("ENTER INVOICE PAYMENT CONFIRMATION Task - Navigation and opening task", () -> {

            task2Page.openAndEnterInvoicepaymentConfirmationTask();
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
        Allure.step("ENTER INVOICE PAYMENT CONFIRMATION Task - Validating and selecting fields", () -> {

            task2Page.handleEnterInvoiceandPaymentConfirmationFields();
        });
    }
}