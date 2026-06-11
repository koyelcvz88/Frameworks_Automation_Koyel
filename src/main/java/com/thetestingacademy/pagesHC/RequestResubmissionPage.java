package com.thetestingacademy.pagesHC;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

public class RequestResubmissionPage extends CommonUIActions {

    public RequestResubmissionPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
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

            waitForVisible(tableVisibleR);

            System.out.println("✅ Task grid loaded");
            SceenshotUtil.takeScreenshot(driver, "Task Grid Loaded");

            int attempts = 0;
            int maxAttempts = 3;

            while (attempts < maxAttempts) {

                try {

                    waitForPageLoad();
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

            // COMMON UI ACTIONS
            click(checkboxR);

            System.out.println("✅ Checkbox selected");
            SceenshotUtil.takeScreenshot(driver, "Checkbox Selected");

            click(taskLinkR);

            System.out.println("✅ Task opened");
            SceenshotUtil.takeScreenshot(driver, "Task Opened - Request Resubmission");
        });
    }

    // =========================================================
    // Task UI - Submit Field
    // =========================================================
    public void handleRequestResubmissionFields() {
        Allure.step("Click Resubmit button to complete task", () -> {

            click(reSubmitButton);

            System.out.println("✅ ReSubmit button clicked");

            SceenshotUtil.takeScreenshot(driver, "Task ReSubmitted");
        });

        // -------------------------------
        // STEP : Click Back to Home + Validate Home Page
        // -------------------------------
        Allure.step("Click Back to Home and validate Home page", () -> {

            WebElement backBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(backToHome)
            );

            scrollToElement(backToHome);

            try {
                click(backBtn);
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed. Using JS click.");
                jsClick(backBtn);
            }

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
