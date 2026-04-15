package com.thetestingacademy.tests;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    @Feature("Login Module")
    @Story("Valid Login Flow")
    @Description("Verify user can login successfully")
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage
                .enterUsername("AM_CMSTest")
                .enterPassword("Maantic@2026");

        loginPage.clickSignin();
    }
}