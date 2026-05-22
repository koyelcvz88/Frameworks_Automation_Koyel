package com.thetestingacademy.pages;

import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import com.thetestingacademy.utils.SceenshotUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LegalRequestExistingOCPage {


    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    public LegalRequestExistingOCPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // Stored values for validation
    protected String requestMatterEx;
    public String selectedOCFirm;
    public String selectedAttorney;
    protected String isOCConflicted;


    public void fillExistingOCRequest() {

        // STEP 1: Request Matter
        Allure.step("Entering Request Matter", () -> {

            /*WebElement requestMatterExTxtBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//label[contains(text(),'Request/Matter Name')]/following::input[1]")
                    )
            ); */
            By inputLocator = By.xpath("//label[contains(.,'Request/Matter Name')]/following::input[1]");

            WebElement requestMatterExTxtBox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(inputLocator)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", requestMatterExTxtBox
            );

            wait.until(ExpectedConditions.elementToBeClickable(requestMatterExTxtBox));

            requestMatterExTxtBox.click();

            requestMatterEx = "Ex_OC_New_Legal_Request";
            requestMatterExTxtBox.sendKeys(requestMatterEx);

            System.out.println("Request Matter entered: " + requestMatterEx);
            Allure.step("Request Matter entered: " + requestMatterEx);
        });

        // STEP 2: Select OC Firm
        Allure.step("Selecting Outside Counsel Firm", () -> {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., '---Select Outside Counsel Firm Name---')]")
                    )
            );

            dropdown.click();

            wait.until(ExpectedConditions.attributeContains(dropdown, "aria-expanded", "true"));
            dropdown.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

            selectedOCFirm = "Alston & Bird";

            System.out.println("Outside Counsel Firm selected: " + selectedOCFirm);
            Allure.step("Outside Counsel Firm selected: " + selectedOCFirm);
        });

        // STEP 3: Validate OC Firm
        Allure.step("Validate OC Firm Name", () -> {

            try {
                WebElement firmValue = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//strong[normalize-space()='OC Firm Name']/following::em[contains(@class,'EmphasisText')][1]")
                        )
                );

                String actualFirm = firmValue.getText().trim();

                Assert.assertEquals(actualFirm, selectedOCFirm,
                        "OC Firm mismatch. Expected: " + selectedOCFirm + " but found: " + actualFirm);

                Allure.step("Validation PASSED: OC Firm matches");
                SceenshotUtil.takeScreenshot(driver, "OC Firm Validated");

            } catch (Exception e) {
                SceenshotUtil.takeScreenshot(driver, "OC Firm Validation Failed");
                throw new RuntimeException(e);
            }
        });

        // STEP 4: Select Attorney
        Allure.step("Selecting first Contact Attorney", () -> {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., '---Select Contact Attorney---')]")
                    )
            );

            dropdown.click();

            wait.until(ExpectedConditions.attributeContains(dropdown, "aria-expanded", "true"));
            dropdown.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

            selectedAttorney = "Elizabeth Murphy";

            System.out.println("Contact Attorney selected: " + selectedAttorney);
            Allure.step("Contact Attorney selected: " + selectedAttorney);
        });

        // STEP 5: Validate Attorney
        Allure.step("Validate Attorney", () -> {

            try {
                WebElement attorneyValue = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//em[normalize-space()='" + selectedAttorney + "']")
                        )
                );

                String actualAttorney = attorneyValue.getText().trim();

                Assert.assertEquals(actualAttorney, selectedAttorney,
                        "Attorney mismatch. Expected: " + selectedAttorney + " but found: " + actualAttorney);

                Allure.step("Validation PASSED: Attorney matches");
                SceenshotUtil.takeScreenshot(driver, "Attorney Validated");

            } catch (Exception e) {
                SceenshotUtil.takeScreenshot(driver, "Attorney Validation Failed");
                throw new RuntimeException(e);
            }
        });

        // STEP 6: OC Conflicted
        Allure.step("Selecting second option for 'Is OC Conflicted?'", () -> {

            WebElement dropdown = driver.findElement(
                    By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value')][.//span[text()='---Select Yes/No---']]")
            );

            dropdown.click();

            actions.sendKeys(Keys.ARROW_DOWN).perform();
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            actions.sendKeys(Keys.ENTER).perform();

            isOCConflicted = dropdown.findElement(By.tagName("span")).getText();

            System.out.println("'Is OC Conflicted?' selected value: " + isOCConflicted);
            Allure.step("'Is OC Conflicted?' selected value: " + isOCConflicted);
        });

        // STEP 7: Submit
        Allure.step("Click Submit button to create the Legal Request", () -> {

            WebElement submitBtn = driver.findElement(
                    By.xpath("//button[.//span[text()='Submit']]")
            );

            submitBtn.click();

            System.out.println("Submit button clicked successfully.");
            Allure.step("Submit button clicked successfully.");
        });

        // STEP 8: Capture Request Number (Post Submit Confirmation Screen)
        Allure.step("Capture EXOC Request Number from confirmation screen", () -> {

            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            String exOCRequestNumber = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//span[contains(text(),'Legal Matter Request Initiated')]/following::span//strong")
                    )
            ).getText().trim();

            TestData.exOCRequestNumber = exOCRequestNumber;

            System.out.println("EXOC Request Number: " + exOCRequestNumber);

            Allure.step("EXOC Request Number captured: " + exOCRequestNumber);
            Allure.addAttachment("EXOC Request Number", exOCRequestNumber);
            //return exOCRequestNumber;

            // store if needed for later steps
            //this.exOCRequestNumber = exOCRequestNumber;
        });

        Allure.step("Existing OC Request Completed Successfully");
        SceenshotUtil.takeScreenshot(driver, "Existing OC Request Completed Successfully");
    }
}