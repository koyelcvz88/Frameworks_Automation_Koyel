package com.thetestingacademy.tests;

import com.thetestingacademy.pages.LegalRequestExistingOCPage;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.thetestingacademy.base.BaseTest;
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC3 - Existing OC Flow")

public class LegalRequestExistingOCTest extends BaseTest {

    @Test(priority = 3)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC3 - Verify user can complete Existing OC flow after form submission")
    public void tc3_existing_oc_flow() {

        // STEP 1: Initialize Page Object
        LegalRequestExistingOCPage page = new LegalRequestExistingOCPage(driver);

        // STEP 2: Execute TC3 Flow
        Allure.step("TC3 - Fill and Submit Existing OC Request", () -> {
            page.fillExistingOCRequest();
        });

        // STEP 3: Final Assertion / Logging
        System.out.println("✅ TC3 PASSED");
        Allure.step("TC3 PASSED SUCCESSFULLY");
    }
}