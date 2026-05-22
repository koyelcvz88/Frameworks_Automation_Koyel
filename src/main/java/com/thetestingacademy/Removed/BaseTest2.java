/*package com.thetestingacademy.Removed;

import com.thetestingacademy.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest2 {

    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:/Webdrivers/msedgedriver.exe");

        //WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        System.out.println("Browser launched successfully");
    }

    //public void openApplication(String url)
    public void openApplication()
    {
        //driver.get(ConfigReader.get("base.url"));
        //String url = ConfigReader.get("base.url");
        String url = ConfigReader.getData("base.url");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("Base URL is missing in config file");
        }

        System.out.println("BASE URL = [" + ConfigReader.getData("base.url") + "]");
        driver.get(url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        System.out.println("Browser closed successfully");
    }
}*/