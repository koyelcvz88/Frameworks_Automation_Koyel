package com.thetestingacademy.pages;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class Update_CancelRequestPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public Update_CancelRequestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
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

                WebElement updateBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(UpdateRequestBtn)
                );

                updateBtn.click();

                // ✅ HARD ASSERT: modal must actually appear (not just DOM text)
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@role='dialog' or contains(@class,'Modal') or contains(@class,'Dialog')]")
                ));

                // extra stability: ensure modal is really rendered
                wait.until(driver -> {
                    List<WebElement> modals =
                            driver.findElements(By.xpath("//div[@role='dialog' or contains(@class,'Modal') or contains(@class,'Dialog')]"));
                    return modals.stream().anyMatch(WebElement::isDisplayed);
                });
                System.out.println("✅ Update Request button is clicked");
                Allure.step("Update Request button is clicked");

                SceenshotUtil.takeScreenshot(driver, "Update Request button is clicked");
            });


            // =========================
            // 2. ENTER DESCRIPTION
            // =========================
            Allure.step("Enter Description", () -> {

                By descLocator = DescriptionBox;

                WebElement desc = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(descLocator)
                );

                wait.until(ExpectedConditions.elementToBeClickable(descLocator));

                desc.click();
                desc.clear();

                desc.sendKeys(ConfigReader.getData("existing.desc"));

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

                By submitLocator = SubmitBtn;

                WebElement submit = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(submitLocator)
                );

                wait.until(ExpectedConditions.elementToBeClickable(submitLocator));

                submit.click();

                // ✅ IMPORTANT: wait for modal to close (prevents silent failure)
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//div[@role='dialog' or contains(@class,'Modal') or contains(@class,'Dialog')]")
                ));
                System.out.println("✅ Submit button is clicked");
                Allure.step("Submit button is clicked");

                SceenshotUtil.takeScreenshot(driver, "Submit button is clicked");
            });

            // ================================
            // 4. VALIDATE DESCRIPTION (READ ONLY)
            // ================================
            Allure.step("Validate Description", () -> {

                // ✅ Wait for element to be visible using YOUR locator
                WebElement descValue = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(DescriptionReadOnly)
                );

                // ✅ Get stable text
                String description = descValue.getText().trim();

                System.out.println("Description Value = " + description);
                System.out.println("RAW TEXT = [" + description + "]");
                //System.out.println("TAG NAME = " + descValue.getTagName());

                // 🔥 HARD ASSERT (this is what you were missing)
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

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();",
                                cancelBtn);

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

                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].scrollIntoView({block:'center'});",
                                    justificationField);

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
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                            justificationField);

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                            justificationField);

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

                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].scrollIntoView({block:'center'});",
                                    modalCancelBtn);

                    Thread.sleep(2000);

                    wait.until(ExpectedConditions.elementToBeClickable(
                            modalCancelBtn));

                    // NORMAL CLICK FIRST
                    try {
                        modalCancelBtn.click();
                    } catch (Exception e) {

                        // JS FALLBACK
                        ((JavascriptExecutor) driver)
                                .executeScript("arguments[0].click();",
                                        modalCancelBtn);
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
                    wait.until(ExpectedConditions.invisibilityOf(
                            modalCancelBtn));

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
                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].scrollIntoView({block:'center'});",
                                    cancelledStatus);

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