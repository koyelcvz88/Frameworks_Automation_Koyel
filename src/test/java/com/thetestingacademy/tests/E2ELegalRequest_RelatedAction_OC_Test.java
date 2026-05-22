package com.thetestingacademy.tests;

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
@Feature("E2E Suite - Update and Cancel Request End-to-End Test Flow")
public class E2ELegalRequest_RelatedAction_OC_Test extends BaseTest {

    @Test(groups = {"E2E"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Flow - EX OC Request + Navigation + Update Request + Cancel Request ")
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

        // =========================================================
        // Update and Cancel Request
        // =========================================================
        relatedAction();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "E2E Flow Completed for Related action Test",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("E2E Flow Completed for Related action Successfully");
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
    // Update and Cancel Request
    // =========================================================
    private void relatedAction() {

        Update_CancelRequestPage actionPage = new Update_CancelRequestPage(driver);
        Allure.step("Update Request Fields", () -> {
            actionPage.handleEnterUpdateRequestFields();
            SceenshotUtil.takeScreenshot(driver,
                     "Update Request Related Action Flow Completed");
        });

        Allure.step("Cancel Request Fields", () -> {
            actionPage.handleEnterCancelRequestFields();
            SceenshotUtil.takeScreenshot(driver,
                    "Cancel Request Related Action Flow Completed");
        });
    }
}