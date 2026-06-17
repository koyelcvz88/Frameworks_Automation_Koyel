package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.enums.FlowType;
import com.thetestingacademy.enums.SuiteType;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.TestDataManager;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@Epic("VenReq Automation Suite")
@Feature("Smoke Suite - Full Critical Path")
public class SmokeLegalRequestTest extends BaseTest {

    @Test(groups = {"Smoke"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Smoke Flow - Login + Initiation + Form + EX OC Request")
    public void smoke_full_existing_oc_flow() {

        openApplication();

        // =========================
        // LOGIN
        // =========================
        DashboardPage dashboard = loginAs("existing");
        Assert.assertTrue(dashboard.isLoaded(), "Login failed");

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
        // EX OC REQUEST FLOW
        // =========================
        performExistingOCRequest();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Smoke Flow Completed",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("Smoke Flow Completed Successfully");
    }

    // =========================
    // INITIATION
    // =========================
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

    // =========================
    // COMMON FORM
    // =========================
    private void performFormFill() {

        System.out.println("===== DATA PASSED =====");
        System.out.println(dataModel);

        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver, dataModel);

        formPage.fillLegalRequestForm();

        Allure.step("Common form filled successfully");
    }

    // =========================
    // EX OC REQUEST FLOW
    // =========================
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

        // =========================
        // STORE REQUEST ID
        // =========================
        String exOCRequestNumber = TestData.exOCRequestNumber;

        TestData.exOCRequestNumber = exOCRequestNumber;

        Allure.step("EX OC Request Stored: " + exOCRequestNumber);
    }
}