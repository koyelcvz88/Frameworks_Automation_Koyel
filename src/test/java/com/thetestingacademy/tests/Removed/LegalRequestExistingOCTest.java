/*package com.thetestingacademy.tests.Removed;

import com.thetestingacademy.pages.LegalRequestExistingOCPage;
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
@Story("TC3 - Existing OC Flow")

public class LegalRequestExistingOCTest extends BaseTest {

    @Test(priority = 3)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC3 - Verify user can complete Existing OC flow after form submission")
    public void tc3_existing_oc_flow() {

        // STEP 1: Initialize Page Object
        LegalRequestExistingOCPage page = new LegalRequestExistingOCPage(driver);

        // STEP 2: Execute TC3 Flow
        Allure.step("TC3 - Fill and Submit Existing OC Request", () -> {
            page.fillExistingOCRequest();
        });

        // STEP 3: OC FIRM VALIDATION
        // =========================
        /*By ocFirmLocator = By.xpath(
                "//strong[normalize-space()='OC Firm Name']/following::em[1]"
        );

        String actualFirm = driver.findElement(ocFirmLocator).getText().trim();

        Allure.step("Capture OC Firm validation screenshot", () -> {

            Allure.addAttachment(
                    "OC Firm Validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );
        });
        String actualFirm = driver.findElement(By.xpath(
                "//strong[normalize-space()='OC Firm Name']/following::em[1]"
        )).getText().trim();

        Allure.step("Capture OC Firm validation screenshot", () -> {
            Allure.addAttachment(
                    "OC Firm Validation Screenshot",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES))
            );
        });

        Allure.step("OC Firm Actual: " + actualFirm);

        Assert.assertEquals(actualFirm, page.selectedOCFirm, "OC Firm mismatch");

        // STEP 4: ATTORNEY VALIDATION
        // =========================
        /* By attorneyLocator = By.xpath(
                "//strong[normalize-space()='Contact Attorney']/following::em[1]"
        );

        String actualAttorney = driver.findElement(attorneyLocator).getText().trim();

        Allure.step("Capture Attorney validation screenshot", () -> {

            Allure.addAttachment(
                    "Attorney Validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );
        });
        String actualAttorney = driver.findElement(By.xpath(
                "//strong[normalize-space()='Contact Attorney']/following::em[1]"
        )).getText().trim();

        Allure.step("Capture Attorney validation screenshot", () -> {
            Allure.addAttachment(
                    "Attorney Validation Screenshot",  // Name of the screenshot attachment
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES))  // Take screenshot and attach
            );
        });
        Allure.step("Attorney Actual: " + actualAttorney);

        // Assertion to validate if the actual attorney matches the expected one
        Assert.assertEquals(actualAttorney, page.selectedAttorney, "Attorney mismatch");

        // STEP 5: Final Assertion / Logging
        System.out.println("✅ TC3 PASSED");
        Allure.step("TC3 PASSED SUCCESSFULLY");
    }
} */