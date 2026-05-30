package com.thetestingacademy.pages;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ConfirmWorkCompletionPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ConfirmWorkCompletionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
                    ExpectedConditions.elementToBeClickable(checkbox)
            );

            cb.click();

            System.out.println("✅ Checkbox selected");
            SceenshotUtil.takeScreenshot(driver, "Checkbox Selected");

            // STEP 3: OPEN TASK
            WebElement link = wait.until(
                    ExpectedConditions.elementToBeClickable(taskLink)
            );

            link.click();

            System.out.println("✅ Task opened");
            SceenshotUtil.takeScreenshot(driver, "Task Opened - Confirm Work Completion");
        });
    }

    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleConfirmWorkCompletionFields() {

        //Allure.step("TASK - Confirm Work Completion: Handle all fields", () -> {

            Allure.step("Select from Request Close Status dropdown using keyboard", () -> {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(requestCloseStatusDropdown)
            );

            dropdown.click();
                Thread.sleep(1000);

            System.out.println("✅ Request Close Status dropdown opened");
                Allure.step("Request Close Status dropdown opened");

            SceenshotUtil.takeScreenshot(driver,
                    "Request Close Status Dropdown Opened");

            // small wait for options to render (important for Appian UI)
            Thread.sleep(2000);

            // KEY DOWN + ENTER (select first option = Completed)
            Actions actions = new Actions(driver);

            actions.sendKeys(Keys.ARROW_DOWN)
                    .sendKeys(Keys.ENTER)
                    .perform();

            SceenshotUtil.takeScreenshot(driver, "Completed Selected");
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

            //  wait for calendar to fully render month grid
            /*wait.until(ExpectedConditions.visibilityOfElementLocated(calendarRoot));

            //  click correct visible day
            By todayBtn = By.xpath(
                    "//button[not(@disabled) and not(contains(@class,'disabled')) " +
                            "and normalize-space()='" + day + "']"
            );

            wait.until(ExpectedConditions.elementToBeClickable(todayBtn)).click();

            // stable value wait

            wait.until(driver -> {
                String val = driver.findElement(inputBox).getAttribute("value");
                return val != null && val.matches("\\d{2}/\\d{2}/\\d{4}");
            });

            String uiValue = driver.findElement(inputBox).getAttribute("value");

            System.out.println("UI Selected Date Value: " + uiValue);

            // expected format from SAME timezone
            String expected = String.format("%02d/%02d/%d",
                    today.getMonthValue(),
                    dayOfMonth,
                    year
            );

            requestClosedate = String.format("%d %s %d", dayOfMonth, month, year);

            //   validation
            if (!uiValue.equals(expected)) {
                throw new AssertionError(
                        "Date mismatch! Expected: " + expected + " but found: " + uiValue
                );
            }

            //  detect Appian validation via UI state
            boolean submitStillEnabled = driver.findElements(
                    By.xpath("//button[normalize-space()='Submit' and @disabled]")
            ).isEmpty();

            if (!submitStillEnabled) {
                throw new AssertionError("Submit got disabled after date selection (Appian validation triggered)");
            } */
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

            WebElement sbmtbtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(submitButton)
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
    }

    public void handleConfirmWorkCompletionEdgeFlow() {

    Allure.step("TASK EDGE FLOW - Confirm Work Completion Regression Scenarios", () -> {

        // =====================================================
        // 1. REQUEST STATUS → WITHDRAWN (keyboard select)
        // =====================================================
        Allure.step("Select from Request Close Status dropdown using keyboard", () -> {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(requestCloseStatusDropdown)
            );

            dropdown.click();
            Thread.sleep(1000);

            System.out.println("✅ Request Close Status dropdown opened");
            Allure.step("Request Close Status dropdown opened");

            SceenshotUtil.takeScreenshot(driver,
                    "Request Close Status Dropdown Opened");

            // small wait for options to render (important for Appian UI)
            Thread.sleep(2000);

            Actions actions = new Actions(driver);

            // DOWN + DOWN (simulate navigation)
            actions.sendKeys(Keys.ARROW_DOWN)
                   .sendKeys(Keys.ARROW_DOWN)
                   .sendKeys(Keys.ENTER)
                   .perform();

            SceenshotUtil.takeScreenshot(driver, "Withdrawn Selected");
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

            // wait for calendar to fully render month grid
            /*By calendarRoot = By.xpath("//*[contains(@class,'DatePicker') or contains(@class,'Calendar')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(calendarRoot));

            // click correct visible day (avoid hidden duplicates)
            By todayBtn = By.xpath(
                    "//button[not(@disabled) and not(contains(@class,'disabled')) " +
                            "and normalize-space()='" + day + "']"
            );

            wait.until(ExpectedConditions.elementToBeClickable(todayBtn)).click();

            // stable value wait
            By inputBox = By.xpath("//input[contains(@data-testid,'DatePickerWidget')]");

            wait.until(driver -> {
                String val = driver.findElement(inputBox).getAttribute("value");
                return val != null && val.matches("\\d{2}/\\d{2}/\\d{4}");
            });

            String uiValue = driver.findElement(inputBox).getAttribute("value");

            System.out.println("UI Selected Date Value: " + uiValue);

            // expected format from SAME timezone
            String expected = String.format("%02d/%02d/%d",
                    today.getMonthValue(),
                    dayOfMonth,
                    year
            );

            requestClosedate = String.format("%d %s %d", dayOfMonth, month, year);

            //  strict validation
            if (!uiValue.equals(expected)) {
                throw new AssertionError(
                        "Date mismatch! Expected: " + expected + " but found: " + uiValue
                );
            }

            //  detect Appian validation via UI state (not DOM text)
            boolean submitStillEnabled = driver.findElements(submitDisabledBtn).isEmpty();

            if (!submitStillEnabled) {
                throw new AssertionError("Submit got disabled after date selection (Appian validation triggered)");
            } */
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

            WebElement comments = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentBox)
            );

            comments.click();
            comments.clear();
            comments.sendKeys(expectedComment);

            SceenshotUtil.takeScreenshot(driver, "Comments Entered");

            // wait briefly for Appian UI state update
            Thread.sleep(2000);

            // locate enabled arrow button only
            WebElement arrowBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(saveArrow)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    arrowBtn
            );

            // JS click works better for Appian
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    arrowBtn
            );

            // wait until comment appears in DOM
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

            WebElement saveClsebtn = wait.until(
                    ExpectedConditions.elementToBeClickable(saveClose));

            // keep your existing checks
            wait.until(driver ->
                    saveClsebtn.isDisplayed() && saveClsebtn.isEnabled()
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", saveClsebtn);

            wait.until(ExpectedConditions.elementToBeClickable(saveClsebtn));

            //  NO CHANGE
            saveClsebtn.click();
            System.out.println("✅ Save and close button clicked");

            //  ( POST CLICK)

            // 1. Wait for Save & Close action to complete
            wait.until(driver -> {
                // either page becomes stable OR process UI changes
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

            // Wait for grid to reappear after Save & Close navigation
            wait.until(ExpectedConditions.presenceOfElementLocated(taskGrid));

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
                    // STEP 1: CLICK TASKS TAB
                    // =========================
                    WebElement tasksTabElement = wait.until(
                            ExpectedConditions.elementToBeClickable(tasksTab)
                    );

                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block:'center'});", tasksTabElement);

                    try {
                        tasksTabElement.click();
                    } catch (Exception e) {
                        System.out.println("⚠️ Normal click failed → JS click used");
                        ((JavascriptExecutor) driver)
                                .executeScript("arguments[0].click();", tasksTabElement);
                    }

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
                    // STEP 3: CLICK TASK
                    // =========================
                    WebElement taskElement = wait.until(
                            ExpectedConditions.elementToBeClickable(taskLink)
                    );

                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block:'center'});", taskElement);

                    Thread.sleep(800);

                    try {
                        taskElement.click();
                    } catch (Exception e) {
                        System.out.println("⚠️ JS click fallback used");
                        ((JavascriptExecutor) driver)
                                .executeScript("arguments[0].click();", taskElement);
                    }

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

            // STEP 3: click cancel only after stable open
            WebElement cancel = wait.until(ExpectedConditions.elementToBeClickable(cancelBtn));
            cancel.click();

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

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", cancelElement);

            try {
                cancelElement.click();
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed → JS click used for Cancel");
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", cancelElement);
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

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", yesBtn);

            try {
                yesBtn.click();
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed → JS click used for Yes");
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", yesBtn);
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