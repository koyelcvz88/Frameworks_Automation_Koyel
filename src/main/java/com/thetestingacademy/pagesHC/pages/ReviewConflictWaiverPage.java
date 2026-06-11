package com.thetestingacademy.pagesHC.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReviewConflictWaiverPage extends CommonUIActions {

    private final DataModel testData;

    public ReviewConflictWaiverPage(WebDriver driver, DataModel testData) {
        super(driver);
        this.testData = testData;
        System.out.println("===== PAGE RECEIVED DATA =====");
        System.out.println(testData);
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
    private By isOCConflictedWaiverDropdown = By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value')][.//span[text()='---Select Yes/No---']]");
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
    private By taskGridC = By.xpath("//table//tbody//tr");
    private By commentsLinkC = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Comments']");
    private By commentSpanLocatorC = By.xpath("//span[contains(@class,'ColorText') and normalize-space()!='']");

    // =====================================================
    // STORED VALUES
    // =====================================================
    protected String isOCConflicted;

    // =========================================================
    // UTILITY METHOD (ADDED)
    // =========================================================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // =====================================================
    // SAFE HELPER
    // =====================================================
    private String safe(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Skipping " + field + " (NULL/EMPTY)");
            return null;
        }
        return value.trim();
    }

    // =========================================================
    // Navigation TASK 1
    // =========================================================
    public void openAndEnterReviewConflictWaiverTask() throws InterruptedException {

        Allure.step("TASK- Review Conflict Waiver: Full navigation flow", () -> {

            openTaskFromGrid(
                    By.xpath("//table//tbody//tr"),
                    checkboxC,
                    claimButtonC,
                    taskLinkC,
                    conflictApprovedOption,
                    "Review Conflict Waiver",
                    "To Do"
            );

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

            String value = safe(testData.getIsOcConflicted(), "Is OC Conflicted?");
            if (value == null) return;

            click(isOCConflictedWaiverDropdown);

            selectFromDynamicList(value);

            isOCConflicted  = value;

            System.out.println("'Is OC Conflicted?' selected value: " + isOCConflicted);
            Allure.step("'Is OC Conflicted?' selected value: " + isOCConflicted);
        });

        Allure.step("Entering Justification", () -> {

            String conflictJustification =
                    ConfigReader.getNewOC("conJustificationText");

            typeIntoFirstVisibleTextArea(conflictJustification);

            System.out.println("Conflict Waiver Justification entered successfully");
        });

        Allure.step("Select Approved radio button", () -> {

            click(conflictApprovedOption);

            System.out.println("✅ Approved radio button selected");

            Allure.step("Approved radio button selected");

            SceenshotUtil.takeScreenshot(driver, "Approved Radio Selected");
        });

        Allure.step("Click Submit button to complete task", () -> {

            clickSubmitAndValidate(submitButtonC);

            System.out.println("Submit button clicked");

            SceenshotUtil.takeScreenshot(driver, "Task Submitted");
        });
    }

    public void handleReviewConflictWaiverEdgeFlow() {

        // STEP 1: OC Conflicted
        Allure.step("Selecting second option for 'Is OC Conflicted?'", () -> {

            String value = safe(testData.getIsOcConflicted(), "Is OC Conflicted?");
            if (value == null) return;

            click(isOCConflictedWaiverDropdown);

            selectFromDynamicList(value);

            isOCConflicted  = value;

            System.out.println("'Is OC Conflicted?' selected value: " + isOCConflicted);
            Allure.step("'Is OC Conflicted?' selected value: " + isOCConflicted);
        });

        Allure.step("Entering Justification", () -> {

            String conflictJustification =
                    ConfigReader.getNewOC("conJustificationText");

            typeIntoFirstVisibleTextArea(conflictJustification);

            System.out.println("Conflict Waiver Justification entered successfully");
        });

        Allure.step("Select Rejected radio button", () -> {

            click(conflictRejectedOption);

            System.out.println("✅ Rejected radio button selected");

            Allure.step("Rejected radio button selected");

            SceenshotUtil.takeScreenshot(driver, "Rejected Radio Selected");
        });

        // =====================================================
        // 3. COMMENTS
        // =====================================================
        Allure.step("Enter comment and submit via right arrow", () -> {

            String expectedComment = ConfigReader.getNewOC("newConflict.comments");

            // type comment using framework
            type(commentBoxC, expectedComment);

            SceenshotUtil.takeScreenshot(driver, "Comments Entered");

            // wait for Appian UI stabilization (replaces Thread.sleep)
            waitForPageLoad();

            // locate arrow button
            WebElement arrowBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(saveArrowC)
            );

            scrollToElement(arrowBtn);

            // use framework click (JS fallback already inside)
            click(arrowBtn);

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

            // Step 1: wait for section
            WebElement section = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(commentSectionC)
            );

            scrollToElement(section);

            // Step 2: build locator dynamically (same logic preserved)
            By actualCommentLocator = By.xpath(
                    "//*[contains(normalize-space(),'" + expectedComment + "')]"
            );

            WebElement actualComment = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(actualCommentLocator)
            );

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

            clickSubmitAndValidate(submitButtonC);

            System.out.println("Submit button clicked");

            SceenshotUtil.takeScreenshot(driver, "Task Submitted");
        });

        // =====================================================
        // 5. NAVIGATE BACK TO GRID (implicit)
        // =====================================================
        Allure.step("Verify return to task grid", () -> {

            // Wait for grid to reappear after Save & Close navigation
            waitForVisible(taskGridC);

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

                        waitForPageLoad();

                        System.out.println("✅ Page refreshed");
                    }

                    // STEP 1: Click Comments Tab
                    WebElement commentsTab = wait.until(
                            ExpectedConditions.elementToBeClickable(commentsLinkC)
                    );

                    scrollToElement(commentsTab);

                    try {
                        click(commentsTab);
                    } catch (Exception e) {
                        System.out.println("⚠️ JS click fallback used");
                        jsClick(commentsTab);
                    }

                    System.out.println("✅ Comments tab clicked");

                    // STEP 2: WAIT FOR COMMENT SPANS
                    List<WebElement> spans = new WebDriverWait(driver, Duration.ofSeconds(20))
                            .until(d -> {

                                List<WebElement> s = d.findElements(commentSpanLocatorC);

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
