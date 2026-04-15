package com.thetestingacademy.tests;

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

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
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
        By ocFirmLocator = By.xpath(
                "//strong[normalize-space()='OC Firm Name']/following::em[1]"
        );

        String actualFirm = driver.findElement(ocFirmLocator).getText().trim();

        // 📸 Screenshot for OC Firm validation
        Allure.addAttachment(
                "OC Firm Validation Screenshot",
                new ByteArrayInputStream(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                )
        );

        Allure.step("OC Firm Actual: " + actualFirm);

        Assert.assertEquals(actualFirm, page.selectedOCFirm, "OC Firm mismatch");

        // STEP 4: ATTORNEY VALIDATION
        // =========================
        By attorneyLocator = By.xpath(
                "//strong[normalize-space()='Contact Attorney']/following::em[1]"
        );

        String actualAttorney = driver.findElement(attorneyLocator).getText().trim();

        // 📸 Screenshot for Attorney validation
        Allure.addAttachment(
                "Attorney Validation Screenshot",
                new ByteArrayInputStream(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                )
        );

        Allure.step("Attorney Actual: " + actualAttorney);

        Assert.assertEquals(actualAttorney, page.selectedAttorney, "Attorney mismatch");

        // STEP 5: Final Assertion / Logging
        System.out.println("✅ TC3 PASSED");
        Allure.step("TC3 PASSED SUCCESSFULLY");
    }
}