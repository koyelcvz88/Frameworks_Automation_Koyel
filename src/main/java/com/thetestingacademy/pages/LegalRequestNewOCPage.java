package com.thetestingacademy.pages;

import io.qameta.allure.Allure;
import com.thetestingacademy.utils.SceenshotUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LegalRequestNewOCPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    public LegalRequestNewOCPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // Stored values
    protected String requestMatterNew;
    protected String selectedOCFirmNew;
    protected String ocFirmName;
    protected String ocJustification;
    protected String attorneyName;
    protected String phoneNumber;
    protected String emailAddress;
    protected String city;
    protected String state;

    public void fillNewOCRequest() {

        // STEP 1: Request Matter
        Allure.step("Entering Request Matter", () -> {

            WebElement field = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//label[contains(text(),'Request/Matter Name')]/following::input[1]")
                    )
            );

            Thread.sleep(1000);
            field.click();

            requestMatterNew = "New_OC_New_Legal_Request";
            field.sendKeys(requestMatterNew);

            System.out.println("Request Matter entered: " + requestMatterNew);
            Allure.step("Request Matter entered: " + requestMatterNew);
        });

        // STEP 2: Select OC Firm
        Allure.step("Selecting Outside Counsel Firm", () -> {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., '---Select Outside Counsel Firm Name---')]")
                    )
            );

            Thread.sleep(1000);
            dropdown.click();
            Thread.sleep(1000);

            wait.until(ExpectedConditions.attributeContains(dropdown, "aria-expanded", "true"));
            dropdown.sendKeys(Keys.END, Keys.ENTER);

            selectedOCFirmNew = "Request New";

            System.out.println("Outside Counsel Firm selected: " + selectedOCFirmNew);
            Allure.step("Outside Counsel Firm selected: " + selectedOCFirmNew);
        });

        // STEP 3: OC Firm Name
        Allure.step("Entering OC Firm Name", () -> {

            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//strong[normalize-space()='OC Firm Name']")
                    )
            );

            Thread.sleep(1000);
            actions.click(field)
                    .sendKeys(Keys.TAB)
                    .sendKeys("Auto New OC Firm")
                    .perform();

            ocFirmName = "Auto New OC Firm";

            System.out.println("OC Firm Name entered: " + ocFirmName);
            Allure.step("OC Firm Name entered: " + ocFirmName);
        });

        // STEP 4: Justification
        Allure.step("Entering OC Justification", () -> {

            WebElement field = driver.findElement(
                    By.xpath("//strong[normalize-space()='New Outside Counsel Justification']")
            );

            Thread.sleep(1000);

            actions.click(field)
                    .sendKeys(Keys.TAB)
                    .sendKeys("Outside counsel engaged for strategic legal support.")
                    .perform();

            ocJustification = "Outside counsel engaged for strategic legal support.";

            System.out.println("Justification entered: " + ocJustification);
            Allure.step("Justification entered: " + ocJustification);
        });

        // STEP 5: Attorney Name
        Allure.step("Entering Attorney Name", () -> {

            WebElement field = driver.findElement(
                    By.xpath("//strong[normalize-space()='Attorney Name']")
            );

            Thread.sleep(1000);

            actions.click(field)
                    .sendKeys(Keys.TAB)
                    .sendKeys("Auto Attorney")
                    .perform();

            attorneyName = "Auto Attorney";

            System.out.println("Attorney Name entered: " + attorneyName);
            Allure.step("Attorney Name entered: " + attorneyName);
        });

        // STEP 6: Phone
        Allure.step("Entering Phone", () -> {

            WebElement field = driver.findElement(
                    By.xpath("//strong[normalize-space()='Phone']")
            );

            Thread.sleep(1000);

            actions.click(field)
                    .sendKeys(Keys.TAB)
                    .sendKeys("1234567890")
                    .perform();

            phoneNumber = "1234567890";

            System.out.println("Phone entered: " + phoneNumber);
            Allure.step("Phone entered: " + phoneNumber);
        });

        // STEP 7: Validate Phone
        Allure.step("Validate Phone", () -> {

            try {
                WebElement phoneField = driver.switchTo().activeElement();
                String value = phoneField.getAttribute("value").replaceAll("[^0-9]", "");

                Thread.sleep(1500);

                Assert.assertTrue(value.matches("\\d{10}"));

                System.out.println("Phone validated successfully: " + value);
                Allure.step("Validation PASSED: Phone is valid");
                SceenshotUtil.takeScreenshot(driver, "Phone Validated");

            } catch (Exception e) {
                SceenshotUtil.takeScreenshot(driver, "Phone Validation Failed");
                throw new RuntimeException(e);
            }
        });

        // STEP 8: Email
        Allure.step("Entering Email", () -> {

            WebElement field = driver.findElement(
                    By.xpath("//strong[normalize-space()='Email']")
            );

            Thread.sleep(1000);

            actions.click(field)
                    .sendKeys(Keys.TAB)
                    .sendKeys("autouser@mail.com")
                    .perform();

            emailAddress = "autouser@mail.com";

            System.out.println("Email entered: " + emailAddress);
            Allure.step("Email entered: " + emailAddress);
        });

        // STEP 9: Validate Email
        Allure.step("Validate Email", () -> {

            try {
                WebElement emailField = driver.switchTo().activeElement();
                String value = emailField.getAttribute("value");

                Thread.sleep(1500);

                Assert.assertTrue(value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));

                System.out.println("Email validated successfully: " + value);
                Allure.step("Validation PASSED: Email is valid");
                SceenshotUtil.takeScreenshot(driver, "Email Validated");

            } catch (Exception e) {
                SceenshotUtil.takeScreenshot(driver, "Email Validation Failed");
                throw new RuntimeException(e);
            }
        });

        // STEP 10: City
        Allure.step("Entering City", () -> {

            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("(//strong[normalize-space()='Address']/following::input)[1]")
                    )
            );

            Thread.sleep(1000);

            field.clear();
            field.sendKeys("Sitka");

            city = "Sitka";

            System.out.println("City entered: " + city);
            Allure.step("City entered: " + city);
        });

        // STEP 11: State
        Allure.step("Selecting State", () -> {

            WebElement dropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("(//strong[normalize-space()='Address']/following::div[contains(@class,'DropdownWidget---dropdown_value')])[1]")
                    )
            );

            Thread.sleep(1000);

            dropdown.click();
            dropdown.sendKeys("AL");
            dropdown.sendKeys(Keys.ENTER);

            state = "AL";

            System.out.println("State selected: " + state);
            Allure.step("State selected: " + state);
        });

        // STEP 12: Submit
        Allure.step("Click Submit button to create the Legal Request", () -> {

            WebElement submitBtn = driver.findElement(
                    By.xpath("//button[.//span[text()='Submit']]")
            );

            submitBtn.click();

            Thread.sleep(2000);

            System.out.println("Submit button clicked successfully.");
            Allure.step("Submit button clicked successfully.");
        });

        Allure.step("New OC Request Completed Successfully");
        SceenshotUtil.takeScreenshot(driver, "New OC Request Completed Successfully");
    }
}