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
@Feature("E2E Suite - Update and Cancel Request End-to-End Test Flow")
public class E2ELegalRequest_RelatedAction_OC_Test extends BaseTest {

    @Test(groups = {"E2E"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Flow - EX OC Request + Navigation + Update Request + Cancel Request ")
    public void e2e_ex_oc_relActflow() {

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

        boolean ok = page.isFormLoaded() && page.isBannerDisplayed();
        Assert.assertTrue(ok, "Initiation failed");
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