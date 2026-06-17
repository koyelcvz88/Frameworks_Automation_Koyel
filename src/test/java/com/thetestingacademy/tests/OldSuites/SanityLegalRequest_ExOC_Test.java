/*package com.thetestingacademy.tests.OldSuites;

import com.thetestingacademy.Removed.BaseTest;
import com.thetestingacademy.pagesHC.ConfirmWorkCompletionPage;
import com.thetestingacademy.pages.DashboardPage;
import com.thetestingacademy.pagesHC.ExOCSummaryPage;
import com.thetestingacademy.pagesHC.Ex_OC_Request_NavigatorPage;
import com.thetestingacademy.pagesHC.LegalRequestExistingOCPage;
import com.thetestingacademy.pagesHC.LegalRequestFormPage;
import com.thetestingacademy.pagesHC.LegalRequestInitiationPage;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

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
        Allure.step("Task 1 - Navigation and opening task", () -> {

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
        Allure.step("Task 1 - Validating and selecting fields", () -> {

            taskPage.handleConfirmWorkCompletionFields();
        });
    }
} */