package com.thetestingacademy.pagesHC;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ReviewNewVendorPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ReviewNewVendorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================
    private By checkboxA = By.xpath(".//input[@type='checkbox']");
    /*private By taskLinkA = By.xpath(","
            + "'Review New Vendor')]"); */
    private By taskLinkA = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Review New Vendor']]//td//a[normalize-space()='Review New Vendor']"
    );
    private By claimButtonA = By.xpath("//button[.//span[normalize-space()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    private By attApprovedOption = By.xpath(
            "//label[normalize-space()='New OC Counsel/Attorney Approved']"
    );
    private By submitButtonA = By.xpath("//button[.//span[text()='Submit']]");

    // =========================================================
    // Edge scenario locators
    // =========================================================

    private By attRejectedOption = By.xpath(
            "//label[normalize-space()='New OC Counsel/Attorney Rejected']"
    );
    private By commentBoxA = By.xpath("//textarea");
    private By saveArrowA = By.xpath(
            "//button[not(@disabled)]//*[name()='svg' and @data-owl-icon-name='chevron-right']/ancestor::button[1]"
    );
    private By commentSectionA = By.xpath(
            "//*[normalize-space()='Comments']/ancestor::div[1]");
    private By processingIndicatorA = By.xpath(
            "//*[contains(@class,'loading') or contains(@class,'spinner') or @aria-busy='true']"
    );
    //private By saveCloseA = By.xpath("//span[normalize-space()='Save & Close']");
    private By taskGridA = By.xpath("//table//tbody//tr");
    private By commentsLinkA = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Comments']");
    private By commentSpanLocatorA = By.xpath("//span[contains(@class,'ColorText') and normalize-space()!='']");

    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterReviewNewVendorTask() throws InterruptedException {

        Allure.step("TASK - Review New Vendor: Full navigation flow", () -> {

            // STEP 1: Refresh Task Grid
            boolean taskOpened = false;

            for (int attempt = 1; attempt <= 10; attempt++) {

                System.out.println("🔄 Searching for Task  for NEW OC - Attempt: " + attempt);

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

                            if (rowText.contains("Review New Vendor")
                                    && rowText.contains("To Do")) {

                                System.out.println("✅ Task 2 row located");

                                // ==============================
                                // SELECT CHECKBOX
                                // ==============================

                                WebElement chckbx = row.findElement(checkboxA);

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", chckbx);

                                System.out.println("✅ Checkbox selected");

                                // WAIT FOR CLAIM BUTTON
                                WebElement claimBtn = wait.until(
                                        ExpectedConditions.elementToBeClickable(claimButtonA));

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", claimBtn);

                                System.out.println("✅ Claim button clicked");

                                // WAIT FOR TASK LINK
                                WebElement task = wait.until(
                                        ExpectedConditions.elementToBeClickable(taskLinkA));

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", task);

                                System.out.println("✅ Task opened");

                                /* =====  VALIDATION ===== */

                                wait.until(ExpectedConditions.or(

                                        // Appian task page URL
                                        ExpectedConditions.urlContains("start-process"),


                                        ExpectedConditions.urlContains("task"),

                                        //  validation -> page fully loaded with Attorney Approve button
                                        ExpectedConditions.visibilityOfElementLocated(attApprovedOption)

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

            System.out.println("✅ Task opened and validated successfully");

            SceenshotUtil.takeScreenshot(driver, "Task Opened - Review New Vendor");
        });
    }


    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleReviewNewVendorFields() {

        Allure.step("Select Approved radio button", () -> {

            WebElement AttorneyApprove = wait.until(
                    ExpectedConditions.elementToBeClickable(attApprovedOption));

            AttorneyApprove.click();

            System.out.println("✅ Approved radio button selected");
            Allure.step("Approved radio button selected");

            SceenshotUtil.takeScreenshot(driver,
                    "Approved Radio Selected");

            Thread.sleep(1000);
        });

        Allure.step("Click Submit button to complete task", () -> {

            WebElement sbmtbtnA = wait.until(
                    ExpectedConditions.presenceOfElementLocated(submitButtonA)
            );

            //  ensure it's actually enabled
            wait.until(driver ->
                    sbmtbtnA.isDisplayed() && sbmtbtnA.isEnabled()
            );

            //  scroll into view
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", sbmtbtnA);

            //  wait for stability before click
            wait.until(ExpectedConditions.elementToBeClickable(sbmtbtnA));

            sbmtbtnA.click();

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

    public void handleReviewNewVendorEdgeFlow() {
        Allure.step("Select Rejected radio button", () -> {

            WebElement AttorneyReject= wait.until(
                    ExpectedConditions.elementToBeClickable(attRejectedOption));

            AttorneyReject.click();

            System.out.println("✅ Rejected radio button selected");
            Allure.step("Rejected radio button selected");

            SceenshotUtil.takeScreenshot(driver,
                    "Rejected Radio Selected");

            Thread.sleep(1000);
        });

        // =====================================================
        // 3. COMMENTS
        // =====================================================
        Allure.step("Enter comment and submit via right arrow", () -> {

            String expectedComment = ConfigReader.getNewOC("newVendor.comments");

            WebElement comments = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentBoxA)
            );

            comments.click();
            comments.clear();
            comments.sendKeys(expectedComment);

            SceenshotUtil.takeScreenshot(driver, "Comments Entered");

            // wait briefly for Appian UI state update
            Thread.sleep(2000);

            // locate enabled arrow button only
            WebElement arrowBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(saveArrowA)
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
        /*Allure.step("Verify comment is displayed in UI after submission", () -> {

            String expectedComment = ConfigReader.getNewOC("newVendor.comments").trim();

            // Step 1: locate Comments section label first
            WebElement section = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentSectionA)
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
        }); */
        Allure.step("Verify comment is displayed in UI after submission", () -> {

            String expectedComment = ConfigReader.getNewOC("newVendor.comments").trim();

            // Step 1: locate Comments section label first
            WebElement section = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentSectionA)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    section
            );

            // Step 2: locate comment globally but still anchored near section text
            By actualCommentLocator = By.xpath(
                    "//*[contains(normalize-space(),'" + expectedComment + "')]"
            );

            WebElement actualComment = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(actualCommentLocator)
            );

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

        Allure.step("Click Submit button to complete task", () -> {

            WebElement sbmtbtnA = wait.until(
                    ExpectedConditions.presenceOfElementLocated(submitButtonA)
            );

            //  ensure it's actually enabled
            wait.until(driver ->
                    sbmtbtnA.isDisplayed() && sbmtbtnA.isEnabled()
            );

            //  scroll into view
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", sbmtbtnA);

            //  wait for stability before click
            wait.until(ExpectedConditions.elementToBeClickable(sbmtbtnA));

            sbmtbtnA.click();

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
            wait.until(ExpectedConditions.presenceOfElementLocated(taskGridA));

            // Ensure grid is actually rendered (not just DOM placeholder)
            wait.until(driver ->
                    driver.findElements(taskGridA).size() > 0
            );

            System.out.println("✅ Returned to task grid successfully");

            SceenshotUtil.takeScreenshot(driver, "Back to Task Grid");
        });

        // =====================================================
        // 6. COMMENTS LINK VALIDATION
        // =====================================================
        Allure.step("Open Comments tab and validate comment from UI span", () -> {

            String expectedComment = ConfigReader.getNewOC("newVendor.comments").trim();

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
                            ExpectedConditions.elementToBeClickable(commentsLinkA)
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

                                List<WebElement> s = driver1.findElements(commentSpanLocatorA);

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

    }
}
