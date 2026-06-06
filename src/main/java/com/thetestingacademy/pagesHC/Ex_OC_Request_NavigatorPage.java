package com.thetestingacademy.pagesHC;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class Ex_OC_Request_NavigatorPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public Ex_OC_Request_NavigatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.js = (JavascriptExecutor) driver;
    }

    // =========================================================
    // SINGLE BUSINESS FLOW METHOD (RECOMMENDED)
    // =========================================================
    public void exOCRequest() {

        Allure.step("Home → Open Latest EX OC Request Number", () -> {

            // -------------------------------
            // STEP 1: Navigate to Home
            // -------------------------------
            String homeUrl = ConfigReader.getData("home.url");

            driver.navigate().to(homeUrl);

            wait.until(ExpectedConditions.urlContains("/home"));

            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);

            Assert.assertTrue(currentUrl.contains("/home"),
                    "❌ Home URL mismatch: " + currentUrl);

            SceenshotUtil.takeScreenshot(driver, "Home Page Loaded");
            // -------------------------------
            // STEP 2: Open Latest EX OC Request
            // -------------------------------

            String targetRequest = TestData.exOCRequestNumber;

            if (targetRequest == null || targetRequest.isEmpty()) {
                throw new RuntimeException("❌ EX OC Request number missing in TestData");
            }

        // 🔥 IMPORTANT FIX → Remove '#'
            targetRequest = targetRequest.replace("#", "").trim();

            System.out.println("Processed Target Request: " + targetRequest);

        // 🔥 Direct dynamic XPath (NO LOOP NEEDED)
            By requestLink = By.xpath(
                    "//a[contains(@class,'LinkedItem') and contains(text(),'" + targetRequest + "')]"
            );

            try {
                WebElement request = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(requestLink)
                );

                js.executeScript("arguments[0].scrollIntoView({block:'center'});", request);

                wait.until(ExpectedConditions.elementToBeClickable(request));

                request.click();

                System.out.println("✅ Clicked EX OC Request: " + targetRequest);
                SceenshotUtil.takeScreenshot(driver, "Clicked EX OC Request");

                // =========================================================
                // 🔥 POST CLICK STABILIZATION (TASK GRID LOAD FIX)
                // =========================================================

                // wait for DOM ready state
                wait.until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete")
                );

                // wait for either Claim button OR task grid to appear
                By claimButton = By.xpath("//span[normalize-space()='Claim']");
                By taskTable = By.xpath("//table | //div[contains(@class,'Table')]");

                wait.until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(claimButton),
                        ExpectedConditions.presenceOfElementLocated(taskTable)
                ));

                // optional: wait for loading spinner to disappear (if present)
                By loadingSpinner = By.xpath(
                        "//*[contains(@class,'loading') or contains(@class,'spinner')]"
                );

                try {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
                } catch (Exception ignored) {
                    // spinner may not exist in some renders
                }

                System.out.println("✅ Task grid fully loaded after request open");

            } catch (Exception e) {
                SceenshotUtil.takeScreenshot(driver, "Request Not Found");
                throw new RuntimeException(
                        "❌ EX OC Request not found on Home Page: " + targetRequest
                );
            }
        });
    }
}

