package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LegalRequestExistingOCPage extends CommonUIActions {

    private final DataModel testData;

    public LegalRequestExistingOCPage(WebDriver driver, DataModel testData) {
        super(driver);
        this.testData = testData;
    }
    // =====================================================
    // LOCATORS
    // =====================================================

    private final By requestMatterTextbox =
            By.xpath("//label[contains(.,'Request/Matter Name')]/following::input[1]");

    private final By ocFirmDropdown =
            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., '---Select Outside Counsel Firm Name---')]");

    private final By attorneyDropdown =
            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., '---Select Contact Attorney---')]");

    private final By ocConflictedDropdown =
            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value')][.//span[text()='---Select Yes/No---']]");

    private final By submitButton =
            By.xpath("//button[.//span[text()='Submit']]");

    // =====================================================
    // STORED VALUES
    // =====================================================

    //protected String requestMatterEx;
    protected String selectedOCFirm;
    protected String selectedAttorney;
    protected String isOCConflicted;

    // =====================================================
    // MAIN METHOD
    // =====================================================

    public void fillExistingOCRequest() {

        // ==========================================
        // STEP 1: Request Matter
        // ==========================================

        Allure.step("Entering Request Matter", () -> {

            String requestMatterEx = ConfigReader.getData("exOC.requestmatter");

            scrollToElement(requestMatterTextbox);

            type(requestMatterTextbox, requestMatterEx);

            System.out.println("Request Matter entered: " + requestMatterEx);

            Allure.step("Request Matter entered: " + requestMatterEx);
        });

        // ==========================================
        // STEP 2: Outside Counsel Firm
        // ==========================================

        Allure.step("Select Outside Counsel Firm", () -> {

            selectedOCFirm = testData.getOutsideCounselFirm();

            click(ocFirmDropdown);

            selectFromDynamicList(selectedOCFirm);

            System.out.println("Outside Counsel Firm selected: " + selectedOCFirm);
            Allure.step("Outside Counsel Firm selected: " + selectedOCFirm);
        });

        // ==========================================
        // STEP 3: Validate OC Firm
        // ==========================================

        Allure.step("Validate OC Firm", () -> {

            try {

                /*WebElement firmValue = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//strong[normalize-space()='OC Firm Name']/following::em[contains(@class,'EmphasisText')][1]")
                        )
                ); */
                By firmValue = By.xpath(
                        "//strong[normalize-space()='OC Firm Name']/following::em[contains(@class,'EmphasisText')][1]"
                );

                waitForVisible(firmValue);

                //String actualFirm = firmValue.getText().trim();
                String actualFirm = driver.findElement(firmValue).getText().trim();

                Assert.assertEquals(
                        actualFirm,
                        selectedOCFirm,
                        "OC Firm mismatch"
                );

                SceenshotUtil.takeScreenshot(driver, "OC Firm Validated");

            } catch (Exception e) {

                SceenshotUtil.takeScreenshot(driver, "OC Firm Validation Failed");
                throw new RuntimeException(e);
            }
        });

        // ==========================================
        // STEP 4: Select Attorney
        // ==========================================
        Allure.step("Select Contact Attorney", () -> {

            selectedAttorney = testData.getContactAttorney();

            click(attorneyDropdown);
            selectFromDynamicList(selectedAttorney);

            System.out.println("Attorney selected: " + selectedAttorney);
            Allure.step("Contact Attorney selected: " + selectedAttorney);
        });

        // ==========================================
        // STEP 5: Validate Attorney
        // ==========================================

        Allure.step("Validate Attorney", () -> {

            try {

                /*WebElement attorneyValue = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//em[normalize-space()='" + selectedAttorney + "']")
                        )
                ); */

                By attorneyValue = By.xpath("//em[normalize-space()='" + selectedAttorney + "']");

                waitForVisible(attorneyValue);

                //String actualAttorney = attorneyValue.getText().trim();
                String actualAttorney = driver.findElement(attorneyValue).getText().trim();

                Assert.assertEquals(
                        actualAttorney,
                        selectedAttorney,
                        "Attorney mismatch"
                );

                SceenshotUtil.takeScreenshot(driver, "Attorney Validated");

            } catch (Exception e) {

                SceenshotUtil.takeScreenshot(driver, "Attorney Validation Failed");
                throw new RuntimeException(e);
            }
        });

        // ==========================================
        // STEP 6: Is OC Conflicted
        // ==========================================

        Allure.step("Select Is OC Conflicted", () -> {

            isOCConflicted = testData.getIsOcConflicted();

            click(ocConflictedDropdown);

            /*WebElement option = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[normalize-space()='" + isOCConflicted + "']")
                    )
            ); */
            By option = By.xpath("//*[normalize-space()='" + isOCConflicted + "']");

            waitForVisible(option);

            click(option);

            System.out.println("Is OC Conflicted: " + isOCConflicted);

            Allure.step("'Is OC Conflicted?' selected value: " + isOCConflicted);
        });

        // ==========================================
        // STEP 7: Submit
        // ==========================================

        Allure.step("Submit Legal Request", () -> {

            click(submitButton);

            System.out.println("Submit button clicked");
            Allure.step("Submit button clicked successfully.");
        });

        // ==========================================
        // STEP 8: Capture Request Number
        // ==========================================

        Allure.step("Capture EXOC Request Number from confirmation screen", () -> {

            /*String requestNumber = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//span[contains(text(),'Legal Matter Request Initiated')]/following::span//strong")
                    )
            ).getText().trim(); */

            By requestNumberLocator = By.xpath("//span[contains(text(),'Legal Matter Request Initiated')]/following::span//strong");

            waitForVisible(requestNumberLocator);

            String requestNumber = driver.findElement(requestNumberLocator).getText().trim();

            TestData.exOCRequestNumber = requestNumber;

            System.out.println("EX OC Request Number : " + requestNumber);

            Allure.addAttachment("EX OC Request Number", requestNumber);
        });

        // ==========================================
        // FINAL STEP
        // ==========================================

        Allure.step("Existing OC Request Completed Successfully");

        SceenshotUtil.takeScreenshot(driver, "Existing OC Request Completed Successfully");
    }
}