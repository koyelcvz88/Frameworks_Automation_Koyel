package com.thetestingacademy.pagesHC;

import io.qameta.allure.Allure;
import com.thetestingacademy.utils.SceenshotUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class LegalRequestFormPage {


    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    public LegalRequestFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // Variables to store selected data for validation later
    protected String requestType;
    protected String requestSubType;
    protected String internalCounsel;
    protected String requestSegment;
    protected String ocFeesPayer;
    protected String dueDate;


    // ---------------- ACTION METHODS ----------------

    public void enterFormFields() {
        // Request Type
        Allure.step("Selecting Request Type from dropdown", () -> {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='combobox'][.//span[normalize-space()='---Select Request Type---']]")));
            dropdown.click();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
            requestType = "Litigation"; // Value selected

            // Print to console
            System.out.println("'" + requestType + "' option selected.");
            // Also log in Allure
            Allure.step("'" + requestType + "' option selected.");
        });

        // Request SubType
        Allure.step("Selecting Request SubType from dropdown", () -> {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='combobox'][.//span[contains(text(),'Select Request Sub Type')]]")));
            dropdown.click();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
            requestSubType = "CMS"; // Value selected

            // Print to console
            System.out.println("'" + requestSubType + "' option selected.");
            // Also log in Allure
            Allure.step("'" + requestSubType + "' option selected.");
        });

        // Internal Counsel
        Allure.step("Selecting Internal Counsel from dropdown", () -> {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='combobox'][.//span[contains(text(),'Search Internal Counsel')]]")));
            Thread.sleep(2000);
            dropdown.click();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
            internalCounsel = "Annie George"; // Value selected

            // Print to console
            System.out.println("'" + internalCounsel + "' option selected.");
            // Also log in Allure
            Allure.step("'" + internalCounsel + "' option selected.");
        });

        // Request Segment
        Allure.step("Selecting Request Segment from dropdown", () -> {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='combobox'][.//span[contains(text(),'Select Request Segment')]]")));
            dropdown.click();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
            requestSegment = "Assumptions"; // Value selected

            // Print to console
            System.out.println("'" + requestSegment + "' option selected.");
            // Also log in Allure
            Allure.step("'" + requestSegment + "' option selected.");
        });

        // OC Fees Payer
        Allure.step("Selecting OC Fees Payer from dropdown", () -> {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='combobox'][.//span[contains(text(),'---Select OC Fees Payer---')]]")));
            dropdown.click();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
            ocFeesPayer = "Trimont"; // Value selected

            // Print to console
            System.out.println("'" + ocFeesPayer + "' option selected.");
            // Also log in Allure
            Allure.step("'" + ocFeesPayer + "' option selected.");
        });

        /*Allure.step("Selecting Due Date", () -> {

            // Open the calendar
            WebElement calendarBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.DatePickerWidget---calendar_btn")));
            calendarBtn.click();

            // Get today's date
            LocalDate today = LocalDate.now();
            String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH); // e.g., "Saturday"
            int dayOfMonth = today.getDayOfMonth(); // e.g., 28
            String month = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH); // e.g., "March"
            int year = today.getYear(); // e.g., 2026

            // Construct the aria-label exactly as in your date button
            //String ariaLabel = String.format("Select %s, %s %dth %d", dayOfWeek, month, dayOfMonth, year);

            // Locate the date button dynamically
            /*WebElement todayDateBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@aria-label='" + ariaLabel + "']")));
            todayDateBtn.click(); */
            /*String day = String.valueOf(LocalDate.now().getDayOfMonth());

            WebElement todayDateBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[not(@disabled) and normalize-space()='" + day + "']")));

            todayDateBtn.click();

            dueDate = String.format("%d %s %d", dayOfMonth, month, year);

            // Print to console
            System.out.println("Date selected today: " + dueDate);
            // Also log in Allure
            Allure.step("Date selected today: " + dueDate);
        }); */
        Allure.step("Selecting Due Date", () -> {

            // Open the calendar
            WebElement calendarBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.DatePickerWidget---calendar_btn")));
            calendarBtn.click();

            // Get today's date
            LocalDate today = LocalDate.now();

            String dayOfWeek = today.getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            int dayOfMonth = today.getDayOfMonth();

            String month = today.getMonth()
                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            int year = today.getYear();

            // Handle st/nd/rd/th suffix
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

            // Build exact aria-label
            String ariaLabel = String.format(
                    "Select %s, %s %d%s %d",
                    dayOfWeek,
                    month,
                    dayOfMonth,
                    suffix,
                    year
            );

            System.out.println("Looking for date: " + ariaLabel);

            // Click exact matching date
            WebElement todayDateBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[@aria-label=\"" + ariaLabel + "\"]")
                    ));

            todayDateBtn.click();

            dueDate = String.format("%d %s %d", dayOfMonth, month, year);

            // Print to console
            System.out.println("Date selected today: " + dueDate);

            // Allure log
            Allure.step("Date selected today: " + dueDate);
        });
    }

    // ---------------- MASTER METHOD ----------------

    public void fillLegalRequestForm() {
        Allure.step("Filling Legal Request Form", () -> {
            SceenshotUtil.takeScreenshot(driver, "Form Start");

            // Call the enterFormFields method to fill all fields
            enterFormFields();

            SceenshotUtil.takeScreenshot(driver, "Form Completed");

            System.out.println("Legal Request Form Completed");
            Allure.step("Legal Request Form Completed");
            SceenshotUtil.takeScreenshot(driver, "Legal Request Form Completed");
        });
    }
}