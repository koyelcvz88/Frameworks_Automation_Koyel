package com.thetestingacademy.pages;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.thetestingacademy.actions.CommonUIActions;

import java.time.Duration;
import java.util.List;
public class Update_CancelRequestPage extends CommonUIActions {

    public Update_CancelRequestPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================

    private By UpdateRequestBtn =
            By.xpath("//button[.//span[normalize-space()='Update Request']]");

    private By DescriptionBox =
            By.xpath("//label[normalize-space()='Description']/following::textarea[1]");

    private By SubmitBtn =
            By.xpath("//button[.//span[normalize-space()='Submit']]");
    private By DescriptionReadOnly = By.xpath(
            "//span[text()='Description']/ancestor::div[contains(@class,'FieldLayout')]//p"
    );

    private  By CancelRequestBtn =
            By.xpath("//span[normalize-space()='Cancel Request']");

    private  By JustificationBox =
            By.xpath("//label[contains(.,'Justification')]/following::textarea[1]");

    private By CancelRequestModalBtn = By.xpath(
            "//button[.//span[normalize-space()='Cancel Request']]");
    private  By CancelledStatus = By.xpath(
            "//div[contains(@class,'TagItem---tag')]" +
                    "//span[normalize-space()='Cancelled']");

    // ================= MAIN FLOW =================

