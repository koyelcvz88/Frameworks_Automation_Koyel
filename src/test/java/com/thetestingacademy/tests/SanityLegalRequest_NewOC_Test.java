package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.enums.FlowType;
import com.thetestingacademy.enums.SuiteType;
import com.thetestingacademy.pages.*;
import com.thetestingacademy.utils.TestData;
import com.thetestingacademy.utils.TestDataManager;
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
@Feature("Sanity Suite - New OC Flow")
@Story("Sanity - New OC Request Creation")

public class SanityLegalRequest_NewOC_Test extends BaseTest {

    @Test(groups = {"Sanity"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Sanity Flow - New OC (Login + Initiation + Form + Request Creation)")

    public void sanity_new_oc_flow() {

        openApplication();

        // =========================
        // LOGIN
        // =========================
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboard = Allure.step("Login as New OC", () -> {
            return loginPage
                    .enterUsername(ConfigReader.getData("new.username"))
                    .enterPassword(ConfigReader.getData("new.password"))
                    .clickSignin();
        });

        Assert.assertTrue(dashboard.isLoaded(), "New OC login failed");

        // =========================
        // INIT DATA
        // =========================
        TestDataManager testDataManager = TestDataManager.getInstance();

        testDataManager.initForSuite(SuiteType.E2E, FlowType.NEW_OC);

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
        performCommonForm();

        // =========================
        // CREATE NEW OC REQUEST
        // =========================
        performNewOCRequest();

        // =========================
        // FINAL SCREENSHOT
        // =========================
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Sanity New OC Completed",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.step("Sanity New OC Flow Completed Successfully");
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
                "Initiation failed for New OC"
        ); */
    }

    // =========================================================
    // COMMON FORM
    // =========================================================
    private void performCommonForm() {

        System.out.println("===== DATA PASSED =====");
        System.out.println(dataModel);

        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver , dataModel);

        formPage.fillLegalRequestForm();

        Allure.step("Common form filled successfully");
    }

    // =========================================================
    // CREATE NEW OC REQUEST
    // =========================================================
    private void performNewOCRequest() {

        dataModel.validateOrFail();

        LegalRequestNewOCPage2 newOCPage =
                new LegalRequestNewOCPage2(driver, dataModel);

        Allure.step("Executing New OC Request Flow", newOCPage::fillNewOCRequest);

        // -------------------------
        // VALIDATION
        // -------------------------
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        /*Assert.assertTrue(
                wait.until(d -> driver.getPageSource()
                        .contains("Legal Matter Request Initiated!")),
                "New OC request not created successfully"
        ); */

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

        Assert.assertTrue(flowCompleted, "NEW OC Flow failed");

        // -------------------------
        // REQUEST ID CAPTURE
        // -------------------------
        /*Assert.assertNotNull(
                TestData.newOCRequestNumber,
                "New OC Request Number is NULL"
        ); */

        String newOCRequestNumber = TestData.newOCRequestNumber;

        TestData.newOCRequestNumber = newOCRequestNumber;

        Allure.step("New OC Request Created: " + TestData.newOCRequestNumber);
    }
}