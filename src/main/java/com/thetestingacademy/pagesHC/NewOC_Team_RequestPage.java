package com.thetestingacademy.pagesHC;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class NewOC_Team_RequestPage {

   WebDriver driver;
   WebDriverWait wait;
    JavascriptExecutor js;

   public NewOC_Team_RequestPage(WebDriver driver) {

       this.driver = driver;

       this.wait = new WebDriverWait(driver,
               Duration.ofSeconds(30));
       this.js = (JavascriptExecutor) driver;
   }

   // =========================
   // LOCATORS
   // =========================

   // HOME PAGE VALIDATION
  /* private final By homePageContainer = By.xpath(
           "//div[contains(@class,'HeaderLayout---header')]");

    // SEARCH BOX
    private By SearchBox = By.xpath(
            "//input[@placeholder='Search Legal Matter requests']");
    private By searchBtn = By.xpath("//button[.//span[text()='Search']]"); */
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

       /*Allure.step("Search Generated Request", () -> {

            // remove #
            String cleanRequestNumber = requestNumber.replace("#", "").trim();

            WebElement search = wait.until(
                    ExpectedConditions.elementToBeClickable(SearchBox));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    search);

            search.click();

            search.clear();

            search.sendKeys(cleanRequestNumber);

            System.out.println(
                    "Searching Request: " + cleanRequestNumber);

            // click search button
            WebElement searchButton = wait.until(
                    ExpectedConditions.elementToBeClickable(searchBtn));
            searchButton.click();
        }); */

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

        // scroll + JS fallback click (important for Appian grids)
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", request);

        wait.until(ExpectedConditions.elementToBeClickable(request));
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
    }
}