package com.thetestingacademy.pagesHC.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ConfirmWorkCompletionPage extends CommonUIActions {

    private final DataModel testData;

    public ConfirmWorkCompletionPage(WebDriver driver, DataModel testData) {
        super(driver);
        this.testData = testData;
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================
    private By checkbox = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]//input[@type='checkbox']/following-sibling::label"
    );
    private By taskLink = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]//td//a[normalize-space()='Confirm Work Completion']"
    );
    private By claimVisible = By.xpath("//button[.//span[text()='Claim']]");
    private By tableVisible = By.xpath("//table//tbody//tr");
    private By task = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]");

   // private By claimButton = By.xpath("//button[.//span[text()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    private By requestCloseStatusDropdown = By.xpath(
            "//div[@role='combobox'][.//span[contains(text(),'---Request Close Status---')]]");
    private By calendarBtn = By.cssSelector("button.DatePickerWidget---calendar_btn");
    private By calHdr = By.xpath(("//h2 | //div[contains(@class,'Header')]"));
    private By calendarRoot = By.xpath("//*[contains(@class,'DatePicker') or contains(@class,'Calendar')]");
    private By inputBox = By.xpath("//input[contains(@data-testid,'DatePickerWidget')]");
    private String requestClosedate;
    private By submitDisabledBtn = By.xpath("//button[normalize-space()='Submit' and @disabled]");
    private By submitButton = By.xpath("//button[.//span[text()='Submit']]");

    // =========================================================
    // Edge scenario locators
    // =========================================================

    private By commentBox = By.xpath("//textarea");
    private By saveArrow = By.xpath(
            "//button[not(@disabled)]//*[name()='svg' and @data-owl-icon-name='chevron-right']/ancestor::button[1]"
    );
    private By commentSection = By.xpath(
            "//*[normalize-space()='Comments']/ancestor::div[1]");
    private By processingIndicator = By.xpath(
            "//*[contains(@class,'loading') or contains(@class,'spinner') or @aria-busy='true']"
    );
    private By saveClose = By.xpath("//span[normalize-space()='Save & Close']");
    private By taskGrid = By.xpath("//table//tbody//tr");
    private By commentsLink = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Comments']");
    private By commentSpanLocator = By.xpath("//span[contains(@class,'ColorText') and normalize-space()!='']");
    private By cancelBtn = By.xpath("//button[.//span[normalize-space()='Cancel']]");
    private By yesButton = By.xpath("//button[.//span[normalize-space()='Yes']]");
    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {

        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterConfirmWorkCompletionTask() {

        Allure.step("TASK  - Confirm Work Completion: Full navigation flow", () -> {

            // STEP 0: WAIT FOR GRID
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(claimVisible),
                    ExpectedConditions.visibilityOfElementLocated(tableVisible)
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

                    List<WebElement> rows = driver.findElements(task);

                    if (!rows.isEmpty() && rows.get(0).isDisplayed()) {

                        System.out.println("✅ Task found");
                        SceenshotUtil.takeScreenshot(driver, "Task Found");
                        break;
                    }

                } catch (Exception ignored) {
                }

                attempts++;

                System.out.println("🔄 Refresh attempt: " + attempts);
                SceenshotUtil.takeScreenshot(driver, "Task Refresh Attempt " + attempts);

                driver.navigate().refresh();
            }

            if (attempts == maxAttempts) {
                throw new RuntimeException("❌ Task not found after retries");
            }

            // STEP 2: SELECT CHECKBOX (COMMON UI ACTION USED)
            click(checkbox);

            System.out.println("✅ Checkbox selected");
            SceenshotUtil.takeScreenshot(driver, "Checkbox Selected");

            // STEP 3: OPEN TASK (COMMON UI ACTION USED)
            click(taskLink);

            System.out.println("✅ Task opened");
            SceenshotUtil.takeScreenshot(driver, "Task Opened - Confirm Work Completion");
        });
    }
    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleConfirmWorkCompletionFields() {

        //Allure.step("TASK - Confirm Work Completion: Handle all fields", () -> {

        Allure.step("Select from Request Close Status dropdown", () -> {

            String requestCloseStatus = testData.getRequestClose();

            // OPEN DROPDOWN (COMMON UI ACTION)
            click(requestCloseStatusDropdown);

            System.out.println("Request Close Status dropdown opened");
            Allure.step("Request Close Status dropdown opened");

            SceenshotUtil.takeScreenshot(driver,
                    "Request Close Status Dropdown Opened");

            // EXISTING LOGIC KEPT AS-IS
            selectFromDynamicList(requestCloseStatus);

            System.out.println("Request Close Status selected: " + requestCloseStatus);
            Allure.step("Request Close Status selected: " + requestCloseStatus);

            SceenshotUtil.takeScreenshot(driver,
                    "Request Close Status Completed option Selected");
        });

        Allure.step("Handle Date of Completion (select current date only)", () -> {

            WebElement calendar = wait.until(
                    ExpectedConditions.elementToBeClickable(calendarBtn)
            );
            calendar.click();

            // DEBUG
            WebElement header = driver.findElement(calHdr);
            System.out.println("Calendar Header: " + header.getText());

            //  Using fixed business timezone
            ZoneId zone = ZoneId.of("America/New_York"); // MUST match Appian server config
            LocalDate today = LocalDate.now(zone);

            String dayOfWeek = today.getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            int dayOfMonth = today.getDayOfMonth();
            //String day = String.valueOf(dayOfMonth);

            String month = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            int year = today.getYear();

            // Handle suffix
            String suffix;

            if (dayOfMonth >= 11 && dayOfMonth <= 13) {
                suffix = "th";
            } else {
                switch (dayOfMonth % 10) {
                    case 1:
                        suffix = "st";
                        break;
                    case 2:
                        suffix = "nd";
                        break;
                    case 3:
                        suffix = "rd";
                        break;
                    default:
                        suffix = "th";
                }
            }

            // Exact Appian aria-label
            String ariaLabel = String.format(
                    "Select %s, %s %d%s %d",
                    dayOfWeek,
                    month,
                    dayOfMonth,
                    suffix,
                    year
            );

            System.out.println("Looking for date: " + ariaLabel);

            // Wait for calendar render
            wait.until(ExpectedConditions.visibilityOfElementLocated(calendarRoot));

            // Exact date selection
            By todayBtn = By.xpath(
                    "//button[@aria-label=\"" + ariaLabel + "\"]"
            );

            WebElement selectedDate = wait.until(
                    ExpectedConditions.elementToBeClickable(todayBtn)
            );

            System.out.println("Matched aria-label: "
                    + selectedDate.getAttribute("aria-label"));

            selectedDate.click();

            // Wait until input gets populated
            wait.until(driver -> {
                String val = driver.findElement(inputBox).getAttribute("value");
                return val != null && val.matches("\\d{2}/\\d{2}/\\d{4}");
            });

            String uiValue = driver.findElement(inputBox).getAttribute("value");

            System.out.println("UI Selected Date Value: " + uiValue);

            // Expected format
            String expected = String.format(
                    "%02d/%02d/%d",
                    today.getMonthValue(),
                    dayOfMonth,
                    year
            );

            requestClosedate = String.format(
                    "%d %s %d",
                    dayOfMonth,
                    month,
                    year
            );

            // Validation
            if (!uiValue.equals(expected)) {
                throw new AssertionError(
                        "Date mismatch! Expected: "
                                + expected
                                + " but found: "
                                + uiValue
                );
            }

            // Detect Appian validation state
            boolean submitStillEnabled = driver.findElements(
                    By.xpath("//button[normalize-space()='Submit' and @disabled]")
            ).isEmpty();

            if (!submitStillEnabled) {
                throw new AssertionError(
                        "Submit got disabled after date selection (Appian validation triggered)"
                );
            }

            System.out.println("Selected today's date: " + requestClosedate);
            Allure.step("Selected today's date: " + requestClosedate);

            SceenshotUtil.takeScreenshot(driver, "Date of Completion Selected");
        });
        Allure.step("Click Submit button to complete task", () -> {

            // CLICK (COMMON UI ACTION REPLACES WAIT + JS + CLICK FLOW)
            click(submitButton);

            System.out.println("✅ Submit button clicked");

            // WAIT FOR POST-SUBMIT STATE CHANGE
            // Option A: URL change
            boolean urlChanged = wait.until(driver ->
                    !driver.getCurrentUrl().contains("start-process")
            );

            if (!urlChanged) {
                throw new AssertionError("Submit click did not navigate away - likely validation failure");
            }

            // confirm no validation banner appears
            boolean validationErrorPresent = !driver.findElements(
                    By.xpath("//*[contains(text(),'cannot') or contains(text(),'invalid') or contains(text(),'required')]")
            ).isEmpty();

            if (validationErrorPresent) {
                throw new AssertionError("Validation error detected after submit");
            }

            SceenshotUtil.takeScreenshot(driver, "Task Submitted");
        });
    }

    public void handleConfirmWorkCompletionEdgeFlow() {

        Allure.step("TASK EDGE FLOW - Confirm Work Completion Regression Scenarios", () -> {

            // =====================================================
            // 1. REQUEST STATUS → WITHDRAWN (keyboard select)
            // =====================================================
            Allure.step("Select from Request Close Status dropdown", () -> {

                String requestCloseStatus = testData.getRequestClose();

                // OPEN DROPDOWN (COMMON UI ACTION)
                click(requestCloseStatusDropdown);

                System.out.println("Request Close Status dropdown opened");
                Allure.step("Request Close Status dropdown opened");

                SceenshotUtil.takeScreenshot(driver,
                        "Request Close Status Dropdown Opened");

                // EXISTING LOGIC KEPT AS-IS
                selectFromDynamicList(requestCloseStatus);

                System.out.println("Request Close Status selected: " + requestCloseStatus);
                Allure.step("Request Close Status selected: " + requestCloseStatus);

                SceenshotUtil.takeScreenshot(driver,
                        "Request Close Status Withdrawn option Selected");
            });
            // =====================================================
            // 2. DATE OF WITHDRAWAL
            // =====================================================
            Allure.step("Handle Date of Withdrawl (select current date only)", () -> {

                WebElement calendar = wait.until(
                        ExpectedConditions.elementToBeClickable(calendarBtn)
                );
                calendar.click();

                // DEBUG
                WebElement header = driver.findElement(calHdr);
                System.out.println("Calendar Header: " + header.getText());

                //  Using fixed business timezone
                ZoneId zone = ZoneId.of("America/New_York"); // MUST match Appian server config
                LocalDate today = LocalDate.now(zone);

                String dayOfWeek = today.getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                int dayOfMonth = today.getDayOfMonth();
                //String day = String.valueOf(dayOfMonth);

                String month = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                int year = today.getYear();

                // Handle suffix
                String suffix;

                if (dayOfMonth >= 11 && dayOfMonth <= 13) {
                    suffix = "th";
                } else {
                    switch (dayOfMonth % 10) {
                        case 1:
                            suffix = "st";
                            break;
                        case 2:
                            suffix = "nd";
                            break;
                        case 3:
                            suffix = "rd";
                            break;
                        default:
                            suffix = "th";
                    }
                }

                // Exact Appian aria-label
                String ariaLabel = String.format(
                        "Select %s, %s %d%s %d",
                        dayOfWeek,
                        month,
                        dayOfMonth,
                        suffix,
                        year
                );

                System.out.println("Looking for date: " + ariaLabel);

                // Wait for calendar render
                wait.until(ExpectedConditions.visibilityOfElementLocated(calendarRoot));

                // Exact date selection
                By todayBtn = By.xpath(
                        "//button[@aria-label=\"" + ariaLabel + "\"]"
                );

                WebElement selectedDate = wait.until(
                        ExpectedConditions.elementToBeClickable(todayBtn)
                );

                System.out.println("Matched aria-label: "
                        + selectedDate.getAttribute("aria-label"));

                selectedDate.click();

                // Wait until input gets populated
                wait.until(driver -> {
                    String val = driver.findElement(inputBox).getAttribute("value");
                    return val != null && val.matches("\\d{2}/\\d{2}/\\d{4}");
                });

                String uiValue = driver.findElement(inputBox).getAttribute("value");

                System.out.println("UI Selected Date Value: " + uiValue);

                // Expected format
                String expected = String.format(
                        "%02d/%02d/%d",
                        today.getMonthValue(),
                        dayOfMonth,
                        year
                );

                requestClosedate = String.format(
                        "%d %s %d",
                        dayOfMonth,
                        month,
                        year
                );

                // Validation
                if (!uiValue.equals(expected)) {
                    throw new AssertionError(
                            "Date mismatch! Expected: "
                                    + expected
                                    + " but found: "
                                    + uiValue
                    );
                }

                // Detect Appian validation state
                boolean submitStillEnabled = driver.findElements(
                        By.xpath("//button[normalize-space()='Submit' and @disabled]")
                ).isEmpty();

                if (!submitStillEnabled) {
                    throw new AssertionError(
                            "Submit got disabled after date selection (Appian validation triggered)"
                    );
                }

                System.out.println("Selected today's date: " + requestClosedate);
                Allure.step("Selected today's date: " + requestClosedate);

                SceenshotUtil.takeScreenshot(driver, "Date of Withdrawl Selected");
            });

            // =====================================================
            // 3. COMMENTS
            // =====================================================
            Allure.step("Enter comment and submit via right arrow", () -> {

                String expectedComment = ConfigReader.getData("existing.comments");

                // TYPE (COMMON UI ACTION)
                type(commentBox, expectedComment);

                SceenshotUtil.takeScreenshot(driver, "Comments Entered");

                // wait briefly for Appian UI state update (keeping as-is)
                Thread.sleep(2000);

                // CLICK ARROW BUTTON (COMMON UI ACTION)
                click(saveArrow);

                // wait until comment appears in DOM (UNCHANGED LOGIC)
                wait.until(driver ->
                        driver.getPageSource().contains(expectedComment)
                );

                System.out.println("✅ Comment submitted");
                Allure.step("Comment submitted");

                SceenshotUtil.takeScreenshot(driver, "Comment Saved");
            });

            Allure.step("Verify comment is displayed in UI after submission", () -> {

                String expectedComment = ConfigReader.getData("existing.comments").trim();

                // Step 1: locate Comments section label first
                WebElement section = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(commentSection)
                );

                // Step 2: find comment inside that section only
                By actualCommentLocator = By.xpath(
                        ".//*[contains(normalize-space(),'" + expectedComment + "')]"
                );

                WebElement actualComment = section.findElement(actualCommentLocator);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        actualComment
                );

                String actualText = actualComment.getText().trim();

                System.out.println("Expected Comment: " + expectedComment);
                System.out.println("Actual Comment: " + actualText);

                if (!actualText.contains(expectedComment)) {
                    throw new RuntimeException(
                            "❌ Comment mismatch!\nExpected: " + expectedComment +
                                    "\nActual: " + actualText
                    );
                }

                System.out.println("✅ Comment verified successfully inside Comments section");

                SceenshotUtil.takeScreenshot(driver, "Comment Verified in UI");
            });
            // =====================================================
            // 4. SAVE & CLOSE
            // =====================================================
            Allure.step("Click Save and Close", () -> {

                // CLICK (COMMON UI ACTION)
                click(saveClose);

                System.out.println("✅ Save and close button clicked");

                // =====================================================
                // POST CLICK (UNCHANGED LOGIC)
                // =====================================================

                // 1. Wait for Save & Close action to complete
                wait.until(driver -> {
                    boolean stillProcessing = driver.findElements(processingIndicator).size() > 0;
                    return !stillProcessing;
                });

                // 2. Ensure page is no longer in editable/process state
                wait.until(driver -> {
                    String url = driver.getCurrentUrl();
                    return !url.contains("start-process") || !driver.findElements(saveClose).isEmpty();
                });

                // 3. Ensure Comments tab is clickable again
                wait.until(ExpectedConditions.elementToBeClickable(commentsLink));

                SceenshotUtil.takeScreenshot(driver, "Task Saved & Closed");
            });

            // =====================================================
            // 5. NAVIGATE BACK TO GRID (implicit)
            // =====================================================
            Allure.step("Verify return to task grid", () -> {

                // WAIT FOR GRID (COMMON WAIT STYLE KEPT SAME BEHAVIOR)
                waitForVisible(taskGrid);

                // Ensure grid is actually rendered (not just DOM placeholder)
                wait.until(driver ->
                        driver.findElements(taskGrid).size() > 0
                );

                System.out.println("✅ Returned to task grid successfully");

                SceenshotUtil.takeScreenshot(driver, "Back to Task Grid");
            });

            // =====================================================
            // 6. COMMENTS LINK VALIDATION
            // =====================================================
            Allure.step("Open Comments tab and validate comment from UI span", () -> {

                String expectedComment = ConfigReader.getData("existing.comments").trim();

                int maxAttempts = 3;
                boolean commentVerified = false;
                Exception lastException = null;

                for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                    try {

                        System.out.println("🔄 Comments validation attempt: " + attempt);

                        if (attempt > 1) {

                            driver.navigate().refresh();

                            wait.until(webDriver ->
                                    ((JavascriptExecutor) webDriver)
                                            .executeScript("return document.readyState")
                                            .equals("complete")
                            );

                            System.out.println("✅ Page refreshed");
                        }

                        // STEP 1: Click Comments Tab
                        WebElement commentsTab = wait.until(
                                ExpectedConditions.elementToBeClickable(commentsLink)
                        );

                        ((JavascriptExecutor) driver)
                                .executeScript("arguments[0].scrollIntoView({block:'center'});", commentsTab);

                        try {
                            commentsTab.click();
                        } catch (Exception e) {
                            System.out.println("⚠️ JS click fallback used");
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", commentsTab);
                        }

                        System.out.println("✅ Comments tab clicked");

                        // STEP 2: WAIT FOR COMMENT SPANS
                        List<WebElement> spans = new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(driver1 -> {

                                    List<WebElement> s = driver1.findElements(commentSpanLocator);

                                    return (s.size() >= 1) ? s : null;
                                });

                        System.out.println("✅ Comment spans loaded: " + spans.size());

                        // STEP 3: SEARCH COMMENT IN SPANS
                        boolean found = false;
                        String actual = "";

                        for (WebElement span : spans) {

                            String text = span.getText().trim();

                            System.out.println("➡️ Span Text: " + text);

                            if (text.contains(expectedComment)) {

                                actual = text;
                                found = true;
                                break;
                            }
                        }

                        if (!found) {

                            throw new RuntimeException(
                                    "❌ Comment not found in UI spans.\nExpected: " + expectedComment
                            );
                        }

                        System.out.println("✅ Comment matched successfully");
                        System.out.println("Expected: " + expectedComment);
                        System.out.println("Actual: " + actual);

                        SceenshotUtil.takeScreenshot(driver, "Comments Verified");

                        commentVerified = true;
                        break;

                    } catch (Exception e) {

                        lastException = e;

                        System.out.println("⚠️ Attempt failed: " + attempt);
                        System.out.println("Reason: " + e.getMessage());

                        if (attempt == maxAttempts) {

                            throw new RuntimeException(
                                    "❌ Comment verification failed after retries",
                                    e
                            );
                        }
                    }
                }

                if (!commentVerified && lastException != null) {
                    throw new RuntimeException("❌ Final failure", lastException);
                }
                System.out.println("✅ Comment successfully verified from UI span");

                SceenshotUtil.takeScreenshot(driver, "Comments Final Verified");

                //System.out.println("✅ Comment successfully verified for user: " + username);
            });
            // =====================================================
            // 7. REOPEN TASK + CANCEL
            // =====================================================
            Allure.step("Navigate to Tasks tab and open Confirm Work Completion task", () -> {

                String taskName = "Confirm Work Completion";

                By tasksTab = By.xpath("//a[normalize-space()='Tasks' or contains(.,'Tasks')]");
                By taskLink = By.xpath("//a[normalize-space()='" + taskName + "']");

                int maxAttempts = 3;
                boolean taskOpened = false;
                Exception lastException = null;

                for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                    try {

                        System.out.println("🔄 Task navigation attempt: " + attempt);

                        // =========================
                        // REFRESH ONLY AFTER FAILURE
                        // =========================
                        if (attempt > 1) {

                            driver.navigate().refresh();

                            wait.until(webDriver ->
                                    ((JavascriptExecutor) webDriver)
                                            .executeScript("return document.readyState")
                                            .equals("complete")
                            );

                            System.out.println("✅ Page refreshed");
                        }

                        // =========================
                        // STEP 1: CLICK TASKS TAB (COMMON UI ACTION)
                        // =========================
                        click(tasksTab);

                        System.out.println("✅ Tasks tab clicked");

                        // =========================
                        // STEP 2: WAIT FOR TASK LIST TO LOAD
                        // =========================
                        new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(d ->
                                        d.findElements(taskLink).size() > 0
                                );

                        System.out.println("✅ Task list loaded");

                        // =========================
                        // STEP 3: CLICK TASK (COMMON UI ACTION)
                        // =========================
                        click(taskLink);

                        System.out.println("✅ Task opened successfully: " + taskName);

                        taskOpened = true;
                        break;

                    } catch (Exception e) {

                        lastException = e;

                        System.out.println("⚠️ Attempt failed: " + attempt);
                        System.out.println("Reason: " + e.getMessage());

                        if (attempt == maxAttempts) {
                            throw new RuntimeException(
                                    "❌ Failed to navigate to task after " + maxAttempts + " attempts",
                                    e
                            );
                        }
                    }
                }

                if (!taskOpened && lastException != null) {
                    throw new RuntimeException("❌ Task navigation failed", lastException);
                }

                System.out.println("✅ Task opened");
                SceenshotUtil.takeScreenshot(driver, "Task Opened - Confirm Work Completion");

                // STEP 4: CANCEL TASK (COMMON UI ACTION)
                click(cancelBtn);

                System.out.println("✅ Task reopened and cancelled");

                SceenshotUtil.takeScreenshot(driver, "Task Cancelled");
            });
        });


        // =====================================================
        // 7. Click Yes
        // =====================================================
        Allure.step("Click Cancel and handle confirmation popup (Yes)", () -> {

            // =========================
            // STEP 1: CLICK CANCEL BUTTON
            // =========================
            WebElement cancelElement = wait.until(
                    ExpectedConditions.elementToBeClickable(cancelBtn)
            );

            scrollToElement(cancelBtn);

            try {
                click(cancelElement);
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed → JS click used for Cancel");
                jsClick(cancelElement);
            }

            System.out.println("✅ Cancel button clicked");

            // =========================
            // STEP 2: WAIT FOR MODAL POPUP
            // =========================
            By cancelModal =
                    By.xpath("//div[@role='dialog' or contains(@class,'Modal') or contains(@class,'Dialog')]");

            wait.until(ExpectedConditions.visibilityOfElementLocated(cancelModal));

            wait.until(driver ->
                    driver.findElements(cancelModal)
                            .stream()
                            .anyMatch(WebElement::isDisplayed)
            );

            System.out.println("✅ Cancel confirmation modal appeared");

            // =========================
            // STEP 3: CLICK YES BUTTON
            // =========================

            WebElement yesBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(yesButton)
            );

            scrollToElement(yesButton);

            try {
                click(yesBtn);
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed → JS click used for Yes");
                jsClick(yesBtn);
            }

            System.out.println("✅ Yes clicked on confirmation popup");

            // =========================
            // STEP 4: VERIFY MODAL CLOSED
            // =========================
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cancelModal));

            System.out.println("✅ Cancel confirmation completed (modal closed)");

            SceenshotUtil.takeScreenshot(driver, "Cancel_Confirmation_Yes_Clicked");
        });
    }



    /*@Step("Validate Confirm Work Completion page loaded")
    public boolean isPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).isDisplayed();
    }
    */
}