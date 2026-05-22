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
@Story("E2E - TC1 + TC2 + TC4 New OC Flow")
public class LegalRequestNewOCE2ETest2 extends BaseTest {

    @Test
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("E2E Flow - Login → Initiate → Form → New OC")
    public void e2e_new_oc_flow() {

        /*openApplication("https://trimont-test.appiancloud.com/suite/portal/login.jsp");

        // ================= LOGIN =================
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard = Allure.step("Login to Application", () ->
                loginPage.login("AM_CMSTest", "Maantic@2026")
        );

        // ================= Login using Config =================

        //openApplication();
        LoginPage loginPage = new LoginPage(driver);

        String username = ConfigReader.getData("new.username");
        String password = ConfigReader.getData("new.password");

        DashboardPage dashboard = Allure.step("Login to Application", () ->
                loginPage.login(username, password)
        );

        // ================= VALIDATION =================
        Allure.step("Validate Login Success", () -> {
            boolean result = dashboard.isLoaded();

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment("Login Screenshot", "image/png",
                    new ByteArrayInputStream(screenshot), ".png");

            Assert.assertTrue(result, "Login failed");
        });

        // ================= INITIATE =================
        LegalRequestInitiationPage initiationPage = new LegalRequestInitiationPage(driver);
        Allure.step("Click Initiate Legal Request", initiationPage::clickInitiateLegalRequest);

        // Step 2: Validate the Initiation Screen Loaded
        boolean isFormLoaded = Allure.step("Validate Initiation Form Loaded", () -> {
            boolean result = initiationPage.isFormLoaded();
            captureScreenshot("Failure Screenshot", result);
            return result;
        });

        // Step 3: Validate the Banner Visibility
        boolean isBannerVisible = Allure.step("Validate Banner is Visible", () -> {
            boolean result = initiationPage.isBannerDisplayed();
            captureScreenshot("Failure Screenshot", result);
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

        // ================= NEW OC =================
        LegalRequestNewOCPage2 newOCPage = new LegalRequestNewOCPage2(driver);
        Allure.step("Fill New OC Request Form", newOCPage::fillNewOCRequest);

        // 🔥 WAIT AFTER SUBMIT (Reduced timeout)
       /* waitForElement("//strong[normalize-space()='Phone']", 10);  // 10 seconds for demo purpose

        // ================= PHONE VALIDATION =================
        Allure.step("Validate New OC - Phone Field", () -> {
            String phoneValue = validateFieldAndGetValue("//strong[normalize-space()='Phone']/following::input[1]", "phone");
            Assert.assertTrue(phoneValue.matches("\\d{10}"), "Invalid phone number");
            captureScreenshot("Phone Validation Screenshot");
            System.out.println("Phone: " + phoneValue);
        });

        // ================= EMAIL VALIDATION =================
        Allure.step("Validate New OC - Email Field", () -> {
            String emailValue = validateFieldAndGetValue("//strong[normalize-space()='Email']/following::input[1]", "email");
            Assert.assertTrue(emailValue.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"),
                    "Invalid email format");
            captureScreenshot("Email Validation Screenshot");
            System.out.println("Email: " + emailValue);
        });

        // ----------- RETRY VALIDATION STEP -----------
        /*retryValidation(() -> {
            waitForElement("//strong[normalize-space()='Phone']", 10); // Just wait for the element, no need to return anything
        }, 3, 1000); */  // 3 retries with 1s interval

        // ================= FINAL =================
       /* Allure.step("Final New OC Validation", () -> {
            Assert.assertTrue(driver.getCurrentUrl().contains("vendor-req"), "New OC E2E Failed");
            captureScreenshot("Final Screenshot");
        });

        System.out.println("✅ NEW OC E2E PASSED");
    }

    // ===================== Utility Methods =====================

    private String validateFieldAndGetValue(String xpath, String fieldType) {
        return new WebDriverWait(driver, Duration.ofSeconds(5))  // Reduced wait time for demo purposes
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
                .getAttribute("value");
    }

    private void captureScreenshot(String attachmentName) {
        Allure.addAttachment(attachmentName,
                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    private void captureScreenshot(String attachmentName, boolean condition) {
        if (!condition) {
            Allure.addAttachment(attachmentName,
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }

    /*private void retryValidation(Runnable validationAction, int retries, int sleepInterval) {
        for (int i = 0; i < retries; i++) {
            try {
                validationAction.run();
                System.out.println("Validation passed on attempt " + (i + 1));
                return;
            } catch (Exception e) {
                System.out.println("Retry " + (i + 1) + ": Validation failed, retrying...");
                sleep(sleepInterval);
            }
        }
        throw new RuntimeException("Validation FAILED after " + retries + " attempts.");
    }

    private void waitForElement(String xpath, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    } */

    /* private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
} */