package com.thetestingacademy.pagesHC.pages;

import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ExecuteAndUploadELPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public ExecuteAndUploadELPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================
    private By checkboxD = By.xpath(".//input[@type='checkbox']");
    private By taskLinkD = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Execute and Upload Engagement Letter']]//td//a[normalize-space()='Execute and Upload Engagement Letter']"
    );
    private By claimButtonD = By.xpath("//button[.//span[normalize-space()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    private By executeEngagementLettersCheckbox = By.xpath(".//input[@type='checkbox']");
    private By uploadExecutedEngagementLettersButton = By.xpath(
            "(//div[contains(.,'Upload Executed Engagement Letters')]/following::button[.//span[normalize-space()='Upload']])[1]");

    private By uploadW9Button = By.xpath("(//div[contains(.,'Upload W9 Form')]/following::button[.//span[normalize-space()='Upload']])[1]");
    private By submitButtonC = By.xpath("//button[.//span[text()='Submit']]");

    // =========================================================
    // DOCUMENT TAB  locators
    // =========================================================
    private By documentsTabLocator = By.xpath("//div[normalize-space()='Documents']/parent::div");
    private By taskGridC = By.xpath("//table//tbody//tr");
    private By EngagementLetterLink = By.xpath("//a[normalize-space()='Engagement Letter']");
    private By W9FormLink = By.xpath("//a[normalize-space()='W-9 Form']");

    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterExecuteUploadELTask() throws InterruptedException {

        Allure.step("TASK- Execute and Upload Engagement Letter: Full navigation flow", () -> {

            // STEP 1: Refresh Task Grid
            boolean taskOpened = false;

            for (int attempt = 1; attempt <= 10; attempt++) {

                System.out.println("🔄 Searching for Task for NEW OC - Attempt: " + attempt);

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

                            if (rowText.contains("Execute and Upload Engagement Letter")
                                    && rowText.contains("To Do")) {

                                System.out.println("✅ Task 4 row located");

                                // ==============================
                                // SELECT CHECKBOX
                                // ==============================

                                WebElement chckbx = row.findElement(checkboxD);

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", chckbx);

                                System.out.println("✅ Checkbox selected");

                                // WAIT FOR CLAIM BUTTON
                                WebElement claimBtn = wait.until(
                                        ExpectedConditions.elementToBeClickable(claimButtonD));

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", claimBtn);

                                System.out.println("✅ Claim button clicked");

                                // WAIT FOR TASK LINK
                                WebElement task = wait.until(
                                        ExpectedConditions.elementToBeClickable(taskLinkD));

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", task);

                                System.out.println("✅ Task opened");

                                /* =====  VALIDATION ===== */

                                wait.until(ExpectedConditions.or(

                                        // Appian task page URL
                                        ExpectedConditions.urlContains("start-process"),


                                        ExpectedConditions.urlContains("task"),

                                        //  validation -> page fully loaded with Execute Engagement button
                                        ExpectedConditions.visibilityOfElementLocated(executeEngagementLettersCheckbox)

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
                        "❌ Task 4 could not be opened after retries");
            }

            System.out.println("✅ Task opened and validated successfully");

            SceenshotUtil.takeScreenshot(driver, "Task Opened - Execute and Upload Engagement Letter");
        });
    }


    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleExecuteUploadELFields() {

        // STEP : Select Execute Engagement Letters Checkbox
        Allure.step("Selecting 'Execute Engagement letters' checkbox", () -> {

        // ==============================
        // STEP: FIND ROW + SELECT CHECKBOX + EXPAND ROW (APPIAN SAFE)
        // ==============================

            boolean actionDone = false;

            for (int attempt = 1; attempt <= 10; attempt++) {

                System.out.println("🔄 Attempt " + attempt + " - locating engagement row");

                try {

                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class,'FieldLayout')][.//strong[normalize-space()='Execute Engagement letters']]")
                    ));

                    List<WebElement> rows = driver.findElements(By.xpath("//div[contains(@class,'FieldLayout')][.//strong[normalize-space()='Execute Engagement letters']]"));

                    System.out.println("Rows found: " + rows.size());

                    for (int i = 0; i < rows.size(); i++) {

                        try {

                            // IMPORTANT: re-fetch rows every loop to avoid stale elements
                            rows = driver.findElements(By.xpath("//div[contains(@class,'FieldLayout')][.//strong[normalize-space()='Execute Engagement letters']]"));
                            WebElement row = rows.get(i);

                            String rowText = row.getText();
                            System.out.println("ROW => " + rowText);

                            if (rowText.contains("Execute Engagement letters")) {

                                System.out.println("✅ Target row identified");

                                // ==============================
                                // STEP 1: CHECKBOX LOCATE FRESH
                                // ==============================
                                WebElement checkbox = row.findElement(executeEngagementLettersCheckbox);

                                ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].scrollIntoView({block:'center'});",
                                        checkbox
                                );

                                Thread.sleep(800);

                                try {
                                    checkbox.click();
                                } catch (Exception e) {

                                    ((JavascriptExecutor) driver)
                                            .executeScript("arguments[0].click();", checkbox);
                                }

                                System.out.println("✅ Checkbox clicked");
                                String checked = checkbox.getAttribute("aria-checked");

                                System.out.println("Checkbox state = " + checked);

                                // ==============================
                                // STEP 2: RE-LOCATE ROW (IMPORTANT)
                                // ==============================
                                rows = driver.findElements(By.xpath("//div[contains(@class,'FieldLayout')][.//strong[normalize-space()='Execute Engagement letters']]"));
                                row = rows.get(i);

                                ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].scrollIntoView({block:'center'});",
                                        row
                                );

                                Thread.sleep(800);

                                // ==============================
                                // CLICK ROW (EXPAND)
                                // ==============================
                                try {
                                    row.click();
                                } catch (Exception e) {
                                    ((JavascriptExecutor) driver)
                                            .executeScript("arguments[0].click();", row);
                                }

                                System.out.println("✅ Row clicked for expansion");

                                actionDone = true;
                                break;
                            }

                        } catch (StaleElementReferenceException se) {
                            System.out.println("⚠ Stale row detected, retrying...");
                        }
                    }

                    if (actionDone) break;

                    driver.navigate().refresh();
                    Thread.sleep(3000);

                } catch (Exception e) {

                    System.out.println("❌ Attempt failed: " + e.getMessage());

                    driver.navigate().refresh();
                    Thread.sleep(3000);
                }
            }

           // ==============================
           // FINAL VALIDATION
           // ==============================

            if (!actionDone) {
                throw new RuntimeException("❌ Engagement row selection failed after retries");
            }

            System.out.println("✅ Engagement row + checkbox completed successfully");
            System.out.println("✅ Upload section loaded successfully");
            SceenshotUtil.takeScreenshot(driver, "Execute Engagement Letters Checkbox Selected");
        });

        // STEP : Upload Engagement Letter
         Allure.step("Uploading Executed Engagement Letters", () -> {


            Thread.sleep(4000);

            System.out.println("✅ Upload button clicked successfully");


            WebElement uploadFileInput = driver.findElement(
                    By.xpath("(//div[@aria-label='Upload, Drop or paste file here']/following::input)[1]")
            );

            String user_dir_path = System.getProperty("user.dir");
            System.out.println(user_dir_path);

            uploadFileInput.sendKeys(
                    user_dir_path + "/src/main/resources/Legal_Engagement_Letter_Trimont.pdf");

            Thread.sleep(4000);

            System.out.println("✅ Executed Engagement Letter uploaded successfully");
            Allure.step("Executed Engagement Letter uploaded successfully");
            SceenshotUtil.takeScreenshot(driver, "Executed Engagement Letter uploaded successfully");
        });

        // STEP : Upload W9 Form
        Allure.step("Uploading W9 Form", () -> {
            Thread.sleep(4000);

            System.out.println("✅ Upload button clicked successfully");


            WebElement uploadFileInput = driver.findElement(
                    By.xpath("(//div[@aria-label='Upload, Drop or paste file here']/following::input)[1]")
            );

            String user_dir_path = System.getProperty("user.dir");
            System.out.println(user_dir_path);

            uploadFileInput.sendKeys(
                    user_dir_path + "/src/main/resources/Form_W9_Trimont_Sample.pdf");

            Thread.sleep(4000);

            System.out.println("✅ W9 Form uploaded successfully");
            Allure.step("W9 Form uploaded successfully");
            SceenshotUtil.takeScreenshot(driver, "W9 Form uploaded successfully");

        });
        // Click Submit
        Allure.step("Click Submit button to complete task", () -> {

            WebElement sbmtbtnC = wait.until(
                    ExpectedConditions.presenceOfElementLocated(submitButtonC)
            );

            //  ensure it's actually enabled
            wait.until(driver ->
                    sbmtbtnC.isDisplayed() && sbmtbtnC.isEnabled()
            );

            //  scroll into view
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", sbmtbtnC);

            //  wait for stability before click
            wait.until(ExpectedConditions.elementToBeClickable(sbmtbtnC));

            sbmtbtnC.click();

            System.out.println("✅ Submit button clicked");

            // WAIT FOR POST-SUBMIT STATE CHANGE
            // Option A: URL change
            boolean urlChanged = wait.until(driver ->
                    !driver.getCurrentUrl().contains("start-process")
            );

            if (!urlChanged) {
                throw new AssertionError("Submit click did not navigate away - likely validation failure");
            }

            //  confirm no validation banner appears
            boolean validationErrorPresent = !driver.findElements(
                    By.xpath("//*[contains(text(),'cannot') or contains(text(),'invalid') or contains(text(),'required')]")
            ).isEmpty();

            if (validationErrorPresent) {
                throw new AssertionError("Validation error detected after submit");
            }

            SceenshotUtil.takeScreenshot(driver, "Task Submitted");
        });

        // =====================================================
        // 5. NAVIGATE BACK TO GRID (implicit)
        // =====================================================
        Allure.step("Verify return to task grid", () -> {

            // Wait for grid to reappear after Save & Close navigation
            wait.until(ExpectedConditions.presenceOfElementLocated(taskGridC));

            // Ensure grid is actually rendered (not just DOM placeholder)
            wait.until(driver ->
                    driver.findElements(taskGridC).size() > 0
            );

            System.out.println("✅ Returned to task grid successfully");

            SceenshotUtil.takeScreenshot(driver, "Back to Task Grid");
        });

        // =====================================================
        // 6. DOCUMENTS TAB VALIDATION
        // =====================================================
        Allure.step("Open Documents tab and validate uploaded documents", () -> {

            String expectedDocument1 = "Legal_Engagement_Letter_Trimont.pdf";
            String expectedDocument2 = "Form_W9_Trimont_Sample.pdf";

            int maxAttempts = 3;
            boolean documentsVerified = false;
            Exception lastException = null;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                try {

                    System.out.println("🔄 Documents validation attempt: " + attempt);

                    if (attempt > 1) {

                        driver.navigate().refresh();

                        wait.until(webDriver ->
                                ((JavascriptExecutor) webDriver)
                                        .executeScript("return document.readyState")
                                        .equals("complete")
                        );

                        System.out.println("✅ Page refreshed");
                    }

                    // STEP 1: Click Documents Tab
                    WebElement documentsTab = wait.until(
                            ExpectedConditions.elementToBeClickable(documentsTabLocator)
                    );

                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block:'center'});", documentsTab);

                    try {
                        documentsTab.click();
                    } catch (Exception e) {

                        System.out.println("⚠️ JS click fallback used");

                        ((JavascriptExecutor) driver)
                                .executeScript("arguments[0].click();", documentsTab);
                    }

                    System.out.println("✅ Documents tab clicked");

                    Thread.sleep(2000);

                    boolean engagementFound = false;
                    boolean w9Found = false;

                    // =====================================================
                    // STEP 2: OPEN ENGAGEMENT LETTER SECTION + VALIDATE
                    // =====================================================
                    WebElement engagementLink = wait.until(
                            ExpectedConditions.elementToBeClickable(EngagementLetterLink));

                    engagementLink.click();

                    System.out.println("✅ Engagement Letter section opened");

                    WebElement engagementDoc = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'" + expectedDocument1 + "')]")
                            )
                    );

                    if (engagementDoc.isDisplayed()) {
                        engagementFound = true;
                        System.out.println("➡️ Engagement Document Found: " + engagementDoc.getText());
                    }

                    driver.findElement((documentsTabLocator)).click();
                    Thread.sleep(2000);


                    // =====================================================
                    // STEP 3: OPEN W9 FORM SECTION + VALIDATE
                    // =====================================================
                    /*WebElement w9Link = wait.until(
                            ExpectedConditions.elementToBeClickable(W9FormLink));

                    w9Link.click();

                    System.out.println("✅ W9 Form section opened");

                    WebElement w9Doc = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'" + expectedDocument2 + "')]")
                            )
                    );

                    if (w9Doc.isDisplayed()) {
                        w9Found = true;
                        System.out.println("➡️ W9 Document Found: " + w9Doc.getText());
                    } */
                    // =====================================================
