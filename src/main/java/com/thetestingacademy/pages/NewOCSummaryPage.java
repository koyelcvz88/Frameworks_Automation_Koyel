package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NewOCSummaryPage extends CommonUIActions {

    public NewOCSummaryPage(WebDriver driver) {
        super(driver);
    }

    // =====================================================
    // LOCATORS
    // =====================================================

    private final By tasksTab =
            By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Tasks']");

    private final By taskTable =
            By.xpath("//table");

    private final By taskHeaders =
            By.xpath(".//thead//span[contains(@class,'GridHeaderCell---header_label')]");

    private final By taskRows =
            By.xpath(".//tbody//tr");

    // =====================================================
    // STORED VALUES
    // =====================================================

    protected String currentUrl;

    // =====================================================
    // STEP 1 : Validate Summary Page
    // =====================================================

    @Step("Validate user is on Summary page")
    public boolean isSummaryPageLoaded() {

        wait.until(
                ExpectedConditions.urlContains("/view/summary")
        );

        currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL : " + currentUrl);

        return currentUrl.contains("/record/")
                && currentUrl.contains("/summary");
    }

    // =====================================================
    // STEP 2 : Navigate To Tasks Tab
    // =====================================================

    @Step("Navigate to Tasks tab")
    public void navigateToTasksTab() {

        WebElement tab = wait.until(
                ExpectedConditions.elementToBeClickable(tasksTab)
        );

        tab.click();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(taskTable)
        );

        System.out.println("Tasks tab opened successfully");
    }

    // =====================================================
    // STEP 3 : Open Task By Name
    // =====================================================

    @Step("Open task with name : {taskName}")
    public void openTaskByName(String taskName) {

        By taskLink = By.xpath(
                "//table//tbody//tr//td[2]//a[normalize-space()='"
                        + taskName + "']"
        );

        WebElement task = wait.until(
                ExpectedConditions.elementToBeClickable(taskLink)
        );

        task.click();

        System.out.println("Task opened : " + taskName);
    }

    // =====================================================
    // STEP 4 : Get All Task Data
    // =====================================================

    @Step("Fetch all task table data")
    public List<Map<String, String>> getAllTasksData() {

        List<Map<String, String>> tableData =
                new ArrayList<>();

        WebElement table = wait.until(
                ExpectedConditions.visibilityOfElementLocated(taskTable)
        );

        List<WebElement> headers =
                table.findElements(taskHeaders);

        List<String> headerNames =
                new ArrayList<>();

        for (WebElement header : headers) {

            headerNames.add(
                    header.getText().trim()
            );
        }

        List<WebElement> rows =
                table.findElements(taskRows);

        for (WebElement row : rows) {

            List<WebElement> cols =
                    row.findElements(By.xpath("./td"));

            Map<String, String> rowData =
                    new LinkedHashMap<>();

            for (int i = 1; i < cols.size(); i++) {

                String header =
                        headerNames.get(i - 1);

                String value;

                List<WebElement> links =
                        cols.get(i).findElements(By.tagName("a"));

                if (!links.isEmpty()) {

                    value = links.get(0)
                            .getText()
                            .trim();

                } else {

                    value = cols.get(i)
                            .getText()
                            .trim();
                }

                rowData.put(header, value);
            }

            tableData.add(rowData);
        }

        return tableData;
    }

    // =====================================================
    // STEP 5 : Print Task Data
    // =====================================================

    @Step("Print all task data")
    public void printTasks(List<Map<String, String>> taskData) {

        for (Map<String, String> row : taskData) {

            System.out.println(
                    "=============================="
            );

            System.out.println(
                    "TASK DETAILS"
            );

            System.out.println(
                    "=============================="
            );

            row.forEach(
                    (key, value) ->
                            System.out.println(
                                    key + " : " + value
                            )
            );
        }
    }
}