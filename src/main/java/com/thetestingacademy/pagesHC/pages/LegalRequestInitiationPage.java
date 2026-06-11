package com.thetestingacademy.pagesHC.pages;

import com.thetestingacademy.actions.CommonUIActions;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;

public class LegalRequestInitiationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final CommonUIActions ui;

    // =========================
    // CONSTRUCTOR
    // =========================
    public LegalRequestInitiationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        this.ui = new CommonUIActions(driver);
    }

    // =========================
    // LOCATORS
    // =========================

    private By initiateBtn =
            By.xpath("//button[.//span[text()='Initiate Legal Request']]");

    private By formHeader =
            By.xpath("//span[contains(text(),'Initiate Legal Matter Request')]");

    private By banner =
            By.xpath("//span[contains(text(),'Hello')]");

    // =========================
    // VALIDATION : BANNER
    // =========================

    public boolean isBannerDisplayed() {

        return Allure.step("Verify banner is displayed", () -> {

            try {

                boolean isDisplayed = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(banner)
                ).isDisplayed();

                if (isDisplayed) {
                    captureScreenshot("Success: banner is displayed");
                }

                return isDisplayed;

            } catch (Exception e) {

                attachFailureDetails(e);

                return false;
            }
        });
    }

    // =========================
    // ACTION : CLICK INITIATE
    // =========================

    public LegalRequestInitiationPage clickInitiateLegalRequest() {

        return Allure.step("Click Initiate Legal Request button", () -> {

            ui.scrollToElement(initiateBtn);

            ui.click(initiateBtn);

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(formHeader)
            );

            captureScreenshot("Initiate Legal Request Form Loaded");

            return this;
        });
    }

    // =========================
    // VALIDATION : FORM LOADED
    // =========================

    public boolean isFormLoaded() {

        return Allure.step("Verify Legal Request form is loaded", () -> {

            try {

                boolean isLoaded = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(formHeader)
                ).isDisplayed();

                if (isLoaded) {
                    captureScreenshot("Form Loaded Screenshot");
                }

                return isLoaded;

            } catch (Exception e) {

                attachFailureDetails(e);

                return false;
            }
        });
    }

    // =========================
    // SCREENSHOT HELPER
    // =========================

    public void captureScreenshot(String screenshotName) {

        byte[] screenshot =
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                screenshotName,
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }

    // =========================
    // FAILURE HANDLER
    // =========================

    private void attachFailureDetails(Exception e) {

        byte[] screenshot =
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Failure Screenshot",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );

        Allure.addAttachment(
                "Error Message",
                "text/plain",
                e.getMessage(),
                ".txt"
        );

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        Allure.addAttachment(
                "Stack Trace",
                "text/plain",
                sw.toString(),
                ".txt"
        );

        Allure.addAttachment(
                "Failure URL",
                driver.getCurrentUrl()
        );
    }
}