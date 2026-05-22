package com.thetestingacademy.pages;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.utils.DynamicDataUtil;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

//import static com.thetestingacademy.utils.TestData.newOCRequestNumber;

public class LegalRequestNewOCPage2 {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    public LegalRequestNewOCPage2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // Stored values
    protected String requestMatterNew;
    protected String selectedOCFirmNew;
    int count = DynamicDataUtil.getSharedCounter();
    protected String ocFirmName;
    protected String ocJustification;
    protected String attorneyName;
    protected String phoneNumber;
    protected String emailAddress;
    protected String city;
    protected String state;

    public void fillNewOCRequest() throws InterruptedException {

        // STEP 1: Request Matter
        Allure.step("Entering Request Matter", () -> {

            /* WebElement field = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//label[contains(text(),'Request/Matter Name')]/following::input[1]")
                    )
            ); */

            By inputLocator = By.xpath("//label[contains(.,'Request/Matter Name')]/following::input[1]");

            WebElement field = wait.until(
                    ExpectedConditions.presenceOfElementLocated(inputLocator)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", field
            );

            wait.until(ExpectedConditions.elementToBeClickable(field));


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

        //"Step 2.5: Confirm Outside Counsel selection applied"
// STEP 2.5 FIXED: Force Appian UI refresh properly

        Thread.sleep(500);

        WebElement dropdown1 = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'DropdownWidget---dropdown_value') and contains(., 'Request New')]")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                dropdown1
        );

// 🔥 Click instead of dispatchEvent (IMPORTANT for Appian)
        try {
            dropdown1.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown1);
        }

