package com.thetestingacademy.pages;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class RequestResubmissionPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public RequestResubmissionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================
    private By checkboxR = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Request Resubmission']]//input[@type='checkbox']/following-sibling::label"
    );
    private By taskLinkR = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Request Resubmission']]//td//a[normalize-space()='Request Resubmission']"
    );
    private By claimVisibleR = By.xpath("//button[.//span[text()='Claim']]");
    private By tableVisibleR = By.xpath("//table//tbody//tr");
    private By taskR = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Request Resubmission']]");

   // private By claimButton = By.xpath("//button[.//span[text()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    //private By submitDisabledBtnR = By.xpath("//button[normalize-space()='Submit' and @disabled]");
    private By reSubmitButton = By.xpath("//button[.//span[contains(normalize-space(),'Resubmit')]]");
    private By backToHome = By.xpath("//*[self::button or self::a][.//span[normalize-space()='Back to Home']]");
    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterRequestResubmissionTask() {

        Allure.step("TASK - Request Resubmission: Full navigation flow", () -> {

            // STEP 0: WAIT FOR GRID
                    wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(claimVisibleR),
                    ExpectedConditions.visibilityOfElementLocated(tableVisibleR)
            ));

            System.out.println("✅ Task grid loaded");
            SceenshotUtil.takeScreenshot(driver, "Task Grid Loaded");

            // STEP 1: FIND TASK (retry + refresh)

            int attempts = 0;
            int maxAttempts = 3;

            while (attempts < maxAttempts) {

                try {
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(d -> ((JavascriptExecutor) d)
                                    .executeScript("return document.readyState")
                                    .equals("complete"));

                    Thread.sleep(3000);

                    List<WebElement> rows = driver.findElements(taskR);

                    if (!rows.isEmpty() && rows.get(0).isDisplayed()) {

                        System.out.println("✅ Task found");
                        SceenshotUtil.takeScreenshot(driver, "Task Found");
                        break;
                    }

                } catch (Exception ignored) {}

                attempts++;

                System.out.println("🔄 Refresh attempt: " + attempts);
                SceenshotUtil.takeScreenshot(driver, "Task Refresh Attempt " + attempts);

                driver.navigate().refresh();
            }

            if (attempts == maxAttempts) {
                throw new RuntimeException("❌ Task not found after retries");
            }

            // STEP 2: SELECT CHECKBOX
            WebElement cb = wait.until(
                    ExpectedConditions.elementToBeClickable(checkboxR)
            );

            cb.click();

            System.out.println("✅ Checkbox selected");
            SceenshotUtil.takeScreenshot(driver, "Checkbox Selected");

            // STEP 3: OPEN TASK
            WebElement link = wait.until(
                    ExpectedConditions.elementToBeClickable(taskLinkR)
            );

            link.click();

            System.out.println("✅ Task opened");
            SceenshotUtil.takeScreenshot(driver, "Task Opened - Request Resubmission");
        });
    }

    // =========================================================
    // Task UI - Submit Field
    // =========================================================
    public void handleRequestResubmissionFields() {
        Allure.step("Click Submit button to complete task", () -> {

            WebElement sbmtbtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(reSubmitButton)
            );

            //  ensure it's actually enabled
            wait.until(driver ->
                    sbmtbtn.isDisplayed() && sbmtbtn.isEnabled()
            );

            //  scroll into view
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", sbmtbtn);

            //  wait for stability before click
            wait.until(ExpectedConditions.elementToBeClickable(sbmtbtn));

            sbmtbtn.click();

            System.out.println("✅ ReSubmit button clicked");

            /*// WAIT FOR POST-SUBMIT STATE CHANGE
            // Option A: URL change
            boolean urlChanged = wait.until(driver ->
                    !driver.getCurrentUrl().contains("start-process")
            );

            if (!urlChanged) {
                throw new AssertionError("ReSubmit click did not navigate away - likely validation failure");
            }

            //  confirm no validation banner appears
            boolean validationErrorPresent = !driver.findElements(
                    By.xpath("//*[contains(text(),'cannot') or contains(text(),'invalid') or contains(text(),'required')]")
            ).isEmpty();

            if (validationErrorPresent) {
                throw new AssertionError("Validation error detected after resubmit");
            } */

            SceenshotUtil.takeScreenshot(driver, "Task ReSubmitted");
        });

        // -------------------------------
        // STEP : Click Back to Home + Validate Home Page
        // -------------------------------
        Allure.step("Click Back to Home and validate Home page", () -> {

            WebElement backToHomeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(backToHome)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    backToHomeBtn
            );

            try {
                backToHomeBtn.click();
            } catch (Exception e) {

                System.out.println("⚠️ Normal click failed. Using JS click.");

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", backToHomeBtn);
            }

            // wait until redirected to home page
            wait.until(ExpectedConditions.urlContains("/home"));

            String currentUrl = driver.getCurrentUrl();

            System.out.println("Current URL: " + currentUrl);

            Assert.assertTrue(currentUrl.contains("/home"),
                    "❌ Home URL mismatch: " + currentUrl);

            System.out.println("✅ Successfully navigated back to Home page");

            SceenshotUtil.takeScreenshot(driver, "Home Page Loaded");
        });
    }
}