    public void handleEnterUpdateRequestFields() {

        // =========================================================
        // CLICK UPDATE REQUEST
        // =========================================================


        Allure.step("Update Request Flow", () -> {

            Allure.step("Click Update Request button", () -> {

                // STEP 1: Ensure page is stable
                waitForPageLoad();

                // STEP 2: Wait for Appian loaders/spinners to disappear (safe fallback)
                try {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(
                            By.xpath("//div[contains(@class,'loading') or contains(@class,'spinner') or contains(@class,'appian-spinner')]")
                    ));
                } catch (Exception ignored) {}

                // STEP 3: Wait for button
                waitForVisible(UpdateRequestBtn);
                waitForClickable(UpdateRequestBtn);

                WebElement updateBtn = driver.findElement(UpdateRequestBtn);

                // STEP 4: Scroll using framework
                scrollToElement(updateBtn);

                // Debug logs
                System.out.println("Button displayed: " + updateBtn.isDisplayed());
                System.out.println("Button enabled: " + updateBtn.isEnabled());

                // STEP 5: Click using CommonUIActions (NO JS, NO DIRECT CLICK)
                click(updateBtn);

                // STEP 6: Validate modal/dialog appears
                By modalLocator = By.xpath(
                        "//div[@role='dialog' or contains(@class,'Modal') or contains(@class,'Dialog')]"
                );

                waitForVisible(modalLocator);

                // STEP 7: Ensure at least one modal is visible
                wait.until(driver ->
                        driver.findElements(modalLocator)
                                .stream()
                                .anyMatch(WebElement::isDisplayed)
                );

                System.out.println("✅ Update Request button is clicked");

                Allure.step("Update Request button is clicked");

                SceenshotUtil.takeScreenshot(driver, "Update_Request_Button_Clicked");
            });
                // =========================
                // 2. ENTER DESCRIPTION
                // =========================
            Allure.step("Enter Description", () -> {

                String description = ConfigReader.getData("existing.desc");

                WebElement desc = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(DescriptionBox)
                );

                waitForClickable(DescriptionBox);

                scrollToElement(desc);

                click(desc);

                desc.clear();

                desc.sendKeys(description);

                // optional: validate input is actually set
                wait.until(driver ->
                        desc.getAttribute("value") != null &&
                                !desc.getAttribute("value").isEmpty()
                );

                System.out.println("✅ Description entered");

                Allure.step("Description entered");

                SceenshotUtil.takeScreenshot(driver, "Description entered");
            });
                // =========================
                // 3. CLICK SUBMIT
                // =========================
            Allure.step("Click Submit", () -> {

                // Wait for button to be ready
                waitForVisible(SubmitBtn);
                waitForClickable(SubmitBtn);

                WebElement submit = driver.findElement(SubmitBtn);

                scrollToElement(submit);

                // CommonUIActions click (safe fallback included)
                click(submit);

                // IMPORTANT: wait for modal/dialog to close
                By modalLocator = By.xpath(
                        "//div[@role='dialog' or contains(@class,'Modal') or contains(@class,'Dialog')]"
                );

                wait.until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));

                System.out.println("✅ Submit button is clicked");

                Allure.step("Submit button is clicked");

                SceenshotUtil.takeScreenshot(driver, "Submit button is clicked");
            });

                // ================================
                // 4. VALIDATE DESCRIPTION
                // ================================
                Allure.step("Validate Description", () -> {

                    WebElement descValue = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    DescriptionReadOnly
                            )
                    );

                    String description =
                            descValue.getText().trim();

                    System.out.println("Description Value = " + description);
                    System.out.println("RAW TEXT = [" + description + "]");

                    assert description != null;
                    assert !description.equals("-");
                    assert !description.isEmpty();
                });
            });
        }

    // ================= CANCEL FLOW =================

    public void handleEnterCancelRequestFields() {

        Allure.step("Cancel Request Flow", () -> {

            // =====================================================
            // CLICK CANCEL REQUEST BUTTON
            // =====================================================

            WebElement cancelBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            CancelRequestBtn));

            /*((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();",
                            cancelBtn); */
            jsClick(cancelBtn);

            System.out.println("✅ Cancel Request button clicked");

            SceenshotUtil.takeScreenshot(driver,
                    "Cancel_Request_Button_Clicked");

            Thread.sleep(3000);

            // =====================================================
            // ENTER JUSTIFICATION
            // =====================================================
            Allure.step("Enter Justification", () -> {

                String justification =
                        ConfigReader.getData("existing.jus");

                WebElement justificationField = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                JustificationBox));

                /*((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView({block:'center'});",
                                justificationField); */

                scrollToElement(justificationField);

                Thread.sleep(2000);

                justificationField.click();

                Thread.sleep(1000);

                // CLEAR USING CTRL+A
                justificationField.sendKeys(Keys.CONTROL + "a");
                justificationField.sendKeys(Keys.DELETE);

                Thread.sleep(1000);

                // APPPIAN SAFE INPUT
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value=arguments[1];",
                        justificationField,
                        justification);

                // TRIGGER EVENTS
                /*((JavascriptExecutor) driver).executeScript(
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                        justificationField);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                        justificationField); */
                triggerInputAndChangeEvents(justificationField);

                Thread.sleep(1000);

                // IMPORTANT FOR APPIAN
                justificationField.sendKeys(Keys.TAB);

                Thread.sleep(2000);

                String enteredValue =
                        justificationField.getAttribute("value");

                System.out.println("Entered Justification = "
                        + enteredValue);

                if (enteredValue == null ||
                        enteredValue.trim().isEmpty()) {

                    throw new RuntimeException(
                            "❌ Justification value NOT retained");
                }

                System.out.println("✅ Justification retained successfully");

                SceenshotUtil.takeScreenshot(driver,
                        "Justification_Entered");
            });
            // =====================================================
            // CLICK MODAL CANCEL REQUEST
            // =====================================================

            Allure.step("Click Cancel Request button", () -> {

                List<WebElement> cancelButtons =
                        wait.until(ExpectedConditions
                                .visibilityOfAllElementsLocatedBy(CancelRequestModalBtn));

                // SECOND BUTTON = MODAL BUTTON
                WebElement modalCancelBtn = cancelButtons.get(1);

                /*((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView({block:'center'});",
                                modalCancelBtn); */
                scrollToElement(modalCancelBtn);

                Thread.sleep(2000);

                //wait.until(ExpectedConditions.elementToBeClickable(modalCancelBtn));
                waitForClickable(modalCancelBtn);

                // NORMAL CLICK FIRST
                try {
                    modalCancelBtn.click();
                } catch (Exception e) {

                    // JS FALLBACK
                    /*((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();",
                                    modalCancelBtn); */
                    jsClick(modalCancelBtn);
                }

                System.out.println("✅ Clicked Cancel Request button");

                Thread.sleep(4000);

                // VALIDATE NO REQUIRED ERROR
                List<WebElement> validationErrors =
                        driver.findElements(By.xpath(
                                "//*[contains(text(),'A value is required')]"));

                if (!validationErrors.isEmpty()) {

                    throw new RuntimeException(
                            "❌ Justification validation still displayed");
                }

                // VALIDATE MODAL CLOSED
                //wait.until(ExpectedConditions.invisibilityOf(modalCancelBtn));
                waitForInvisibility(modalCancelBtn);

                System.out.println("✅ Cancel Request modal closed");

                SceenshotUtil.takeScreenshot(driver,
                        "Cancel_Request_Completed");
            });
            // ===============================
            // VALIDATE STATUS = CANCELLED
            // ===============================

            Allure.step("Validate Request Status is Cancelled", () -> {

                WebElement cancelledStatus = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                CancelledStatus));

                // SCROLL TO STATUS
                /*((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView({block:'center'});",
                                cancelledStatus); */
                scrollToElement(cancelledStatus);

                Thread.sleep(2000);

                String actualStatus =
                        cancelledStatus.getText().trim();

                System.out.println("Request Status = "
                        + actualStatus);

                // HARD ASSERT
                if (!actualStatus.equalsIgnoreCase("Cancelled")) {

                    throw new RuntimeException(
                            "❌ Request status NOT updated to Cancelled");
                }

                System.out.println("✅ Request status successfully updated to Cancelled");

                SceenshotUtil.takeScreenshot(driver,
                        "Request_Status_Cancelled");
            });
        });
    }
}