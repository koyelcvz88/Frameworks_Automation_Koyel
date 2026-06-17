package com.thetestingacademy.OldCode;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CommonUIActions2 {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public CommonUIActions2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // =========================================================
    // CLICK (WebElement)
    // =========================================================
    public void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            jsClick(element);
        }
    }

    // =========================================================
    // CLICK (By)
    // =========================================================
    public void click(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (Exception e) {
            jsClick(driver.findElement(locator));
        }
    }

    // =========================================================
    // JS CLICK
    // =========================================================
    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    public void jsClick(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    // =========================================================
    // TYPE
    // =========================================================
    public void type(By locator, String value) {

        if (value == null || value.trim().isEmpty()) {
            System.out.println("⚠️ Skipping type - NULL/EMPTY value");
            return;
        }

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        element.clear();
        element.sendKeys(value);
    }

    // =========================================================
    // TYPE INTO FIRST VISIBLE TEXTAREA
    // =========================================================
    public void typeIntoFirstVisibleTextArea(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Textarea value is null or empty");
        }

        waitForPageLoad();

        List<WebElement> textAreas = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("textarea"))
        );

        WebElement targetField = null;

        for (WebElement area : textAreas) {
            try {
                if (area.isDisplayed() && area.isEnabled()) {
                    targetField = area;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (targetField == null) {
            throw new RuntimeException("No visible/enabled textarea found");
        }

        scrollToElement(targetField);

        wait.until(ExpectedConditions.visibilityOf(targetField));
        wait.until(ExpectedConditions.elementToBeClickable(targetField));

        targetField.clear();
        targetField.sendKeys(value);
    }

    // =========================================================
    // FILE UPLOAD (NEW)
    // =========================================================
    public void uploadFile(By locator, String filePath) {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new RuntimeException("❌ File path is null or empty");
        }

        waitForVisible(locator);

        WebElement input = driver.findElement(locator);
        input.sendKeys(filePath);

        System.out.println("📁 File uploaded using locator: " + filePath);
    }

    public void uploadFile(WebElement element, String filePath) {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new RuntimeException("❌ File path is null or empty");
        }

        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(filePath);

        System.out.println("📁 File uploaded using WebElement: " + filePath);
    }

    // =========================================================
    // FILE UPLOAD (HIDDEN INPUT / APPIAN SAFE)
    // =========================================================
    public void uploadFileUsingPresence(By locator, String filePath) {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new RuntimeException("❌ File path is null or empty");
        }

        try {

            waitForPageLoad();
            waitForUIRender();

            WebElement input = wait.until(
                    ExpectedConditions.presenceOfElementLocated(locator)
            );

            System.out.println("📁 Upload input located");
            System.out.println("📁 Uploading file: " + filePath);

            input.sendKeys(filePath);

            System.out.println("✅ File uploaded successfully");

        } catch (TimeoutException e) {

            int count = driver.findElements(locator).size();

            throw new RuntimeException(
                    "❌ Upload input not found within timeout. Elements found: "
                            + count
                            + " | Locator: "
                            + locator,
                    e
            );
        }
    }

    // =========================================================
    // ENTER COMMENT + SUBMIT (NEW FINAL METHOD)
    // =========================================================
    public void enterCommentAndSubmit(By commentBox,
                                      By submitArrow,
                                      String commentText) {

        if (commentText == null || commentText.trim().isEmpty()) {
            throw new RuntimeException("Comment text is null/empty");
        }

        WebElement commentField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(commentBox)
        );

        wait.until(ExpectedConditions.elementToBeClickable(commentField));

        commentField.click();
        commentField.clear();
        commentField.sendKeys(commentText);

        System.out.println("✅ Comment entered");

        waitForUIRender();

        WebElement arrowBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(submitArrow)
        );

        scrollToElement(arrowBtn);

        wait.until(ExpectedConditions.elementToBeClickable(arrowBtn));

        click(arrowBtn);

        wait.until(driver ->
                driver.getPageSource().contains(commentText)
        );

        System.out.println("✅ Comment submitted successfully");
    }

    // =========================================================
    // SUBMIT + VALIDATION FLOW
    // =========================================================
    public void clickSubmitAndValidate(By submitLocator) {

        WebElement submitBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(submitLocator)
        );

        wait.until(d -> submitBtn.isDisplayed() && submitBtn.isEnabled());

        scrollToElement(submitBtn);

        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));

        click(submitBtn);

        System.out.println("✅ Submit button clicked");

        boolean urlChanged = wait.until(driver ->
                !driver.getCurrentUrl().contains("start-process")
        );

        if (!urlChanged) {
            throw new AssertionError(
                    "Submit click did not navigate away - likely validation failure"
            );
        }

        boolean validationErrorPresent = !driver.findElements(
                By.xpath("//*[contains(text(),'cannot') or contains(text(),'invalid') or contains(text(),'required')]")
        ).isEmpty();

        if (validationErrorPresent) {
            throw new AssertionError("Validation error detected after submit");
        }
    }

    // =========================================================
    // TASK GRID FLOW
    // =========================================================
    public void openTaskFromGrid(By rowLocator,
                                 By checkboxLocator,
                                 By claimButtonLocator,
                                 By taskLinkLocator,
                                 By validationLocator,
                                 String text1,
                                 String text2) {

        boolean taskOpened = false;

        for (int attempt = 1; attempt <= 10; attempt++) {

            System.out.println("🔄 Searching Task Attempt: " + attempt);

            try {

                waitForVisible(rowLocator);

                List<WebElement> rows = driver.findElements(rowLocator);

                for (int i = 0; i < rows.size(); i++) {

                    try {

                        rows = driver.findElements(rowLocator);
                        WebElement row = rows.get(i);

                        String text = row.getText();

                        if (text.contains(text1) && text.contains(text2)) {

                            WebElement checkbox = row.findElement(checkboxLocator);
                            jsClick(checkbox);

                            waitForClickable(claimButtonLocator);
                            jsClick(claimButtonLocator);

                            waitForClickable(taskLinkLocator);
                            jsClick(taskLinkLocator);

                            waitForTaskPageReady(validationLocator);

                            taskOpened = true;
                            break;
                        }

                    } catch (StaleElementReferenceException ignored) {}
                }

                if (taskOpened) break;

                driver.navigate().refresh();
                waitForPageLoad();

            } catch (Exception e) {

                driver.navigate().refresh();
                waitForPageLoad();
            }
        }

        if (!taskOpened) {
            throw new RuntimeException("❌ Task not found after retries");
        }
    }

    // =========================================================
    // TASK PAGE READY VALIDATION
    // =========================================================
    public void waitForTaskPageReady(By locator) {

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("start-process"),
                ExpectedConditions.urlContains("task"),
                ExpectedConditions.visibilityOfElementLocated(locator)
        ));
    }

    // =========================================================
    // DROPDOWN HANDLER
    // =========================================================
    public void selectFromDynamicList(String valueToSelect) {

        if (valueToSelect == null || valueToSelect.trim().isEmpty()) {
            return;
        }

        By optionLocator = By.xpath("//*[(@role='option' or @role='listitem')]");

        wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
        waitForUIRender();

        List<WebElement> options = driver.findElements(optionLocator);

        for (WebElement option : options) {

            try {
                String text = option.getText();

                if (text.equalsIgnoreCase(valueToSelect)
                        || text.contains(valueToSelect)
                        || valueToSelect.contains(text)) {

                    click(option);
                    waitForUIRender();
                    return;
                }

            } catch (StaleElementReferenceException ignored) {}
        }

        throw new RuntimeException("❌ Value NOT found in dropdown");
    }

    // =========================================================
    // CHECKBOX
    // =========================================================
    public void check(By locator) {
        WebElement element = driver.findElement(locator);
        if (!element.isSelected()) element.click();
    }

    // =========================================================
    // WAIT HELPERS
    // =========================================================
    public void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // =========================================================
    // PAGE LOAD WAIT
    // =========================================================
    public void waitForPageLoad() {

        new WebDriverWait(driver, Duration.ofSeconds(25))
                .until(d ->
                        ((JavascriptExecutor) d)
                                .executeScript("return document.readyState")
                                .equals("complete")
                );
    }

    // =========================================================
    // SCROLL
    // =========================================================
    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // =========================================================
    // BLUR
    // =========================================================
    public void jsBlur() {
        ((JavascriptExecutor) driver)
                .executeScript("document.activeElement.blur();");
    }

    // =========================================================
    // SAFE UI WAIT
    // =========================================================
    protected void waitForUIRender() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}