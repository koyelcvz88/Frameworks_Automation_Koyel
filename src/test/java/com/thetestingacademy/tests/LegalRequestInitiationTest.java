package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.pages.LegalRequestInitiationPage;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC1 - Initiate Legal Request")
public class LegalRequestInitiationTest extends BaseTest {

    @Test(priority = 1)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC1 - Verify user can initiate legal request")
    public void tc1_initiate_legal_request() {
        LegalRequestInitiationPage initiationPage = new LegalRequestInitiationPage(driver);

        Allure.step("Initiate Legal Request", () -> {
            initiationPage.clickInitiateLegalRequest();
        });

        // Step 2: Locate banner (adjust locator as per your app)
        By banner = By.xpath("//span[contains(text(),'Hello')]");

        String bannerText = driver.findElement(banner).getText();

        // Log banner in Allure
        Allure.step("Banner Text: " + bannerText);
        System.out.println("Banner Text: " + bannerText);

        // 📸 Screenshot BEFORE validation
        Allure.addAttachment(
                "Banner Validation Screenshot",
                new ByteArrayInputStream(
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                )
        );

        // Step 3: Validation
        try {
            Assert.assertEquals(bannerText, "Hello, AM_CMS!");

            System.out.println("Validation PASSED: Banner text is correct.");
            Allure.step("Validation PASSED: Banner text is correct.");

        } catch (AssertionError e) {

            System.out.println("Validation FAILED: Banner text is incorrect.");
            Allure.step("Validation FAILED: Banner text is incorrect.");

            // 📸 Screenshot on failure also (very useful)
            Allure.addAttachment(
                    "FAILED - Banner Validation Screenshot",
                    new ByteArrayInputStream(
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                    )
            );

            throw e;
        }
        // Final log
        System.out.println("✅ TC1 PASSED");
        Allure.step("TC1 PASSED");
    }
}