package com.thetestingacademy.pages;

import com.thetestingacademy.actions.CommonUIActions;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

public class NewOCSummaryPage extends CommonUIActions {

    public NewOCSummaryPage(WebDriver driver) {
        super(driver);
    }

    private final By tasksTab =
            By.xpath("//div[contains(@class,'TabButtonWidget') and text()='Tasks']");

    private final By taskTable =
            By.xpath("//table");

    private final By taskHeaders =
            By.xpath(".//thead//span[contains(@class,'GridHeaderCell---header_label')]");

    private final By taskRows =
            By.xpath(".//tbody//tr");

    protected String currentUrl;

    // =====================================================
    // FIX: SAFE WINDOW + SUMMARY VALIDATION
    // =====================================================
    @Step("Validate user is on Summary page")
    public boolean isSummaryPageLoaded() {

        /*try {
            ensureSingleWindow();   // 🔥 IMPORTANT FIX

            waitForUIRender();

            boolean loaded = wait.until(d -> {
                try {
                    String url = d.getCurrentUrl();
                    return url != null
                            && url.contains("/record/")
                            && url.contains("/summary");
                } catch (NoSuchWindowException e) {
                    ensureSingleWindow();
                    return false;
                }
            });

            currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL : " + currentUrl);

            return loaded;

        } catch (Exception e) {
            System.out.println("⚠️ Summary page not stable: " + e.getMessage());
            return false;
        } */
        wait.until(
                ExpectedConditions.urlContains("/view/summary")
        );

        currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL : " + currentUrl);

        return currentUrl.contains("/record/")
                && currentUrl.contains("/summary");
    }

    // =====================================================
    // FIX: SAFE TAB CLICK
    // =====================================================
    @Step("Navigate to Tasks tab")
    public void navigateToTasksTab() {

        ensureSingleWindow();  // 🔥 prevent tab drift

        waitForClickable(tasksTab);

        WebElement tab = driver.findElement(tasksTab);
        tab.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(taskTable));

        System.out.println("Tasks tab opened successfully");
    }

    // =====================================================
    @Step("Open task with name : {taskName}")
    public void openTaskByName(String taskName) {

        ensureSingleWindow();

        By taskLink = By.xpath(
                "//table//tbody//tr//td[2]//a[normalize-space()='"
                        + taskName + "']"
        );

        waitForClickable(taskLink);

        driver.findElement(taskLink).click();

        System.out.println("Task opened : " + taskName);
    }

    // =====================================================
    @Step("Fetch all task table data")
    public List<Map<String, String>> getAllTasksData() {

        ensureSingleWindow();

        List<Map<String, String>> tableData = new ArrayList<>();

        waitForVisible(taskTable);

        WebElement table = driver.findElement(taskTable);

        List<WebElement> headers = table.findElements(taskHeaders);

        List<String> headerNames = new ArrayList<>();

        for (WebElement header : headers) {
            headerNames.add(header.getText().trim());
        }

        List<WebElement> rows = table.findElements(taskRows);

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

    // =====================================================
    @Step("Print all task data")
    public void printTasks(List<Map<String, String>> taskData) {

        for (Map<String, String> row : taskData) {

            System.out.println("==============================");
            System.out.println("TASK DETAILS");
            System.out.println("==============================");

            row.forEach((k, v) -> System.out.println(k + " : " + v));
        }
    }
}