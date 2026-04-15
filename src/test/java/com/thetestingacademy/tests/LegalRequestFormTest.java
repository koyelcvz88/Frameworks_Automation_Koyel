package com.thetestingacademy.tests;

import com.thetestingacademy.pages.LegalRequestFormPage;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.thetestingacademy.base.BaseTest;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC2 - Fill Legal Request Form")
public class LegalRequestFormTest extends BaseTest {

    LegalRequestFormPage formPage;

    @Test(priority = 2)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC2 - Fill Legal Request Form after login and initiation")
    public void tc2_fill_legal_request_form() {

        // STEP 1: Initialize Form Page (reuse existing session and driver)
        formPage = new LegalRequestFormPage(driver);
        Allure.step("Initialized Legal Request Form Page");

        // STEP 2: Fill the Legal Request Form
        Allure.step("Filling the Legal Request Form", () -> {
            formPage.fillLegalRequestForm();
        });

        // STEP 3: Log success
        System.out.println("✅ TC2 PASSED");
        Allure.step("TC2 Passed Successfully!");
    }
}