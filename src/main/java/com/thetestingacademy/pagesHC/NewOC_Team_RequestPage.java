package com.thetestingacademy.pagesHC;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class NewOC_Team_RequestPage extends CommonUIActions {

    public NewOC_Team_RequestPage(WebDriver driver) {
        super(driver);
    }

   // =========================
   // LOCATORS
   // =========================

    private By teamTab = By.xpath(
            "//a[contains(normalize-space(.),\"MY TEAM'S LEGAL MATTER REQUESTS\")]");

    // DYNAMIC REQUEST LINK
    private By requestLink(String requestNumber) {

        return By.xpath(
                "//a[contains(normalize-space(),'"
                        + requestNumber + "')]");
    }

    // =========================
    // ACTIONS
    // =========================

    public void openRequestTeamsTab(String requestNumber) {

        Allure.step("Validate Home Page Loaded", () -> {

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
        });
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

        By requestLink = By.xpath(
                "//a[contains(@class,'LinkedItem') and contains(.,'" + targetRequest + " |')]"
        );

        // wait for presence first (not visibility)
        WebElement request = wait.until(
                ExpectedConditions.presenceOfElementLocated(requestLink)
        );

        // =========================================================
        // SCROLL (via CommonUIActions - NO JS)
        // =========================================================
        scrollToElement(request);

        // ensure clickable
        wait.until(ExpectedConditions.elementToBeClickable(request));

        // =========================================================
        // SAFE CLICK WITH FALLBACK
        // =========================================================
        try {
            click(request);
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed → JS click fallback");
            jsClick(request);
        }

        System.out.println("✅ Clicked NEW OC Request: " + targetRequest);
        SceenshotUtil.takeScreenshot(driver, "Clicked NEW OC Request");

        // =========================================================
        // POST CLICK STABILIZATION (TASK GRID LOAD FIX)
        // =========================================================

        // UI render wait (Appian/React stability layer)
        waitForUIRender();

        // GRID VALIDATION
        By claimButton = By.xpath("//span[normalize-space()='Claim']");
        By taskTable = By.xpath("//table | //div[contains(@class,'Table')]");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(claimButton),
                ExpectedConditions.presenceOfElementLocated(taskTable)
        ));

        // SPINNER HANDLING
        By loadingSpinner = By.xpath(
                "//*[contains(@class,'loading') or contains(@class,'spinner')]"
        );

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (Exception ignored) {
            // spinner may not exist
        }

        System.out.println("✅ Task grid fully loaded after request open");
    }
}