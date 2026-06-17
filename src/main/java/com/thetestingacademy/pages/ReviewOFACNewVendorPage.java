package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReviewOFACNewVendorPage extends CommonUIActions {

    public ReviewOFACNewVendorPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================
    private By checkboxB = By.xpath(".//input[@type='checkbox']");
    private By taskLinkB = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Review OFAC For New Vendor']]//td//a[normalize-space()='Review OFAC For New Vendor']"
    );
    private By claimButtonB = By.xpath("//button[.//span[normalize-space()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    private By OFACApprovedOption = By.xpath(
            "//label[normalize-space()='OFAC Approved']"
    );
    private By submitButtonB = By.xpath("//button[.//span[text()='Submit']]");

    // =========================================================
    // Edge scenario locators
    // =========================================================

    private By OFACRejectedOption = By.xpath(
            "//label[normalize-space()='OFAC Rejected']"
    );
    private By commentBoxB = By.xpath("//textarea");
    private By saveArrowB = By.xpath(
            "//button[not(@disabled)]//*[name()='svg' and @data-owl-icon-name='chevron-right']/ancestor::button[1]"
    );
    private By commentSectionB = By.xpath(
            "//*[normalize-space()='Comments']/ancestor::div[1]");
    /*private By processingIndicatorB = By.xpath(
            "//*[contains(@class,'loading') or contains(@class,'spinner') or @aria-busy='true']"
    ); */
    //private By saveCloseB = By.xpath("//span[normalize-space()='Save & Close']");
    private By taskGridB = By.xpath("//table//tbody//tr");
    private By commentsLinkB = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Comments']");
    private By commentSpanLocatorB = By.xpath("//span[contains(@class,'ColorText') and normalize-space()!='']");

    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterReviewOFACNewVendorTask() throws InterruptedException {

        Allure.step("TASK - Review OFAC New Vendor: Full navigation flow", () -> {

            // STEP 1: Refresh Task Grid
            boolean taskOpened = false;

            for (int attempt = 1; attempt <= 10; attempt++) {

                System.out.println("🔄 Searching for Review OFAC Task for NEW OC - Attempt: " + attempt);

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

                            if (rowText.contains("Review OFAC For New Vendor")
                                    && rowText.contains("To Do")) {

                                System.out.println("✅ Task 2 row located");

                                // ==============================
                                // SELECT CHECKBOX
                                // ==============================

                                WebElement chckbx = row.findElement(checkboxB);

                                /*((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", chckbx); */

                                jsClick(chckbx);

                                System.out.println("✅ Checkbox selected");

                                // WAIT FOR CLAIM BUTTON
                                WebElement claimBtn = wait.until(
                                        ExpectedConditions.elementToBeClickable(claimButtonB));

                                /*((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", claimBtn); */
                                jsClick(claimBtn);

                                System.out.println("✅ Claim button clicked");

                                // WAIT FOR TASK LINK
                                /*WebElement task = wait.until(
                                        ExpectedConditions.elementToBeClickable(taskLinkB)); */

                                waitForClickable(taskLinkB);
                                WebElement task = driver.findElement(taskLinkB);

                                /*((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", task); */
                                jsClick(task);

                                System.out.println("✅ Task opened");

                                /* =====  VALIDATION ===== */

                                wait.until(ExpectedConditions.or(

                                        // Appian task page URL
                                        ExpectedConditions.urlContains("start-process"),


                                        ExpectedConditions.urlContains("task"),

                                        //  validation -> page fully loaded with OFAC Approve button
                                        ExpectedConditions.visibilityOfElementLocated(OFACApprovedOption)
                                        //waitForVisible(OFACApprovedOption)

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

            SceenshotUtil.takeScreenshot(driver, "Task Opened - Review OFAC For New Vendor");
        });
    }

    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleReviewOFACNewVendorFields() {

        Allure.step("Select Approved radio button", () -> {

            /*WebElement AttorneyApprove = wait.until(
                    ExpectedConditions.elementToBeClickable(OFACApprovedOption)
            ); */

            waitForClickable(OFACApprovedOption);
            WebElement AttorneyApprove = driver.findElement(OFACApprovedOption);

            click(AttorneyApprove);

            System.out.println("✅ Approved radio button selected");
            Allure.step("Approved radio button selected");

            SceenshotUtil.takeScreenshot(driver,
                    "Approved Radio Selected");

            Thread.sleep(1000);
        });

        Allure.step("Click Submit button to complete task", () -> {

            /*WebElement sbmtbtnB = wait.until(
                    ExpectedConditions.presenceOfElementLocated(submitButtonB)
            ); */

            waitForVisible(submitButtonB);
            WebElement sbmtbtnB = driver.findElement(submitButtonB);

            // ensure it's actually enabled
            wait.until(driver ->
                    sbmtbtnB.isDisplayed() && sbmtbtnB.isEnabled()
            );

            // scroll into view
            /*((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", sbmtbtnB); */
            scrollToElement(sbmtbtnB);

            // wait for stability before click
            wait.until(ExpectedConditions.elementToBeClickable(sbmtbtnB));
            //waitForClickable(sbmtbtnB);

            click(sbmtbtnB);

            System.out.println("✅ Submit button clicked");

            // WAIT FOR POST-SUBMIT STATE CHANGE
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

    public void handleReviewOFACNewVendorEdgeFlow() {
        Allure.step("Select Rejected radio button", () -> {

            /*WebElement AttorneyReject = wait.until(
                    ExpectedConditions.elementToBeClickable(OFACRejectedOption)
            ); */

            waitForClickable(OFACRejectedOption);
            WebElement AttorneyReject = driver.findElement(OFACRejectedOption);

            click(AttorneyReject);

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

            String expectedComment = ConfigReader.getNewOC("newOFAC.comments");

            /*WebElement comments = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentBoxB)
            ); */

            waitForVisible(commentBoxB);
            WebElement comments = driver.findElement(commentBoxB);

            comments.click();
            comments.clear();
            comments.sendKeys(expectedComment);

            SceenshotUtil.takeScreenshot(driver, "Comments Entered");

            // wait briefly for Appian UI state update
            Thread.sleep(2000);

            // locate enabled arrow button only
            /*WebElement arrowBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(saveArrowB)
            ); */

            waitForVisible(saveArrowB);
            WebElement arrowBtn = driver.findElement(saveArrowB);

            scrollToElement(arrowBtn);

            // JS click kept (Appian stability requirement - no logic change)
            jsClick(arrowBtn);

            // wait until comment appears in DOM
            wait.until(driver ->
                    driver.getPageSource().contains(expectedComment)
            );

            System.out.println("✅ Comment submitted");
            Allure.step("Comment submitted");

            SceenshotUtil.takeScreenshot(driver, "Comment Saved");
        });

        Allure.step("Verify comment is displayed in UI after submission", () -> {

            String expectedComment = ConfigReader.getNewOC("newOFAC.comments").trim();

            // Step 1: locate Comments section label first
            /*WebElement section = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentSectionB)
            ); */

            waitForVisible(commentSectionB);
            WebElement section = driver.findElement(commentSectionB);

            scrollToElement(section);

            // Step 2: locate comment globally but still anchored near section text
            By actualCommentLocator = By.xpath(
                    "//*[contains(normalize-space(),'" + expectedComment + "')]"
            );

            /*WebElement actualComment = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(actualCommentLocator)
            ); */

            waitForVisible(actualCommentLocator);
            WebElement actualComment = driver.findElement(actualCommentLocator);

            scrollToElement(actualComment);

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

            /*WebElement sbmtbtnB = wait.until(
                    ExpectedConditions.presenceOfElementLocated(submitButtonB)
            ); */

            waitForVisible(submitButtonB);
            WebElement sbmtbtnB = driver.findElement(submitButtonB);

            // ensure it's actually enabled
            wait.until(driver ->
                    sbmtbtnB.isDisplayed() && sbmtbtnB.isEnabled()
            );

            // scroll into view
            /*((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", sbmtbtnB); */
            scrollToElement(sbmtbtnB);

            // wait for stability before click
            wait.until(ExpectedConditions.elementToBeClickable(sbmtbtnB));
            //waitForClickable(sbmtbtnB);

            click(sbmtbtnB);

            System.out.println("✅ Submit button clicked");

            // WAIT FOR POST-SUBMIT STATE CHANGE
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
        // =====================================================
        // 5. NAVIGATE BACK TO GRID (implicit)
        // =====================================================
        Allure.step("Verify return to task grid", () -> {

            // Wait for grid to reappear after Save & Close navigation
            //wait.until(ExpectedConditions.presenceOfElementLocated(taskGridB));
            waitForVisible(taskGridB);
            driver.findElement(taskGridB);

            // Ensure grid is actually rendered (not just DOM placeholder)
            wait.until(driver ->
                    driver.findElements(taskGridB).size() > 0
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

                        /*wait.until(webDriver ->
                                ((JavascriptExecutor) webDriver)
                                        .executeScript("return document.readyState")
                                        .equals("complete")
                        ); */
                        waitForPageLoad();

                        System.out.println("✅ Page refreshed");
                    }

                    // STEP 1: Click Comments Tab
                    /*WebElement commentsTab = wait.until(
                            ExpectedConditions.elementToBeClickable(commentsLinkB)
                    ); */

                    waitForClickable(commentsLinkB);
                    WebElement commentsTab = driver.findElement(commentsLinkB);

                    scrollToElement(commentsTab);

                    try {
                        commentsTab.click();
                    } catch (Exception e) {
                        System.out.println("⚠️ JS click fallback used");
                        jsClick(commentsTab);
                    }

                    System.out.println("✅ Comments tab clicked");

                    // STEP 2: WAIT FOR COMMENT SPANS
                    List<WebElement> spans = new WebDriverWait(driver, Duration.ofSeconds(20))
                            .until(driver1 -> {

                                List<WebElement> s = driver1.findElements(commentSpanLocatorB);

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
        });
    }
}