// 🔥 Force blur to trigger Appian backend refresh
        ((JavascriptExecutor) driver).executeScript("document.activeElement.blur();");

        Thread.sleep(1500);

        System.out.println("Step 2.5: Outside Counsel selection confirmed successfully");
            //Allure.step("Appian change event triggered successfully");
        //});

        // STEP 3: OC Firm Name
        Allure.step("Enter OC Firm Name", () -> {

            ocFirmName = DynamicDataUtil.getOCFirmName(
                    ConfigReader.getNewOC("ocFirmName"), count);

            By ocFirmLocator = By.xpath(
                    "//strong[contains(text(),'OC Firm Name')]/following::input[1]"
//                    "//input[contains(@aria-label,'OC Firm Name') or contains(@placeholder,'OC Firm Name')]"
            );
            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocFirmLocator)
            );
            field.sendKeys(ocFirmName);
           /* WebElement field = null;

            // 🔥 Retry mechanism for Appian re-render issues
            for (int i = 0; i < 5; i++) {
                try {
                    field = wait.until(ExpectedConditions.presenceOfElementLocated(ocFirmLocator));

                    // Ensure element is attached & usable
                    if (field.isDisplayed() && field.isEnabled()) {
                        break;
                    }

                } catch (Exception e) {
                    Thread.sleep(1000); // wait for re-render
                }
            }

            if (field == null) {
                throw new RuntimeException("❌ OC Firm Name field not found after retries");
           }

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", field
            );

            // Click safely (Appian sometimes blocks normal click)
           try {
                field.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", field);
           }

            // 🔥 Clear + Set value (IMPORTANT for Appian inputs)
           ((JavascriptExecutor) driver).executeScript(
                    "let el = arguments[0];" +
                           "el.value = '';" +
                            "el.dispatchEvent(new Event('input', {bubbles:true}));",
                    field
            );

           // Set value using React-safe setter
            ((JavascriptExecutor) driver).executeScript(
                    "let el = arguments[0];" +
                           "let value = arguments[1];" +
                           "let setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype,'value').set;" +
                            "setter.call(el, value);" +
                            "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                            "el.dispatchEvent(new Event('change',{bubbles:true}));" +
                            "el.blur();",
                    field, ocFirmName
           ); */

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocFirmLocator).getAttribute("value");
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

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            ocJustification = DynamicDataUtil.getOCJustification(
                    ConfigReader.getNewOC("justificationText"), count);

            By ocJusLocator = By.xpath(
                    //"//strong[contains(text(),'New Outside Counsel Justification')]/following::input[1]"
            "//strong[contains(text(),'New Outside Counsel Justification')]/following::textarea[1]");

            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocJusLocator)
            );
            field.sendKeys(ocJustification);

            /*// FIX: Stable Appian-safe locator ()
            By textareaLocator = By.xpath(
                    "//textarea[contains(@aria-label,'Justification') or contains(@placeholder,'Justification')]"
            );

            WebElement textarea = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(textareaLocator)
            );

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    textarea
            );

            wait.until(ExpectedConditions.elementToBeClickable(textarea));

            try {
                textarea.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", textarea);
            }

            textarea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            textarea.sendKeys(Keys.DELETE);

            String justificationText = "Auto justification added by automation";
            textarea.sendKeys(justificationText);

            textarea.sendKeys(Keys.TAB); */
            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocJusLocator).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!ocJustification.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Justification mismatch. Expected: " + ocJustification + " | Got: " + actual
                );
            }

            System.out.println("Justification entered: " + actual);
            Allure.step("Justification entered: " + actual);
        });

    // STEP 5: Attorney Name
        Allure.step("Entering Attorney Name", () -> {

            attorneyName = DynamicDataUtil.getOCAttorney(
                    ConfigReader.getNewOC("ocAttorney"), count);

            By ocAttLocator = By.xpath(
                    "//strong[contains(text(),'Attorney Name')]/following::input[1]"
            );
            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocAttLocator)
            );
            field.sendKeys(attorneyName);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocAttLocator).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!attorneyName.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Attorney mismatch. Expected: " + attorneyName + " | Got: " + actual
                );
            }
            System.out.println("Attorney Name entered: " + actual);
            Allure.step("Attorney Name entered: " + actual);
        });

        // STEP 6: Phone
        Allure.step("Entering Phone", () -> {

            phoneNumber = ConfigReader.getNewOC("ocPhone");

            By ocPhLocator = By.xpath(
                    "//strong[contains(text(),'Phone')]/following::input[1]"
            );
            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocPhLocator)
            );
            field.sendKeys(phoneNumber);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocPhLocator).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!phoneNumber.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Attorney mismatch. Expected: " + phoneNumber + " | Got: " + actual
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

            emailAddress =  DynamicDataUtil.getOCEmail(
                    ConfigReader.getNewOC("ocEmail"), count);

            By ocEmLocator = By.xpath(
                    "//strong[contains(text(),'Email')]/following::input[1]"
            );
            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocEmLocator)
            );
            field.sendKeys(emailAddress);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocEmLocator).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!emailAddress.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Attorney mismatch. Expected: " + emailAddress + " | Got: " + actual
                );
            }

            System.out.println("Email entered: " + actual);
            Allure.step("Email entered: " + actual);
        });

        // STEP 9: Validate Email
        Allure.step("Validate Email", () -> {

            try {
                WebElement emailField = driver.switchTo().activeElement();
                String value = emailField.getAttribute("value");

                Thread.sleep(2500);

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

            city = ConfigReader.getNewOC("ocCity");

            By ocCTLocator = By.xpath(
                    "//strong[contains(text(),'Address')]/following::input[1]"
            );
            WebElement field = wait.until(
                    ExpectedConditions.elementToBeClickable(ocCTLocator)
            );
            field.sendKeys(city);

            // 🔥 Final stable verification
            String actual = wait.until(d -> {
                try {
                    String val = d.findElement(ocCTLocator).getAttribute("value");
                    return (val != null && !val.trim().isEmpty()) ? val : null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (!city.equals(actual)) {
                throw new RuntimeException(
                        "❌ OC Attorney mismatch. Expected: " + city + " | Got: " + actual
                );
            }
            System.out.println("City entered: " + actual);
            Allure.step("City entered: " + actual);
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

         // STEP 13: Capture Request Number (Post Submit Confirmation Screen)
        Allure.step("Capture NEWOC Request Number from confirmation screen", () -> {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            String newOCRequestNumber = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//span[contains(text(),'Legal Matter Request Initiated')]/following::span//strong")
                    )
            ).getText().trim();

            TestData.newOCRequestNumber = newOCRequestNumber;

            System.out.println("NEWOC Request Number: " + newOCRequestNumber);

            Allure.step("NEWOC Request Number captured: " + newOCRequestNumber);
            Allure.addAttachment("NewOC Request Number", newOCRequestNumber);
            //return newOCRequestNumber;

            // store if needed for later steps
            //this.newOCRequestNumber = newOCRequestNumber;
        });

        // STEP 14: Completion
        Allure.step("New OC Request Completed Successfully");
        SceenshotUtil.takeScreenshot(driver, "New OC Request Completed Successfully");
    }
}