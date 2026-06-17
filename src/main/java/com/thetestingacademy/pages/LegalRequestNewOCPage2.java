package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.utils.DynamicDataUtil;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
public class LegalRequestNewOCPage2 extends CommonUIActions {

    private final DataModel testData;

    public LegalRequestNewOCPage2(WebDriver driver, DataModel testData) {
        super(driver);
        this.testData = testData;
    }

    // ================= LOCATORS =================

    private final By requestMatter =
            By.xpath("//label[contains(.,'Request/Matter Name')]/following::input[1]");

    private final By ocFirmDropdown =
            By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., '---Select Outside Counsel Firm Name---')]");

    private final By ocFirmNameInput =
            By.xpath("//strong[contains(text(),'OC Firm Name')]/following::input[1]");

    private final By justificationInput =
            By.xpath("//strong[contains(text(),'New Outside Counsel Justification')]/following::textarea[1]");

    private final By attorneyInput =
            By.xpath("//strong[contains(text(),'Attorney Name')]/following::input[1]");

    private final By phoneInput =
            By.xpath("//strong[contains(text(),'Phone')]/following::input[1]");

    private final By emailInput =
            By.xpath("//strong[contains(text(),'Email')]/following::input[1]");

    private final By cityInput =
            By.xpath("//strong[contains(text(),'Address')]/following::input[1]");

    private final By stateDropdown =
            By.xpath("(//strong[normalize-space()='Address']/following::div[contains(@class,'DropdownWidget---dropdown_value')])[1]");

    private final By submitBtn =
            By.xpath("//button[.//span[text()='Submit']]");

    private final By dropdownOptions =
            By.xpath("//div[@role='option']");

    // ================= DATA =================

    int count = DynamicDataUtil.getSharedCounter();

    //String requestMatterNew;
    String ocFirmName;
    String selectedOCFirmNew;
    String ocJustification;
    String attorneyName;
    String phoneNumber;
    String emailAddress;
    String city;
    String state;

    // ================= SAFE DROPDOWN HANDLER =================

    private void selectOption(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Dropdown value is NULL/EMPTY");
        }

        value = value.trim();

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(15));

        w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));

        for (WebElement opt : driver.findElements(dropdownOptions)) {

            String text = opt.getText() == null ? "" : opt.getText().trim();

            if (text.equalsIgnoreCase(value)) {
                w.until(ExpectedConditions.elementToBeClickable(opt)).click();
                return;
            }
        }

        throw new RuntimeException("Option not found in dropdown: " + value);
    }


    // ================= MAIN FLOW =================

    public void fillNewOCRequest() {

        // STEP 1: Request Matter
        Allure.step("Entering Request Matter", () -> {

            String requestMatterEx = ConfigReader.getData("exOC.requestmatter");
            String requestMatterNew = ConfigReader.getData("newOC.requestmatter");

            scrollToElement(requestMatter);

            type(requestMatter, requestMatterNew);

            System.out.println("Request Matter entered: " + requestMatterNew);
            Allure.step("Request Matter entered: " + requestMatterNew);
        });

        // STEP 2: Select OC Firm
        Allure.step("Select Outside Counsel Firm", () -> {

            selectedOCFirmNew = testData.getOutsideCounselFirm();

            click(ocFirmDropdown);
            selectFromDynamicList(selectedOCFirmNew);

            System.out.println("Outside Counsel Firm selected: " + selectedOCFirmNew);
            Allure.step("Outside Counsel Firm selected: " + selectedOCFirmNew);
        });


        // STEP 2.5 Confirm Outside Counsel selection applied"
        Allure.step("Confirm Outside Counsel selection applied", () -> {

            By selectedValue = By.xpath(
                    "//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., 'Request New')]"
            );

            scrollToElement(selectedValue);

            WebElement dropdown1 = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(selectedValue)
            );

            click(dropdown1);

            jsBlur();
        });

        // STEP 3: OC Firm Name
        Allure.step("Enter OC Firm Name", () -> {

            ocFirmName = DynamicDataUtil.getOCFirmName(
                    ConfigReader.getNewOC("ocFirmName"), count);

            /* By ocFirmLocator = By.xpath(
                    "//strong[contains(text(),'OC Firm Name')]/following::input[1]"
            );

            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocFirmLocator)
            ); */
            WebElement field = driver.findElement(ocFirmNameInput);

            waitForClickable(ocFirmNameInput);

            scrollToElement(ocFirmNameInput);

            click(field);

            type(ocFirmNameInput, ocFirmName);

            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocFirmNameInput).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!ocFirmName.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Firm Name mismatch. Expected: " + ocFirmName + " | Got: " + actual
                );
            }

            System.out.println("OC Firm Name entered: " + actual);
            Allure.step("OC Firm Name entered successfully: " + actual);
        });

        // STEP 4: Justification
        Allure.step("Entering Justification", () -> {

            ocJustification = DynamicDataUtil.getOCJustification(
                    ConfigReader.getNewOC("justificationText"), count);

            /* By ocJusLocator = By.xpath(
                    "//strong[contains(text(),'New Outside Counsel Justification')]/following::textarea[1]"
            );

            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocJusLocator)
            ); */

            waitForClickable(justificationInput);

            WebElement field = driver.findElement(justificationInput);

            scrollToElement(justificationInput);

            click(field);

            type(justificationInput, ocJustification);

            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(justificationInput).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!ocJustification.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Justification mismatch. Expected: "
                                + ocJustification + " | Got: " + actual
                );
            }

            System.out.println("Justification entered: " + actual);
            Allure.step("Justification entered: " + actual);
        });

            // STEP 5: Attorney Name
        Allure.step("Entering Attorney Name", () -> {

            attorneyName = DynamicDataUtil.getOCAttorney(
                    ConfigReader.getNewOC("ocAttorney"), count);

            /*By ocAttLocator = By.xpath(
                    "//strong[contains(text(),'Attorney Name')]/following::input[1]"

            ); */

            type(attorneyInput, attorneyName);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(attorneyInput).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!attorneyName.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Attorney mismatch. Expected: "
                                + attorneyName + " | Got: " + actual
                );
            }

            System.out.println("Attorney Name entered: " + actual);
            Allure.step("Attorney Name entered: " + actual);
        });


        // STEP 6: Phone
        Allure.step("Entering Phone", () -> {

            phoneNumber = ConfigReader.getNewOC("ocPhone");

            /*By ocPhLocator = By.xpath(
                    "//strong[contains(text(),'Phone')]/following::input[1]"
            ); */

            type(phoneInput, phoneNumber);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(phoneInput).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!phoneNumber.equals(actual)) {
                throw new RuntimeException(
                        "❌ Phone mismatch. Expected: "
                                + phoneNumber + " | Got: " + actual
                );
            }

            System.out.println("Phone entered: " + actual);
            Allure.step("Phone entered: " + actual);
        });

        // STEP 7: Validate Phone
        Allure.step("Validate Phone", () -> {

            try {
                WebElement phoneField = driver.switchTo().activeElement();
                String value = phoneField.getAttribute("value").replaceAll("[^0-9]", "");

                Thread.sleep(2500);

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

            emailAddress = DynamicDataUtil.getOCEmail(
                    ConfigReader.getNewOC("ocEmail"), count);

            /*By ocEmLocator = By.xpath(
                    "//strong[contains(text(),'Email')]/following::input[1]"
            ); */

            type(emailInput, emailAddress);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(emailInput).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!emailAddress.equals(actual)) {
                throw new RuntimeException(
                        "❌ Email mismatch. Expected: "
                                + emailAddress + " | Got: " + actual
                );
            }

            System.out.println("Email entered: " + actual);
            Allure.step("Email entered: " + actual);
        });

        // STEP 9: Validate Email
        Allure.step("Validate Email", () -> {

            String value = driver.switchTo()
                    .activeElement()
                    .getAttribute("value");

            Assert.assertTrue(
                    value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
            );
        });

        // STEP 10: City
        Allure.step("Entering City", () -> {

            city = DynamicDataUtil.getOCCityRandom();

            /*By ocCTLocator = By.xpath(
                    "//strong[contains(text(),'Address')]/following::input[1]"
            ); */

            type(cityInput, city);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(cityInput).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!city.equals(actual)) {
                throw new RuntimeException(
                        "❌ City mismatch. Expected: "
                                + city + " | Got: " + actual
                );
            }

            System.out.println("City entered: " + actual);
            Allure.step("City entered: " + actual);
        });

        // STEP 11: State
        Allure.step("Selecting State", () -> {

            state = testData.getState();

            click(stateDropdown);

            //selectOption(state);
            selectFromDynamicList(state);

            System.out.println("State selected: " + state);
            Allure.step("State selected: " + state);
        });

        // STEP 12: Submit
        Allure.step("Click Submit button to create the Legal Request", () -> {

            click(submitBtn);

            System.out.println("Submit button clicked");
            Allure.step("Submit button clicked successfully.");
        });

        // STEP 13: Capture Request Number (Post Submit Confirmation Screen)
        Allure.step("Capture NEWOC Request Number from confirmation screen", () -> {

            /*String newOCRequestNumber =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//span[contains(text(),'Legal Matter Request Initiated')]/following::span//strong")
                    )).getText().trim(); */

            By requestNumberLocator = By.xpath("//span[contains(text(),'Legal Matter Request Initiated')]/following::span//strong");

            waitForVisible(requestNumberLocator);

            String newOCRequestNumber = driver.findElement(requestNumberLocator).getText().trim();

            TestData.newOCRequestNumber = newOCRequestNumber;
            System.out.println("NEW OC Request Number : " + newOCRequestNumber);

            Allure.addAttachment("NEW OC Request Number", newOCRequestNumber);
        });

        // STEP 14: Completion
        Allure.step("New OC Request Completed Successfully");
        SceenshotUtil.takeScreenshot(driver, "New OC Request Completed Successfully");
    }
}