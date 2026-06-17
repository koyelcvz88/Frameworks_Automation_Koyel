package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.utils.SceenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class LegalRequestFormPage extends CommonUIActions {

    private final DataModel testData;

    public LegalRequestFormPage(WebDriver driver, DataModel testData) {
        super(driver);
        this.testData = testData;
        System.out.println("===== PAGE RECEIVED DATA =====");
        System.out.println(testData);
    }

    // =====================================================
    // LOCATORS
    // =====================================================

    private final By requestTypeDropdown =
            By.xpath("//div[@role='combobox'][.//span[normalize-space()='---Select Request Type---']]");

    private final By requestSubTypeDropdown =
            By.xpath("//div[@role='combobox'][.//span[contains(text(),'Select Request Sub Type')]]");

    private final By internalCounselDropdown =
            By.xpath("//div[@role='combobox'][.//span[contains(text(),'Search Internal Counsel')]]");

    private final By requestSegmentDropdown =
            By.xpath("//div[@role='combobox'][.//span[contains(text(),'Select Request Segment')]]");

    private final By ocFeesPayerDropdown =
            By.xpath("//div[@role='combobox'][.//span[contains(text(),'---Select OC Fees Payer---')]]");

    private final By calendarButton =
            By.cssSelector("button.DatePickerWidget---calendar_btn");

    // =====================================================
    // STORED VALUES
    // =====================================================

    protected String requestType;
    protected String requestSubType;
    protected String internalCounsel;
    protected String requestSegment;
    protected String ocFeesPayer;
    protected String dueDate;

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

    // =====================================================
    // FORM FILL
    // =====================================================

    public void enterFormFields() {

        System.out.println("RequestType      = " + testData.getRequestType());
        System.out.println("RequestSubType   = " + testData.getRequestSubType());
        System.out.println("InternalCounsel  = " + testData.getInternalCounsel());
        System.out.println("RequestSegment   = " + testData.getRequestSegment());
        System.out.println("OcFeesPayer      = " + testData.getOcFeesPayer());

        // =================================================
        // Request Type
        // =================================================

        Allure.step("Select Request Type", () -> {

                    String value = safe(testData.getRequestType(), "RequestType");
                    if (value == null) return;

                    click(requestTypeDropdown);
                    selectFromDynamicList(value);

                    requestType = value;

            System.out.println("Request Type Selected: " + requestType);

            Allure.step("Request Type Selected: " + requestType);
        });

        // =================================================
        // Request Sub Type
        // =================================================
        Allure.step("Select Request Sub Type", () -> {

        String value = safe(testData.getRequestSubType(), "RequestSubType");
        if (value == null) return;

        click(requestSubTypeDropdown);
        selectFromDynamicList(value);

        requestSubType = value;

            System.out.println("Request SubType Selected: " + requestSubType);

            Allure.step("Request SubType Selected: " + requestSubType);
        });

        // =================================================
        // Internal Counsel
        // =================================================
        Allure.step("Select Internal Counsel", () -> {

            String value = safe(testData.getInternalCounsel(), "InternalCounsel");
            if (value == null) return;

            click(internalCounselDropdown);
            selectFromDynamicList(value);

            internalCounsel = value;

            System.out.println("Internal Counsel Selected: " + internalCounsel);

            Allure.step("Internal Counsel Selected: " + internalCounsel);
        });

        // =================================================
        // Request Segment
        // =================================================
        Allure.step("Select Request Segment", () -> {

            String value = safe(testData.getRequestSegment(), "RequestSegment");
            if (value == null) return;

            click(requestSegmentDropdown);
            selectFromDynamicList(value);

            requestSegment = value;

            System.out.println("Request Segment Selected: " + requestSegment);
            Allure.step("Request Segment Selected: " + requestSegment);
        });

        // =================================================
        // OC Fees Payer
        // =================================================
        Allure.step("Select OC Fees Payer", () -> {

            String value = safe(testData.getOcFeesPayer(), "OcFeesPayer");
            if (value == null) return;

            click(ocFeesPayerDropdown);
            selectFromDynamicList(value);

            ocFeesPayer = value;

            System.out.println("OC Fees Payer Selected: " + ocFeesPayer);
            Allure.step("OC Fees Payer Selected: " + ocFeesPayer);
        });
        // =================================================
        // Due Date
        // =================================================
        Allure.step("Selecting Due Date", () -> {

            // Open the calendar
            WebElement calendarBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.DatePickerWidget---calendar_btn")));
            click(calendarBtn);

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

    // =====================================================
    // MASTER METHOD
    // =====================================================

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

    // =====================================================
    // GETTERS FOR VALIDATION PAGES
    // =====================================================

    public String getRequestType() {
        return requestType;
    }

    public String getRequestSubType() {
        return requestSubType;
    }

    public String getInternalCounsel() {
        return internalCounsel;
    }

    public String getRequestSegment() {
        return requestSegment;
    }

    public String getOcFeesPayer() {
        return ocFeesPayer;
    }

    public String getDueDate() {
        return dueDate;
    }
}