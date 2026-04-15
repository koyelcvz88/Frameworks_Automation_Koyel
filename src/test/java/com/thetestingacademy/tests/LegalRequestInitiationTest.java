package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.pages.LegalRequestInitiationPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("VenReq Automation Suite")
@Feature("Legal Request Module")
@Story("TC1 - Initiate Legal Request")
public class LegalRequestInitiationTest extends BaseTest {

    @Test(priority = 1)
    @Owner("Koyel")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC1 - Verify user can initiate legal request")
    public void tc1_initiate_legal_request() {
        LegalRequestInitiationPage initiationPage = new LegalRequestInitiationPage(driver);

        Allure.step("Initiate Legal Request", () -> {
            initiationPage.clickInitiateLegalRequest();
        });

        System.out.println("✅ TC1 PASSED");
        Allure.step("TC1 PASSED");
    }
}