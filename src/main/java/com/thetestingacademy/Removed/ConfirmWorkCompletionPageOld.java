/*package com.thetestingacademy.pages;

import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ConfirmWorkCompletionPageOld {

    private WebDriver driver;
    private WebDriverWait wait;

    public ConfirmWorkCompletionPageOld(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // =========================================================
    // GRID LOCATORS (TASK LIST)
    // =========================================================

    private By taskRow = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]"
    );

    private By checkbox = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]//input[@type='checkbox']/following-sibling::label"
    );

    private By taskLink = By.xpath(
            "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]//td//a[normalize-space()='Confirm Work Completion']"
    );

    private By claimButton = By.xpath("//button[.//span[text()='Claim']]");

    // =========================================================
    // TASK PAGE LOCATORS (UI)
    // =========================================================

    private By requestCloseStatusDropdown = By.xpath(
            "//span[contains(@class,'DropdownWidget---value_display') and contains(text(),'Request Close Status')]"
    );

    private By submitButton = By.xpath("//button[.//span[text()='Submit']]");

    // =========================================================
    // SINGLE FLOW - TASK 1
    // =========================================================
    public void openAndEnterConfirmWorkCompletionTask() {

        Allure.step("TASK 1 - Confirm Work Completion: Full navigation flow", () -> {

            // =====================================================
            // STEP 0: WAIT FOR TASK GRID
            // =====================================================
            By claimVisible = By.xpath("//button[.//span[text()='Claim']]");
            By tableVisible = By.xpath("//table//tbody//tr");

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(claimVisible),
                    ExpectedConditions.visibilityOfElementLocated(tableVisible)
            ));

            System.out.println("✅ Task grid loaded");
            SceenshotUtil.takeScreenshot(driver, "Task Grid Loaded");

            // =====================================================
            // STEP 1: FIND TASK (retry + refresh)
            // =====================================================
            By task = By.xpath(
                    "//table//tbody//tr[.//a[normalize-space()='Confirm Work Completion']]"
            );

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

            // =====================================================
            // STEP 2: SELECT CHECKBOX
            // =====================================================
            WebElement cb = wait.until(
                    ExpectedConditions.elementToBeClickable(checkbox)
            );

            cb.click();

            System.out.println("✅ Checkbox selected");
            SceenshotUtil.takeScreenshot(driver, "Checkbox Selected");

            // =====================================================
            // STEP 3: CLAIM TASK
            // =====================================================
            WebElement claimBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(claimButton)
            );

            claimBtn.click();

            System.out.println("✅ Task claimed");
            SceenshotUtil.takeScreenshot(driver, "Task Claimed");

            wait.until(ExpectedConditions.stalenessOf(claimBtn));

            // =====================================================
            // STEP 4: OPEN TASK
            // =====================================================
            WebElement link = wait.until(
                    ExpectedConditions.elementToBeClickable(taskLink)
            );

            link.click();

            System.out.println("✅ Task opened");
            SceenshotUtil.takeScreenshot(driver, "Task Opened - Confirm Work Completion");
        });
    }
    // =========================================================
    // STEP 6 - Request Close Status
    // =========================================================
    public void handleRequestCloseStatusDropdown() {

        Allure.step("Task 1 - Confirm Work Completion: Open and validate Request Close Status dropdown", () -> {

            // =========================================================
            // STEP: OPEN DROPDOWN
            // =========================================================
            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(requestCloseStatusDropdown)
            );

            dropdown.click();

            System.out.println("✅ Request Close Status dropdown opened");

            SceenshotUtil.takeScreenshot(driver,
                    "Request Close Status Dropdown Opened");

            // =========================================================
            // STEP: VALIDATION (basic UI check after open)
            // =========================================================
            boolean isOpened = wait.until(driver ->
                    dropdown.getAttribute("class") != null
            );

            if (isOpened) {
                System.out.println("✅ Dropdown interaction validated");
                SceenshotUtil.takeScreenshot(driver,
                        "Dropdown Validation Passed");
            } else {
                System.out.println("❌ Dropdown validation failed");
                SceenshotUtil.takeScreenshot(driver,
                        "Dropdown Validation Failed");
            }
        });
        Allure.step("Handle Date of Completion (select current date only)", () -> {

            // Locate date input (enabled only after 'Completed')
            By dateInput = By.xpath(
                    "//input[@data-testid='DatePickerWidget-textInput' and @placeholder='mm/dd/yyyy']"
            );

            WebElement dateField = wait.until(
                    ExpectedConditions.elementToBeClickable(dateInput)
            );

            // Click to activate calendar
            dateField.click();

            System.out.println("✅ Date picker opened");

            SceenshotUtil.takeScreenshot(driver, "Date Picker Opened");

            // Open calendar button (optional safety click)
            By calendarBtn = By.xpath(
                    "//button[@data-testid='DatePickerWidget-calendarButton']"
            );

            driver.findElement(calendarBtn).click();

            // Get today's date dynamically
            String today = java.time.LocalDate.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("d"));

            // Select current day in calendar
            By todayDate = By.xpath(
                    "//button[not(@disabled) and normalize-space()='" + today + "']"
            );

            WebElement todayElement = wait.until(
                    ExpectedConditions.elementToBeClickable(todayDate)
            );

            todayElement.click();

            System.out.println("✅ Selected today's date: " + today);

            SceenshotUtil.takeScreenshot(driver, "Date of Completion Selected");
        });

    }
}

        // =========================================================
        // COMBINED FLOW (STEP 1–6)
        // =========================================================
        @Step("Select, claim and open task")
        public void selectClaimAndOpenTask() {

            waitForTaskGrid();
            findTask();
            selectCheckbox();
            //clickClaim();
            openTask();
        }



        // =========================================================
        // STEP 7 - (Placeholder for fields - to be added later)
        // =========================================================
        // You will add field locators here once HTML is shared

        // =========================================================
        // STEP 8 - OPEN DROPDOWN
        // =========================================================
        @Step("Open 'Request Close Status' dropdown")
        public void openRequestCloseStatusDropdown() {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(requestCloseStatusDropdown)
            );

            dropdown.click();
        }

        // =========================================================
        // OPTIONAL - VALIDATE PAGE LOADED
        // =========================================================
        @Step("Validate Confirm Work Completion page loaded")
        public boolean isPageLoaded() {

            return wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).isDisplayed();
        }
} */