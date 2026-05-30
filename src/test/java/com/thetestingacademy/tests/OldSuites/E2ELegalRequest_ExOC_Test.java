package com.thetestingacademy.tests.OldSuites;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

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
            new Ex_OC_Request_NavigatorPage(driver).exOCRequest();
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
    private void performExistingOCRequest() {

        LegalRequestExistingOCPage existingOCPage =
                new LegalRequestExistingOCPage(driver);

        Allure.step("Executing EX OC Request Flow",
                existingOCPage::fillExistingOCRequest);

        Assert.assertNotNull(
                TestData.exOCRequestNumber,
                "EX OC Request Number not generated"
        );

        Allure.step("EX OC Request Created: " + TestData.exOCRequestNumber);
    }

    // =========================================================
    // SUMMARY NAVIGATION
    // =========================================================
    private void navigateToSummaryAndTasks() {

        Allure.step("Navigate to Summary page and open Tasks tab", () -> {

            ExOCSummaryPage summary = new ExOCSummaryPage(driver);

            Assert.assertTrue(summary.isSummaryPageLoaded(),
                    "Summary page not loaded");

            summary.navigateToTasksTab();

            SceenshotUtil.takeScreenshot(driver, "Tasks Tab Opened");
        });
    }

    // =========================================================
    // TASK 1 EXECUTION
    // =========================================================
    private void executeTask1() {

        ConfirmWorkCompletionPage taskPage =
                new ConfirmWorkCompletionPage(driver);

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

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
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
                new EnterInvoice_PaymentConfirmationPage(driver);

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

            boolean isOpened = currentUrl.contains("/start-process/");

            SceenshotUtil.takeScreenshot(driver,
                    isOpened ? "Task URL Verified" : "Task URL Mismatch");

            Assert.assertTrue(isOpened,
                    "Task URL not opened correctly. URL: " + currentUrl);
        });

        // -------------------------
        // TASK FIELDS HANDLING
        // -------------------------
        Allure.step("ENTER INVOICE PAYMENT CONFIRMATION Task - Validating and selecting fields", () -> {

            task2Page.handleEnterInvoiceandPaymentConfirmationFields();
        });
    }
}