/*package com.thetestingacademy.tests.Removed;

import com.thetestingacademy.pages.LegalRequestNewOCPage;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.thetestingacademy.base.BaseTest;

import java.io.ByteArrayInputStream;

//@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC4 - New OC Flow")

public class LegalRequestNewOCTest extends BaseTest {

    @Test(priority = 4)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC4 - Verify user can complete New OC flow after form submission")
    public void tc4_new_oc_flow() {

        // STEP 1: Initialize Page Object
        LegalRequestNewOCPage page = new LegalRequestNewOCPage(driver);

        // STEP 2: Execute TC4 Flow
        Allure.step("TC4 - Fill and Submit New OC Request", () -> {
            page.fillNewOCRequest();
        });

        // STEP 3: PHONE VALIDATION
        /* By phoneLocator = By.xpath("//strong[normalize-space()='Phone']/following::input[1]");

        String phoneValue = driver.findElement(phoneLocator)
                .getAttribute("value")
                .replaceAll("[^0-9]", "");

        Allure.step("Phone Value: " + phoneValue);
        Allure.step("Validate phone number format", () -> {

            try {
                Assert.assertTrue(phoneValue.matches("\\d{10}"));

                System.out.println("Phone validated successfully: " + phoneValue);
                Allure.step("Phone validation PASSED");

                // 📸 Screenshot on success (optional)
                Allure.addAttachment(
                        "Phone Validation Screenshot",
                        new ByteArrayInputStream(
                                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                        )
                );

            } catch (AssertionError e) {

                System.out.println("Phone validation FAILED: " + phoneValue);

                Allure.step("Phone validation FAILED");

                // 📸 Screenshot on failure
                Allure.addAttachment(
                        "FAILED - Phone Validation Screenshot",
                        new ByteArrayInputStream(
                                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                        )
                );

                throw e;
            }
        });
        Allure.step("Validate email format", () -> {

            By emailLocator = By.xpath("//strong[normalize-space()='Email']/following::input[1]");
            String emailValue = driver.findElement(emailLocator).getAttribute("value");

            System.out.println("Email found: " + emailValue);

            Assert.assertTrue(
                    emailValue.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"),
                    "Invalid email format: " + emailValue
            );

            Allure.addAttachment(
                    "Email Validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );
        });

        // STEP 5: POST-SUBMIT VALIDATION
        /*Allure.step("Post-submit validation: Verify that request was submitted successfully", () -> {
            // After submit, ensure the page URL is correct or there's some confirmation message
            String confirmationMessage = driver.getPageSource();
            Assert.assertTrue(confirmationMessage.contains("Request submitted successfully"),
                    "Request submission failed");

            // Add Screenshot for validation
            Allure.addAttachment(
                    "Post-submit validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );
        });

        // STEP 6: Final Assertion / Logging
        System.out.println("✅ TC4 PASSED");
        Allure.step("TC4 PASSED SUCCESSFULLY");
    }
}*/