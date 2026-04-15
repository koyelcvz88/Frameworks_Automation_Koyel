package com.thetestingacademy.tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.pages.LoginPage;
import com.thetestingacademy.pages.LegalRequestInitiationPage;
import com.thetestingacademy.pages.LegalRequestFormPage;
import com.thetestingacademy.pages.LegalRequestExistingOCPage;

import java.io.ByteArrayInputStream;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("E2E - TC1 + TC2 + TC3 Existing OC Flow")

public class LegalRequestExistingOCE2ETest extends BaseTest {

    @Test
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Full E2E Flow - Login → Initiate → Form → Existing OC")

    public void e2e_existing_oc_flow() {

        // =========================
        // STEP 0 - OPEN APPLICATION
        // =========================
        Allure.step("Prerequisite STEP - Launch Application", () -> {
            openApplication("https://trimont-test.appiancloud.com/suite/portal/login.jsp");
        });

        // =========================
        // STEP 1 - LOGIN
        // =========================
        Allure.step("STEP 1 - Login to Application", () -> {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterUsername("AM_CMSTest");
            loginPage.enterPassword("Maantic@2026");
            loginPage.clickSignin();
            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "Login failed in E2E flow"
            );
        });

        // =========================
        // PAGE OBJECT INITIALIZATION
        // =========================
        LegalRequestInitiationPage initiationPage = new LegalRequestInitiationPage(driver);
        // =========================
        By banner = By.xpath("//span[contains(text(),'Hello')]");

        String bannerText = driver.findElement(banner).getText();

        Allure.step("Banner Text: " + bannerText);
        System.out.println("Banner Text: " + bannerText);

// 📸 Screenshot BEFORE validation
        Allure.addAttachment(
                "Login Banner Screenshot",
                new ByteArrayInputStream(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                )
        );

// ✅ ASSERTION
        try {
            Assert.assertEquals(bannerText, "Hello, AM_CMS!");

            Allure.step("Login validation PASSED");

        } catch (AssertionError e) {

            Allure.step("Login validation FAILED");

            // 📸 Screenshot on failure
            Allure.addAttachment(
                    "FAILED Login Validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );

            throw e;
        }
        LegalRequestFormPage formPage = new LegalRequestFormPage(driver);
        LegalRequestExistingOCPage existingOCPage = new LegalRequestExistingOCPage(driver);

        // =========================
        // STEP 2 - TC1 INITIATE REQUEST
        // =========================
        Allure.step("STEP 2 - TC1 Initiate Legal Request", () -> {
            initiationPage.clickInitiateLegalRequest();
            Thread.sleep(2000);
        });

        // =========================
        // STEP 3 - TC2 FORM FILL
        // =========================
        Allure.step("STEP 3 - TC2 Fill Legal Request Form", () -> {
            formPage.fillLegalRequestForm();
            Thread.sleep(2000);
        });

        // =========================
        // STEP 4 - TC3 EXISTING OC FLOW
        // =========================
        Allure.step("STEP 4 - TC3 Existing OC Flow", () -> {
            existingOCPage.fillExistingOCRequest();
            Thread.sleep(2000);
        });

        // =========================
        // FINAL STATUS
        // =========================
        System.out.println("✅ FULL E2E PASSED (TC1 + TC2 + TC3)");
        Allure.step("E2E FLOW COMPLETED SUCCESSFULLY");
    }
}