// STEP 3: OPEN W9 SECTION + VALIDATE DOCUMENT
// =====================================================

// ===== WAIT FOR W9 LINK PRESENCE =====
                    WebElement w9Link = wait.until(
                            ExpectedConditions.presenceOfElementLocated(W9FormLink)
                    );

// ===== ENSURE VISIBLE =====
                    wait.until(ExpectedConditions.visibilityOf(w9Link));

// ===== SCROLL TO ELEMENT =====
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            w9Link
                    );

                    Thread.sleep(1000);

// ===== CLICK W9 SECTION =====
                    try {

                        w9Link.click();

                    } catch (Exception e) {

                        System.out.println("⚠️ Normal click failed on W9 Form section");

                        ((JavascriptExecutor) driver)
                                .executeScript("arguments[0].click();", w9Link);
                    }

                    System.out.println("✅ W9 Form section opened");

// ===== WAIT FOR SECTION EXPANSION =====
                    Thread.sleep(3000);

// ===== VALIDATE W9 DOCUMENT =====
                    WebElement w9Doc = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'" + expectedDocument2 + "')]")
                            )
                    );

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            w9Doc
                    );

                    if (w9Doc.isDisplayed()) {

                        w9Found = true;

                        System.out.println("➡️ W9 Document Found: " + w9Doc.getText());
                    }

                    // =====================================================
                    // FINAL VALIDATION
                    // =====================================================
                    if (!engagementFound) {
                        throw new RuntimeException(
                                "❌ Engagement Letter document not found: " + expectedDocument1
                        );
                    }

                    if (!w9Found) {
                        throw new RuntimeException(
                                "❌ W9 Form document not found: " + expectedDocument2
                        );
                    }

                    System.out.println("✅ Both uploaded documents verified successfully");

                    SceenshotUtil.takeScreenshot(driver, "Documents Verified for Engagement Letter");

                    documentsVerified = true;
                    break;

                } catch (Exception e) {

                    lastException = e;

                    System.out.println("⚠️ Attempt failed: " + attempt);
                    System.out.println("Reason: " + e.getMessage());

                    if (attempt == maxAttempts) {

                        throw new RuntimeException(
                                "❌ Documents verification failed after retries",
                                e
                        );
                    }
                }
            }

            if (!documentsVerified && lastException != null) {
                throw new RuntimeException("❌ Final failure", lastException);
            }

            System.out.println("✅ Uploaded documents successfully verified");

            SceenshotUtil.takeScreenshot(driver, "Documents Verified for W9 Form");

        });
    }
}
