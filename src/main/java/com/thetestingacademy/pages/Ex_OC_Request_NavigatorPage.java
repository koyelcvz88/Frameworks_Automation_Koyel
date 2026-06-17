package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.utils.SceenshotUtil;
import com.thetestingacademy.utils.TestData;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
public class Ex_OC_Request_NavigatorPage extends CommonUIActions {

    private final DataModel testData;

    public Ex_OC_Request_NavigatorPage(WebDriver driver, DataModel testData) {
        super(driver);
        this.testData = testData;
    }

    // =====================================================
    // LOCATORS
    // =====================================================

    private final By claimButton =
            By.xpath("//span[normalize-space()='Claim']");

    private final By taskTable =
            By.xpath("//table | //div[contains(@class,'Table')]");

    private final By loadingSpinner =
            By.xpath("//*[contains(@class,'loading') or contains(@class,'spinner')]");

    // =====================================================
    // STORED VALUES
    // =====================================================
    protected String targetRequest;

    // =========================================================
    // SINGLE BUSINESS FLOW METHOD (RECOMMENDED)
    // =========================================================
    public void exOCRequest() {

        Allure.step("Home → Open Latest EX OC Request Number", () -> {

            // -------------------------------
            // STEP 1: Navigate to Home
            // -------------------------------
            String homeUrl = ConfigReader.getData("home.url");

            driver.navigate().to(homeUrl);

            wait.until(ExpectedConditions.urlContains("/home"));

            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);

            Assert.assertTrue(currentUrl.contains("/home"),
                    "❌ Home URL mismatch: " + currentUrl);

            SceenshotUtil.takeScreenshot(driver, "Home Page Loaded");

            // ==========================================
            // STEP 2: Get Request Number
            // ==========================================

            Allure.step("Fetch EX OC Request Number", () -> {

                targetRequest = TestData.exOCRequestNumber;

                if (targetRequest == null || targetRequest.isEmpty()) {

                    throw new RuntimeException(
                            "EX OC Request Number missing in TestData"
                    );
                }

                //  Remove '#'
                targetRequest = targetRequest.replace("#", "").trim();

                System.out.println("Processed Target Request : " + targetRequest);

                Allure.step(
                        "Target Request : " + targetRequest
                );
            });

            // ==========================================
            // STEP 3: Open Request
            // ==========================================

            Allure.step("Open Existing OC Request", () -> {

                By requestLink = By.xpath(
                        "//a[contains(@class,'LinkedItem') and contains(text(),'"
                                + targetRequest + "')]"
                );

                try {

                    /*WebElement request = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    requestLink
                            )
                    );

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            request
                    );

                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    request
                            )
                    ); */

                    waitForVisible(requestLink);

                    WebElement request = driver.findElement(requestLink);

                    scrollToElement(request);

                    waitForClickable(requestLink);

                    request.click();

                    System.out.println("Clicked EX OC Request : " + targetRequest);

                    SceenshotUtil.takeScreenshot(
                            driver,
                            "Clicked EX OC Request"
                    );

                } catch (Exception e) {

                    SceenshotUtil.takeScreenshot(
                            driver,
                            "EX OC Request Not Found"
                    );

                    throw new RuntimeException(
                            "EX OC Request not found on Home Page : "
                                    + targetRequest
                    );
                }
            });

            // ==========================================
            // STEP 4: Wait For Task Grid Load - POST CLICK STABILIZATION
            // ==========================================

            Allure.step("Wait For Task Grid To Load", () -> {

                /*wait.until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete")
                ); */
                waitForPageLoad();

                wait.until(
                        ExpectedConditions.or(
                                ExpectedConditions.presenceOfElementLocated(
                                        claimButton
                                ),
                                ExpectedConditions.presenceOfElementLocated(
                                        taskTable
                                )
                        )
                );

                try {

                    wait.until(
                            ExpectedConditions.invisibilityOfElementLocated(
                                    loadingSpinner
                            )
                    );

                } catch (Exception ignored) {

                    System.out.println("Loading spinner not present");
                }

                System.out.println("Task grid fully loaded after request open");

                SceenshotUtil.takeScreenshot(driver, "Task Grid Loaded");
            });

            // ==========================================
            // FINAL STEP
            // ==========================================

            Allure.step("Existing OC Request Opened Successfully", () -> {

                SceenshotUtil.takeScreenshot(driver, "Existing OC Request Opened Successfully");

            });
        });
    }
}

