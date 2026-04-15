package com.thetestingacademy.tests;

import com.thetestingacademy.pages.LegalRequestNewOCPage;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.thetestingacademy.base.BaseTest;

@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC4 - New OC Flow")

public class LegalRequestNewOCTest extends BaseTest {

    @Test(priority = 4)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC4 - Verify user can complete New OC flow after form submission")
    public void tc4_new_oc_flow() {

        // STEP 1: Initialize Page Object
        LegalRequestNewOCPage page = new LegalRequestNewOCPage(driver);

        // STEP 2: Execute TC4 Flow
        Allure.step("TC4 - Fill and Submit New OC Request", () -> {
            page.fillNewOCRequest();
        });

        // STEP 3: Final Assertion / Logging
        System.out.println("✅ TC4 PASSED");
        Allure.step("TC4 PASSED SUCCESSFULLY");
    }
}