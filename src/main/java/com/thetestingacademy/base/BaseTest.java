package com.thetestingacademy.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class BaseTest {


    protected static WebDriver driver;

    @BeforeClass
    public void setUp() {

        System.setProperty("webdriver.edge.driver", "C:/Webdrivers/msedgedriver.exe");

        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    // You control navigation from test classes
    public void openApplication(String url) {
        driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}