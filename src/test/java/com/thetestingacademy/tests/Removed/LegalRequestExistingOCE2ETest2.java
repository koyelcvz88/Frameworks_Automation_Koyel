/*package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

//@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("E2E - TC1 + TC2 + TC3 Existing OC Flow")
public class LegalRequestExistingOCE2ETest2 extends BaseTest {

    @Test
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Flow - Login → Initiate → Form → Existing OC")
    public void e2e_existing_oc_flow() throws InterruptedException {

       /*openApplication("https://trimont-test.appiancloud.com/suite/portal/login.jsp");

        // ================= LOGIN =================
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboard = Allure.step("Login to Application", () ->
                loginPage.login("AM_CMSTest", "Maantic@2026")
        );

        // ================= Login using Config =================

        //openApplication();
        LoginPage loginPage = new LoginPage(driver);

        String username = ConfigReader.getData("existing.username");
        String password = ConfigReader.getData("existing.password");

        DashboardPage dashboard = Allure.step("Login to Application", () ->
                loginPage.login(username, password)
        );

// ================= VALIDATION =================
        Allure.step("Validate Login Success", () -> {

            boolean result = dashboard.isLoaded(); // ✅ FIXED

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment("Login Screenshot", "image/png",
                    new ByteArrayInputStream(screenshot), ".png");

            Assert.assertTrue(result, "Login failed");
        });

        // ================= INITIATE =================
        LegalRequestInitiationPage initiationPage = new LegalRequestInitiationPage(driver);

// Step 1: Click on "Initiate Legal Request"
        Allure.step("Click Initiate Legal Request", initiationPage::clickInitiateLegalRequest);

// Step 2: Validate the Initiation Screen Loaded
        boolean isFormLoaded = Allure.step("Validate Initiation Form Loaded", () -> {
            boolean result = initiationPage.isFormLoaded();
            captureScreenshotEx("Failure Screenshot", result);
            return result;
        });
        // Step 3: Validate the Banner Visibility
        boolean isBannerVisible = Allure.step("Validate Banner is Visible", () -> {
            boolean result = initiationPage.isBannerDisplayed();
            captureScreenshotEx("Failure Screenshot", result);
            return result;
        });

// Step 4: Assertion
        Allure.step("Validating the form and banner visibility", () -> {
            // This assertion checks if the form is loaded and the banner is visible on the initiation screen
            Assert.assertTrue(isFormLoaded && isBannerVisible, "Initiation screen validation failed. Form loaded: " + isFormLoaded + ", Banner visible: " + isBannerVisible);
        });

        // ================= FORM =================
        LegalRequestFormPage formPage = new LegalRequestFormPage(driver);

        Allure.step("Fill Legal Request Form", formPage::fillLegalRequestForm);

        Allure.step("Validate Form Loaded", () ->
                Assert.assertTrue(driver.getPageSource().contains("Request Type")));

        // ================= EXISTING OC =================
        LegalRequestExistingOCPage existingOCPage = new LegalRequestExistingOCPage(driver);

        // Step 1: Fill out the Existing OC Request Form
        Allure.step("Fill Existing OC Request", existingOCPage::fillExistingOCRequest);

        // 🔥 WAIT AFTER SUBMIT (to handle dynamic validation)
        /* new WebDriverWait(driver, Duration.ofSeconds(90))
                .until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[contains(text(),'OC Firm Name')]")
                        ),
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[contains(text(),'Contact Attorney')]")
                        )
                ));

        // ================= OC VALIDATION =================
        Allure.step("Validate OC Firm", () -> {
            String actualFirm = new WebDriverWait(driver, Duration.ofSeconds(90))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//strong[normalize-space()='OC Firm Name']/following::em[1]")))
                    .getText()
                    .trim();

            // Assert that the OC Firm matches the expected value
            Assert.assertEquals(actualFirm, existingOCPage.selectedOCFirm, "OC Firm mismatch");

            // Capture a screenshot and add it to Allure
            Allure.addAttachment("OC Firm Screenshot",
                    new ByteArrayInputStream(((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES)));

            System.out.println("OC Firm validated: " + actualFirm);
        });

        // ================= Attorney VALIDATION =================
         Allure.step("Validate Attorney", () -> {
            String actualAttorney = new WebDriverWait(driver, Duration.ofSeconds(90))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//strong[normalize-space()='Contact Attorney']/following::em[1]")))
                    .getText()
                    .trim();

            // Assert that the Attorney matches the expected value
            Assert.assertEquals(actualAttorney, existingOCPage.selectedAttorney, "Attorney mismatch");

            // Capture a screenshot and add it to Allure
            Allure.addAttachment("Attorney Screenshot",
                    new ByteArrayInputStream(((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES)));

            System.out.println("Attorney validated: " + actualAttorney);
        });

        // ================= FINAL =================
        Allure.step("Final Existing OC Validation", () -> {
            Assert.assertTrue(driver.getCurrentUrl().contains("vendor-req"), "Ex OC E2E Failed");
            captureScreenshotEx("Final Screenshot");
        });

        System.out.println("✅ Ex OC E2E PASSED");
    }

    /* // ===================== Helper =====================
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            // Optionally log the interruption
        }
    } */
    // ===================== Utility Methods =====================

    /*private String validateFieldAndGetValue(String xpath, String fieldType) {
        return new WebDriverWait(driver, Duration.ofSeconds(5))  // Reduced wait time for demo purposes
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
                .getAttribute("value");
    }

    private void captureScreenshotEx(String attachmentName) {
        Allure.addAttachment(attachmentName,
                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    private void captureScreenshotEx(String attachmentName, boolean condition) {
        if (!condition) {
            Allure.addAttachment(attachmentName,
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }

} */