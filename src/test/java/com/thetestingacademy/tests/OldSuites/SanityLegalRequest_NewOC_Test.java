/*package com.thetestingacademy.tests.OldSuites;

import com.thetestingacademy.Removed.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pagesHC.LegalRequestFormPage;
import com.thetestingacademy.pagesHC.LegalRequestInitiationPage;
import com.thetestingacademy.pagesHC.LegalRequestNewOCPage2;
import com.thetestingacademy.pagesHC.LoginPage;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

        Assert.assertTrue(
                page.isFormLoaded() && page.isBannerDisplayed(),
                "Initiation failed for New OC"
        );
    }

    // =========================================================
    // COMMON FORM
    // =========================================================
    private void performCommonForm() {

        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver);

        Allure.step("Filling Common Legal Request Form", formPage::fillLegalRequestForm);
    }

    // =========================================================
    // CREATE NEW OC REQUEST
    // =========================================================
    private void performNewOCRequest() {

        LegalRequestNewOCPage2 newOCPage =
                new LegalRequestNewOCPage2(driver);

        Allure.step("Executing New OC Request Flow", newOCPage::fillNewOCRequest);

        // -------------------------
        // VALIDATION
        // -------------------------
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        Assert.assertTrue(
                wait.until(d -> driver.getPageSource()
                        .contains("Legal Matter Request Initiated!")),
                "New OC request not created successfully"
        );

        // -------------------------
        // REQUEST ID CAPTURE
        // -------------------------
        Assert.assertNotNull(
                TestData.newOCRequestNumber,
                "New OC Request Number is NULL"
        );

        Allure.step("New OC Request Created: " + TestData.newOCRequestNumber);
    }
} */