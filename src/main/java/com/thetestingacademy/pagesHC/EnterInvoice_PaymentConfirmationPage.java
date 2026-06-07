package com.thetestingacademy.pagesHC;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.format.TextStyle;
import java.util.List;


public class EnterInvoice_PaymentConfirmationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public EnterInvoice_PaymentConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================

    private By checkbox = By.xpath(".//input[@type='checkbox']");
    private By task2Link = By.xpath("//a[contains(normalize-space(),"
                    + "'Enter Invoice and Payment Confirmation')]");
    //private By task2 = By.xpath("//table//tbody//tr[.//a[contains(normalize-space(),'Enter Invoice')]]");

    private By claimButton = By.xpath("//button[.//span[normalize-space()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    private By outsideCounselFeesPaidCheckbox = By.xpath(
            "//strong[contains(.,'Are Outside Counsel Fees Paid?')]/following::label[1]");
    private By closeRequest = By.xpath("//button[.//span[text()='Close Request']]");
    private By auditlink = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Audit History']");

    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterInvoicepaymentConfirmationTask() {
        Allure.step("Open Enter Invoice and Payment Confirmation task",()-> {

        boolean taskOpened = false;

        for (int attempt = 1; attempt <= 10; attempt++) {

            System.out.println("🔄 Searching for Task 2 - Attempt: " + attempt);

            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//table//tbody//tr")));

                List<WebElement> rows = driver.findElements(
                        By.xpath("//table//tbody//tr"));

                System.out.println("Rows found: " + rows.size());

                for (int i = 0; i < rows.size(); i++) {

                    try {

                        rows = driver.findElements(By.xpath("//table//tbody//tr"));
                        WebElement row = rows.get(i);

                        String rowText = row.getText();

                        System.out.println("ROW => " + rowText);

                        if (rowText.contains("Enter Invoice and Payment Confirmation")
                                && rowText.contains("To Do")) {

                            System.out.println("✅ Task 2 row located");

                            // ==============================
                            // SELECT CHECKBOX
                            // ==============================

                            WebElement chckbx = row.findElement(checkbox);

                            ((JavascriptExecutor) driver)
                                    .executeScript("arguments[0].click();", chckbx);

                            System.out.println("✅ Checkbox selected");

                            // WAIT FOR CLAIM BUTTON
                            WebElement claimBtn = wait.until(
                                    ExpectedConditions.elementToBeClickable(claimButton));

                            ((JavascriptExecutor) driver)
                                    .executeScript("arguments[0].click();", claimBtn);

                            System.out.println("✅ Claim button clicked");

                            // WAIT FOR TASK LINK
                            WebElement task = wait.until(
                                    ExpectedConditions.elementToBeClickable(task2Link));

                            ((JavascriptExecutor) driver)
                                    .executeScript("arguments[0].click();", task);

                            System.out.println("✅ Task opened");

                            /* =====  VALIDATION ===== */

                            wait.until(ExpectedConditions.or(

                                    // Appian task page URL
                                    ExpectedConditions.urlContains("start-process"),


                                    ExpectedConditions.urlContains("task"),

                                    //  validation -> page fully loaded with Close Request button
                                    ExpectedConditions.visibilityOfElementLocated(closeRequest)

                            ));

                            System.out.println("✅ Task URL verified: "
                                    + driver.getCurrentUrl());

                            taskOpened = true;

                            break;

                        }

                    } catch (StaleElementReferenceException e) {

                        System.out.println("⚠ Row became stale, retrying row...");
                    }
                }

                if (taskOpened) {
                    break;
                }

                driver.navigate().refresh();

                System.out.println("🔄 Page refreshed");

                Thread.sleep(3000);

            } catch (Exception e) {

                System.out.println("❌ Attempt failed: " + e.getMessage());

                driver.navigate().refresh();

                Thread.sleep(3000);
            }
        }

        if (!taskOpened) {
            throw new RuntimeException(
                    "❌ Task 2 could not be opened after retries");
        }
            System.out.println("✅ Task opened");
            SceenshotUtil.takeScreenshot(driver, "Task Opened - Enter invoice and payment confirmation");
    });
    }
    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleEnterInvoiceandPaymentConfirmationFields() {

        //Allure.step("TASK - Confirm Work Completion: Handle all fields", () -> {

        Allure.step("Select 'Are Outside Counsel Fees Paid?' checkbox", () -> {

            WebElement checkbox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(outsideCounselFeesPaidCheckbox)
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);

            Thread.sleep(1000);

            try {

                checkbox.click();

            } catch (Exception e) {

                System.out.println("⚠️ Normal click failed, using JS click");

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", checkbox);
            }

            System.out.println("✅ 'Are Outside Counsel Fees Paid?' checkbox selected");
            Allure.step("'Are Outside Counsel Fees Paid?' checkbox selected");

            SceenshotUtil.takeScreenshot(driver, "Are Outside Counsel Fees Paid Checkbox Selected");
        });

        Allure.step("Click Close request button to complete task", () -> {

            WebElement closeBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(closeRequest)
            );

            // 🔥 FIX 1: ensure it's actually enabled (not just clickable)
            wait.until(driver ->
                    closeBtn.isDisplayed() && closeBtn.isEnabled()
            );

            // 🔥 FIX 2: scroll into view (Appian UI often blocks clicks otherwise)
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", closeBtn);

            // 🔥 FIX 3: wait for stability before click (no Thread.sleep dependency)
            wait.until(ExpectedConditions.elementToBeClickable(closeBtn));

            closeBtn.click();

            System.out.println("✅ Close Request button clicked");

            // 🔥 FIX 4: WAIT FOR POST-CLOSE REQUEST STATE CHANGE
            // Option A: URL change
            boolean urlChanged = wait.until(driver ->
                    !driver.getCurrentUrl().contains("start-process")
            );

            if (!urlChanged) {
                throw new AssertionError("Submit click did not navigate away - likely validation failure");
            }

            // 🔥 FIX 5: confirm no validation banner appears
            boolean validationErrorPresent = !driver.findElements(
                    By.xpath("//*[contains(text(),'cannot') or contains(text(),'invalid') or contains(text(),'required')]")
            ).isEmpty();

            if (validationErrorPresent) {
                throw new AssertionError("Validation error detected after submit");
            }

            SceenshotUtil.takeScreenshot(driver, "Task Submitted");
        });
        Allure.step("Validate Audit History tab", () -> {

            try {

                By auditLogsBy = By.xpath(
                        "//p[contains(.,'Created Task') " +
                                "or contains(.,'Completed Task') " +
                                "or contains(.,'Claimed Task')]"
                );

                // wait till audit tab visible
                WebElement auditTabElement = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(auditlink)
                );

                // scroll to audit tab
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true);", auditTabElement);

                Thread.sleep(1000);

                // js click audit tab
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", auditTabElement);

                SceenshotUtil.takeScreenshot(driver, "Audit_Tab_Clicked");

                System.out.println("✅ Audit History tab clicked");

                // wait for audit logs
                wait.until(ExpectedConditions.visibilityOfElementLocated(auditLogsBy));

                List<WebElement> auditLogs = driver.findElements(auditLogsBy);

                StringBuilder auditText = new StringBuilder();

                for (WebElement log : auditLogs) {

                    String text = log.getText().trim();

                    if (!text.isEmpty()) {
                        auditText.append(text).append("\n");
                    }
                }

                System.out.println("===== AUDIT HISTORY LOGS =====");
                System.out.println(auditText);
                System.out.println("================================");

                Allure.addAttachment("Audit History Logs", auditText.toString());

                SceenshotUtil.takeScreenshot(driver, "Audit_History_Logs");

            } catch (Exception e) {

                SceenshotUtil.takeScreenshot(driver, "Audit_History_Failure");

                throw new RuntimeException(
                        "❌ Failed during Audit History validation : " + e.getMessage()
                );
            }
        });
    }
}