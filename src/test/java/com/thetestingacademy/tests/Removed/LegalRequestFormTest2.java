/*package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.pages.*;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC2 - Fill Legal Request Form")
public class LegalRequestFormTest2 extends BaseTest {

    /* private String suiteType;

    // =========================
    // CAPTURE SUITE TYPE
    // =========================
    @BeforeMethod(alwaysRun = true)
    public void setSuiteType(ITestContext context) {
        if (context.getIncludedGroups().length > 0) {
            suiteType = context.getIncludedGroups()[0];
        }
        System.out.println("Running suite = " + suiteType);
    }

    // =========================
    // MAIN TEST
    // =========================
    @Test(groups = {"Smoke", "Sanity", "E2E", "Regression"})
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC3 - Fill Legal Request Form for Existing & New OC")
    public void tc3_fill_legal_request_form() {

        // =========================
        // SMOKE → ONLY EXISTING OC
        // =========================
        //if ("Smoke".equalsIgnoreCase(suiteType)) {

            runFlow(
                    ConfigReader.getData("existing.username"),
                    ConfigReader.getData("existing.password")
            );

        /*} else {

            // =========================
            // STEP 1 → EXISTING OC
            // =========================
            runFlow(
                    ConfigReader.getData("existing.username"),
                    ConfigReader.getData("existing.password")
            );

            // =========================
            // RESET SESSION (NO QUIT)
            // =========================
            driver.manage().deleteAllCookies();
            driver.navigate().to(ConfigReader.getData("base.url"));

            // =========================
            // STEP 2 → NEW OC
            // =========================
            runFlow(
                    ConfigReader.getData("new.username"),
                    ConfigReader.getData("new.password")
            );
        //}
    }

    // =========================
    // COMMON FLOW (LOGIN + INIT + FORM)
    // =========================
    private void runFlow(String username, String password) {

        openApplication();

        // -------------------------
        // LOGIN
        // -------------------------
        LoginPage loginPage = new LoginPage(driver);

        DashboardPage dashboard = loginPage
                .enterUsername(username)
                .enterPassword(password)
                .clickSignin();

        Assert.assertTrue(
                dashboard.isLoaded(),
                "Login failed for user: " + username
        );

        // -------------------------
        // INITIATION
        // -------------------------
        LegalRequestInitiationPage initiationPage =
                new LegalRequestInitiationPage(driver);

        initiationPage.clickInitiateLegalRequest();

        Assert.assertTrue(
                initiationPage.isFormLoaded() &&
                        initiationPage.isBannerDisplayed(),
                "Initiation failed"
        );

        // -------------------------
        // FORM FILL
        // -------------------------
        LegalRequestFormPage formPage =
                new LegalRequestFormPage(driver);

        Allure.step("Filling Legal Request Form for user: " + username, () -> {
            formPage.fillLegalRequestForm();
        });

        Allure.step("TC2 Passed for user: " + username);
    }
} */