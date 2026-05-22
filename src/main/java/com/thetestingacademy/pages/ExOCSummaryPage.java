package com.thetestingacademy.pages;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;


public class ExOCSummaryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ExOCSummaryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ==============================
    // STEP 1 - Validate Summary URL
    // ==============================
    @Step("Validate user is on Summary page")
    public boolean isSummaryPageLoaded() {
        wait.until(ExpectedConditions.urlContains("/view/summary"));
        String url = driver.getCurrentUrl();
        return url.contains("/record/") && url.contains("/summary");
    }

    // ==============================
    // STEP 2 - Navigate to Tasks Tab
    // ==============================
    private By tasksTab = By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Tasks']");

    @Step("Navigate to Tasks tab")
    public void navigateToTasksTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tasksTab));
        tab.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));
    }

    // ==============================
    // STEP 3 - Open Task by Name
    // ==============================
    @Step("Open task with name: {taskName}")
    public void openTaskByName(String taskName) {

        String xpath = "//table//tbody//tr//td[2]//a[normalize-space()='" + taskName + "']";

        WebElement taskLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        );

        taskLink.click();
    }

    // ==============================
    // STEP 4 - Get All Task Table Data
    // ==============================
    @Step("Fetch all task table data")
    public List<Map<String, String>> getAllTasksData() {

        List<Map<String, String>> tableData = new ArrayList<>();

        WebElement table = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//table"))
        );

        List<WebElement> headers = table.findElements(
                By.xpath(".//thead//span[contains(@class,'GridHeaderCell---header_label')]")
        );

        List<String> headerNames = new ArrayList<>();
        for (WebElement header : headers) {
            headerNames.add(header.getText().trim());
        }

        List<WebElement> rows = table.findElements(By.xpath(".//tbody//tr"));

        for (WebElement row : rows) {

            List<WebElement> cols = row.findElements(By.xpath("./td"));
            Map<String, String> rowData = new LinkedHashMap<>();

            for (int i = 1; i < cols.size(); i++) {

                String header = headerNames.get(i - 1);
                String value;

                List<WebElement> links = cols.get(i).findElements(By.tagName("a"));

                if (!links.isEmpty()) {
                    value = links.get(0).getText().trim();
                } else {
                    value = cols.get(i).getText().trim();
                }

                rowData.put(header, value);
            }

            tableData.add(rowData);
        }

        return tableData;
    }

    // ==============================
    // Utility - Print Tasks
    // ==============================
    @Step("Print all task data")
    public void printTasks(List<Map<String, String>> data) {
        for (Map<String, String> row : data) {
            System.out.println("------ TASK ------");
            row.forEach((k, v) -> System.out.println(k + " : " + v));
        }
    }
}