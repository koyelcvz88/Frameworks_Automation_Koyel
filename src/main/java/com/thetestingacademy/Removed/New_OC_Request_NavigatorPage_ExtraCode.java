/*package com.thetestingacademy.pages;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class New_OC_Request_NavigatorPage2 {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public New_OC_Request_NavigatorPage2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.js = (JavascriptExecutor) driver;
    }

    // =========================
    // LOCATORS
    // =========================
    private By teamTab = By.xpath(
            "//a[contains(normalize-space(.),\"MY TEAM'S LEGAL MATTER REQUESTS\")]");
    // =========================================================
    // SINGLE BUSINESS FLOW METHOD (RECOMMENDED)
    // =========================================================
    public void newOCRequest() {

        Allure.step("Home → Open Latest NEW OC Request Number", () -> {

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
            // STEP 2: Open TEAM REQUESTS TAB
            // -------------------------------
            Allure.step("Open Team Requests Tab", () -> {

                WebElement tab = wait.until(
                        ExpectedConditions.elementToBeClickable(teamTab)
                );

                //tab.click();
                driver.findElement(teamTab).click();

                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'Table') or contains(@class,'Grid')]")
                ));

                System.out.println("Team Requests tab opened");

            });

            // -------------------------------
            // STEP 3: Open Latest NEW OC Request
            // -------------------------------

            String targetRequest = TestData.newOCRequestNumber;

            if (targetRequest == null || targetRequest.isEmpty()) {
                throw new RuntimeException("❌ NEW OC Request number missing in TestData");
            }

        // 🔥 IMPORTANT FIX → Remove '#'
            targetRequest = targetRequest.replace("#", "").trim();

            System.out.println("Processed Target Request: " + targetRequest);

        // 🔥 Direct dynamic XPath (NO LOOP NEEDED)
            /*By requestLink = By.xpath(
                    "//a[contains(@class,'LinkedItem') and contains(text(),'" + targetRequest + "')]"
            );
            By requestLink = By.xpath(
                    "//a[contains(@class,'LinkedItem') " +
                            "and contains(normalize-space(.),'" + targetRequest + " |')]"
            );
            By requestLink = By.xpath(
                    "//a[contains(@class,'LinkedItem') and contains(.,'" + targetRequest + " |')]"
            );
            // wait for presence first (not visibility)
            WebElement request = wait.until(
                    ExpectedConditions.presenceOfElementLocated(requestLink)
            );

            // scroll + JS fallback click (important for Appian grids)
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", request);

            wait.until(ExpectedConditions.elementToBeClickable(request));

            /*try {
                /*WebElement request = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(requestLink)
                );

                js.executeScript("arguments[0].scrollIntoView({block:'center'});", request);

                wait.until(ExpectedConditions.elementToBeClickable(request));

                request.click();

                System.out.println("✅ Clicked NEW OC Request: " + targetRequest);
                SceenshotUtil.takeScreenshot(driver, "Clicked NEW OC Request");

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
                        "❌ NEW OC Request not found on Home Page: " + targetRequest
                );
            }
            try {

                // =========================================================
                // 🔥 RE-LOCATE ELEMENT (avoid stale / timing issues)
                // =========================================================

                js.executeScript("arguments[0].scrollIntoView({block:'center'});", request);

                wait.until(ExpectedConditions.elementToBeClickable(request));

                // =========================================================
                // 🔥 SAFE CLICK (STANDARD + FALLBACK)
                // =========================================================

                try {
                    request.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", request);
                }

                System.out.println("✅ Clicked NEW OC Request: " + targetRequest);
                SceenshotUtil.takeScreenshot(driver, "Clicked NEW OC Request");

                // =========================================================
                // 🔥 POST CLICK STABILIZATION (TASK GRID LOAD FIX)
                // =========================================================

                wait.until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete")
                );

                By claimButton = By.xpath("//span[normalize-space()='Claim']");
                By taskTable = By.xpath("//table | //div[contains(@class,'Table')]");

                wait.until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(claimButton),
                        ExpectedConditions.presenceOfElementLocated(taskTable)
                ));

                By loadingSpinner = By.xpath(
                        "//*[contains(@class,'loading') or contains(@class,'spinner')]"
                );

                try {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
                } catch (Exception ignored) {
                    // spinner may not exist
                }

                System.out.println("✅ Task grid fully loaded after request open");

            } catch (Exception e) {
                SceenshotUtil.takeScreenshot(driver, "Request Not Found");
                throw new RuntimeException(
                        "❌ NEW OC Request not found on Home Page: " + targetRequest
                );
            }
        });
    }
} */

