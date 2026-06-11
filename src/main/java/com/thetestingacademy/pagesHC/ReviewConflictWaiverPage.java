package com.thetestingacademy.pagesHC;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReviewConflictWaiverPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public ReviewConflictWaiverPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================
    private By checkboxC = By.xpath(".//input[@type='checkbox']");
    private By taskLinkC = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Review Conflict Waiver']]//td//a[normalize-space()='Review Conflict Waiver']"
    );
    private By claimButtonC = By.xpath("//button[.//span[normalize-space()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================
    //private By isOCConflictedWaiver = By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value')][.//span[text()='---Select Yes/No---']]");
    private String isOCConflictedWaiver;
    private By conflictJus = By.xpath("//strong[contains(text(),'Conflict Waiver Justification')]/following::textarea[1]");
    private By conflictApprovedOption = By.xpath(
            "//label[normalize-space()='Conflict Waiver Approved']");
    private By submitButtonC = By.xpath("//button[.//span[text()='Submit']]");

    // =========================================================
    // Edge scenario locators
    // =========================================================

    private By conflictRejectedOption = By.xpath(
            "//label[normalize-space()='Conflict Waiver Rejected']");
    private By commentBoxC = By.xpath("//textarea");
    private By saveArrowC = By.xpath(
            "//button[not(@disabled)]//*[name()='svg' and @data-owl-icon-name='chevron-right']/ancestor::button[1]"
    );
    private By commentSectionC = By.xpath(
            "//*[normalize-space()='Comments']/ancestor::div[1]");
    /*private By processingIndicatorB = By.xpath(
            "//*[contains(@class,'loading') or contains(@class,'spinner') or @aria-busy='true']"
    ); */
    //private By saveCloseB = By.xpath("//span[normalize-space()='Save & Close']");
    private By taskGridC = By.xpath("//table//tbody//tr");
    private By commentsLinkC = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Comments']");
    private By commentSpanLocatorC = By.xpath("//span[contains(@class,'ColorText') and normalize-space()!='']");

    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterReviewConflictWaiverTask() throws InterruptedException {

        Allure.step("TASK- Review Conflict Waiver: Full navigation flow", () -> {

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

                            if (rowText.contains("Review Conflict Waiver")
                                    && rowText.contains("To Do")) {

                                System.out.println("✅ Task 3 row located");

                                // ==============================
                                // SELECT CHECKBOX
                                // ==============================

                                WebElement chckbx = row.findElement(checkboxC);

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", chckbx);

                                System.out.println("✅ Checkbox selected");

                                // WAIT FOR CLAIM BUTTON
                                WebElement claimBtn = wait.until(
                                        ExpectedConditions.elementToBeClickable(claimButtonC));

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", claimBtn);

                                System.out.println("✅ Claim button clicked");

                                // WAIT FOR TASK LINK
                                WebElement task = wait.until(
                                        ExpectedConditions.elementToBeClickable(taskLinkC));

                                ((JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", task);

                                System.out.println("✅ Task opened");

                                /* =====  VALIDATION ===== */

                                wait.until(ExpectedConditions.or(

                                        // Appian task page URL
                                        ExpectedConditions.urlContains("start-process"),


                                        ExpectedConditions.urlContains("task"),

                                        //  validation -> page fully loaded with Conflict Waiver button
                                        ExpectedConditions.visibilityOfElementLocated(conflictApprovedOption)

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
                        "❌ Task 3 could not be opened after retries");
            }

            System.out.println("✅ Task opened and validated successfully");

            SceenshotUtil.takeScreenshot(driver, "Task Opened - Review Conflict Waiver");
        });
    }


    // =========================================================
    // Task UI - Fields
    // =========================================================
    public void handleReviewConflictWaiverFields() {

        // STEP 1: OC Conflicted
        Allure.step("Selecting second option for 'Is OC Conflicted?'", () -> {

            WebElement dropdown = driver.findElement(
                    By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value')][.//span[text()='---Select Yes/No/Previously Waived---']]")
            );

            dropdown.click();

            actions.sendKeys(Keys.ARROW_DOWN).perform();
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            actions.sendKeys(Keys.ENTER).perform();

            isOCConflictedWaiver = dropdown.findElement(By.tagName("span")).getText();

            System.out.println("'Is OC Conflicted?' selected value: " + isOCConflictedWaiver);
            Allure.step("'Is OC Conflicted?' selected value: " + isOCConflictedWaiver);
        });


        // STEP 2: Justification
        /*Allure.step("Entering Justification", () -> {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            String conflictJustification = ConfigReader.getNewOC("conJustificationText");

            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(conflictJus)
            );
            field.sendKeys(conflictJustification);
        }); */

        Allure.step("Entering Justification", () -> {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            String conflictJustification =
                    ConfigReader.getNewOC("conJustificationText");

            // Wait for Appian refresh completion
            Thread.sleep(3000);

            // Locate ALL visible textareas after refresh
            List<WebElement> textAreas = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.tagName("textarea")
                    )
            );

            WebElement targetField = null;

            for (WebElement area : textAreas) {

                try {

                    if (area.isDisplayed() && area.isEnabled()) {

                        targetField = area;

                        break;
                    }

                } catch (Exception ignored) {
                }
            }

            if (targetField == null) {

                throw new RuntimeException(
                        "Conflict Waiver Justification textarea not found"
                );
            }

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    targetField
            );

            wait.until(ExpectedConditions.visibilityOf(targetField));

            wait.until(ExpectedConditions.elementToBeClickable(targetField));

            targetField.clear();

            targetField.sendKeys(conflictJustification);

            System.out.println(
                    "Conflict Waiver Justification entered successfully"
            );
        });
            Allure.step("Select Approved radio button", () -> {

                WebElement AttorneyApprove = wait.until(
                        ExpectedConditions.elementToBeClickable(conflictApprovedOption));

                AttorneyApprove.click();

                System.out.println("✅ Approved radio button selected");
                Allure.step("Approved radio button selected");

                SceenshotUtil.takeScreenshot(driver,
                        "Approved Radio Selected");

                Thread.sleep(1000);
            });
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
    }

    public void handleReviewConflictWaiverEdgeFlow() {
        Allure.step("Select Rejected radio button", () -> {

            WebElement AttorneyReject= wait.until(
                    ExpectedConditions.elementToBeClickable(conflictRejectedOption));

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

            String expectedComment = ConfigReader.getNewOC("newConflict.comments");

            WebElement comments = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentBoxC)
            );

            comments.click();
            comments.clear();
            comments.sendKeys(expectedComment);

            SceenshotUtil.takeScreenshot(driver, "Comments Entered");

            // wait briefly for Appian UI state update
            Thread.sleep(2000);

            // locate enabled arrow button only
            WebElement arrowBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(saveArrowC)
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

            String expectedComment = ConfigReader.getNewOC("newConflict.comments").trim();

            // Step 1: locate Comments section label first
            WebElement section = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentSectionC)
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
        // 6. COMMENTS LINK VALIDATION
        // =====================================================
        Allure.step("Open Comments tab and validate comment from UI span", () -> {

            String expectedComment = ConfigReader.getNewOC("newConflict.comments").trim();

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
                            ExpectedConditions.elementToBeClickable(commentsLinkC)
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

                                List<WebElement> s = driver1.findElements(commentSpanLocatorC);

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
