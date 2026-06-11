package com.thetestingacademy.pagesHC;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Update_CancelRequestPage2 extends CommonUIActions {

    public Update_CancelRequestPage2(WebDriver driver) {
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

            // STEP 1: wait for button readiness
            waitForVisible(CancelRequestBtn);
            waitForClickable(CancelRequestBtn);

            WebElement cancelBtn = driver.findElement(CancelRequestBtn);

            // STEP 2: scroll into view (framework)
            scrollToElement(cancelBtn);

            // STEP 3: click using CommonUIActions (NO JS CLICK)
            click(cancelBtn);

            System.out.println("✅ Cancel Request button clicked");

            SceenshotUtil.takeScreenshot(driver, "Cancel_Request_Button_Clicked");

            // STEP 4: replace Thread.sleep with UI stability wait
            waitForPageLoad();
        });

            // =====================================================
            // CLICK MODAL CANCEL REQUEST
            // =====================================================

        Allure.step("Click Cancel Request button", () -> {

     List<WebElement> cancelButtons = wait.until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(CancelRequestModalBtn));

     // SECOND BUTTON = MODAL BUTTON
     WebElement modalCancelBtn = cancelButtons.get(1);

     ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
                     modalCancelBtn);

     Thread.sleep(2000);

     wait.until(ExpectedConditions.elementToBeClickable(modalCancelBtn));

     // NORMAL CLICK FIRST
      try {
      modalCancelBtn.click();
    } catch (Exception e) {

     // JS FALLBACK
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", modalCancelBtn);
     }

    System.out.println("✅ Clicked Cancel Request button");

    Thread.sleep(4000);

    // VALIDATE NO REQUIRED ERROR
    List<WebElement> validationErrors =
    driver.findElements(By.xpath(
            "//*[contains(text(),'A value is required')]"));

    if (!validationErrors.isEmpty()) {

        throw new RuntimeException("❌ Justification validation still displayed");
    }

    // VALIDATE MODAL CLOSED
     wait.until(ExpectedConditions.invisibilityOf(modalCancelBtn));
     System.out.println("✅ Cancel Request modal closed");

     SceenshotUtil.takeScreenshot(driver, "Cancel_Request_Completed");
  });
            // ===============================
            // VALIDATE STATUS = CANCELLED
            // ===============================

        Allure.step("Validate Request Status is Cancelled", () -> {

            WebElement cancelledStatus = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            CancelledStatus));

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            cancelledStatus);

            Thread.sleep(2000);

            String actualStatus =
                    cancelledStatus.getText().trim();

            System.out.println("Request Status = "
                    + actualStatus);

            if (!actualStatus.equalsIgnoreCase("Cancelled")) {

                throw new RuntimeException(
                        "❌ Request status NOT updated to Cancelled");
            }

            System.out.println("✅ Request status successfully updated to Cancelled");

            SceenshotUtil.takeScreenshot(driver,
                    "Request_Status_Cancelled");
        });
    }
}