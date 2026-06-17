package com.thetestingacademy.OldCode;

import com.thetestingacademy.config.ConfigReader;
import com.thetestingacademy.model.DataModel;
import com.thetestingacademy.pages.DashboardPage;
import com.thetestingacademy.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected DataModel dataModel;

    @BeforeClass(alwaysRun = true)
    public void setUp() {

        String driverPath = ConfigReader.getData("edge.driver.path");

        if (driverPath == null || driverPath.isEmpty()) {
            throw new RuntimeException("Edge driver path is missing in config file");
        }

        File file = new File(driverPath);
        if (!file.exists()) {
            throw new RuntimeException("Edge driver not found at: " + driverPath);
        }

        System.setProperty("webdriver.edge.driver", driverPath);

        driver = new EdgeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigReader.getData("implicit.wait"))
        ));

        // ❗ You can initialize DataModel here (recommended placeholder)
        //dataModel = new DataModel();

        openApplication();

        System.out.println("Browser launched successfully");
    }

    public void openApplication() {
        String url = ConfigReader.getData("base.url");

        if (url == null || url.isEmpty()) {
            throw new RuntimeException("Base URL is missing in config file");
        }

        System.out.println("BASE URL = [" + url + "]");
        driver.get(url);
    }

    public DashboardPage loginAs(String role) {

        LoginPage loginPage = new LoginPage(driver);

        String username = ConfigReader.getData(role + ".username");
        String password = ConfigReader.getData(role + ".password");

        if (username == null || password == null) {
            throw new RuntimeException("Credentials missing for role: " + role);
        }

        return loginPage.enterUsername(username)
                .enterPassword(password)
                .clickSignin();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        System.out.println("Browser closed successfully");
    }
}