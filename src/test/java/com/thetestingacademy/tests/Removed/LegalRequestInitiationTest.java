/*package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.pages.LegalRequestInitiationPage;
import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

@Test(groups = {"Smoke"})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC1 - Initiate Legal Request")
public class LegalRequestInitiationTest extends BaseTest {

    @Test(priority = 1)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC1 - Verify user can initiate legal request")
    public void tc1_initiate_legal_request() {

        LegalRequestInitiationPage page =
                new LegalRequestInitiationPage(driver);

        // -------------------------
        // ACTION
        // -------------------------
        Allure.step("Initiate Legal Request", () -> {
            page.clickInitiateLegalRequest();
        });

        // -------------------------
        // VALIDATION
        // -------------------------
        boolean result = Allure.step("Validate Legal Request initiation", () -> {

            // Screenshot after performing validation steps
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            // Attach screenshot to Allure report
            Allure.addAttachment(
                    "Validation Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );

            // Validate the form and banner
            boolean form = page.isFormLoaded();
            boolean banner = page.isBannerDisplayed();

            // Return true only if both validations pass
            return form && banner;
        });

        // -------------------------
        // ASSERTION
        // -------------------------
        Assert.assertTrue(result, "Legal Request initiation failed");

        // Log the success of the test case
        Allure.step("TC1 PASSED");
    }
} */