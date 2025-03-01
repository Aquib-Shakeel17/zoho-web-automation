package com.zoho.pages;

import com.zoho.utils.WaitUtil;
import org.openqa.selenium.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class FilterPage extends BasePage {
    private static final Logger log = LogManager.getLogger(FilterPage.class);

    private By cityFilterCheckbox = By.xpath("//lyte-checkbox[@id='field_City']//span[@class='lyteCheckBoxDefault']");
    private By filterInputField = By.xpath("//input[@placeholder='Type here']");
    private By applyFilterButton = By.xpath("//lyte-yield[normalize-space()='Apply Filter']");
    private By saveFilterButton = By.xpath("//lyte-yield[normalize-space()='Save filter']");
    private By saveFilterInputField = By.xpath("//lyte-wormhole[@case='true']//input[1]");
    private By confirmSaveButton = By.xpath("//lyte-wormhole[@case='true']//input[1]");
    private By clearFilterButton = By.xpath("//span[@id='layerClearFilter']");
    private By alertMessage = By.xpath("//span[contains(@class,'alertHeader')]");
    private By savedFiltersList = By.xpath("//ul[@id='savedFilterSort']/li");



    public FilterPage(WebDriver driver) {
        super(driver);
    }

    public void applyCityFilter(String cityName) throws InterruptedException {
        log.info("Pausing before selecting the city filter checkbox to prevent stale element issues.");
        Thread.sleep(3000); // Short delay to prevent stale elements

        // Handle dynamic XPath and select the FIRST matching accordion header
        log.info("Clicking on the first accordion to expand filters.");
        WebElement accordionElement = WaitUtil.waitForElementClickable(driver, By.xpath("(//lyte-accordion-header[starts-with(@id, 'lyte_accordion_header_')])[1]"));
        accordionElement.click();

        Thread.sleep(1000); // Allow UI to update after expanding the accordion

        log.info("Selecting city filter checkbox.");
        WebElement cityCheckbox = WaitUtil.waitForElementClickable(driver, cityFilterCheckbox);
        cityCheckbox.click();

        log.info("Waiting for filter input field to appear.");
        WebElement inputField = WaitUtil.waitForElementVisible(driver, filterInputField);
        inputField.sendKeys(cityName);

        log.info("Entered city name: {}", cityName);

        Thread.sleep(1000); // Allow UI to settle

        // Ensure Apply Filter button is visible before clicking
        WebElement applyFilterElement = WaitUtil.waitForElementVisible(driver, applyFilterButton);

        log.info("Clicking on Apply Filter button.");
        Thread.sleep(500); // Short wait to stabilize UI
        applyFilterElement.click();

        log.info("Successfully applied the filter.");
    }


    public void applyCityFilterWithEnterKey(String cityName) {
        log.info("Pausing before selecting the city filter checkbox to prevent stale element issues.");

        // Short pause before interacting with the checkbox (can be replaced with an explicit wait)
        try {
            Thread.sleep(3000);  // Adjust delay if needed (500-1000ms)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Selecting city filter checkbox.");
        WaitUtil.waitForElementClickable(driver, cityFilterCheckbox).click();

        log.info("Waiting for filter input field to appear.");
        WebElement filterInput = WaitUtil.waitForElementVisible(driver, filterInputField);
        filterInput.sendKeys(cityName);

        log.info("Entered city name: {}", cityName);
        log.info("Pressing Enter key to apply the filter.");
        filterInput.sendKeys(Keys.ENTER);

        log.info("Successfully applied the filter using Enter key.");
    }


    public void saveFilter() {
        log.info("Waiting for Save Filter button and clicking it.");
        WaitUtil.waitForElementClickable(driver, saveFilterButton).click();
        log.info("Filter has been saved successfully.");
    }

    public void saveFilterName() {
        log.info("Waiting for Save Filter button and clicking it.");
        WaitUtil.waitForElementClickable(driver, saveFilterButton).click();

        // Generate a random filter name
        String randomFilterName = "Filter_" + new Random().nextInt(10000);

        log.info("Entering filter name: " + randomFilterName);
        WebElement inputField = WaitUtil.waitForElementVisible(driver, saveFilterInputField);
        inputField.clear();
        inputField.sendKeys(randomFilterName);

        log.info("Clicking the Save button.");
        WaitUtil.waitForElementClickable(driver, confirmSaveButton).click();

        log.info("Filter saved successfully with name: " + randomFilterName);
    }

    public boolean isFilterPresent(String filterName) {
        log.info("Validating if the saved filter '{}' is present in the list.", filterName);

        // Wait until at least one filter is present
        WaitUtil.waitForElementPresence(driver, savedFiltersList);

        List<WebElement> filters = driver.findElements(savedFiltersList);
        for (WebElement filter : filters) {
            String currentFilterName = filter.getText().trim();
            log.info("Checking filter: {}", currentFilterName);

            if (currentFilterName.equals(filterName)) {
                log.info("Filter '{}' found in the saved filters list.", filterName);
                return true;
            }
        }

        log.warn("Filter '{}' not found in the saved filters list.", filterName);
        return false;
    }

    public void clickApplyFilterWithoutInput() {

        log.info("Clicking Apply Filter button without entering any input.");
        WebElement applyFilterElement = WaitUtil.waitForElementVisible(driver, applyFilterButton);

        // Scroll to ensure visibility
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyFilterElement);

        // Click Apply Filter
        WaitUtil.waitForElementClickable(driver, applyFilterButton).click();
    }

    public void selectCityFilter() {

        log.info("Pausing before selecting the city filter checkbox to prevent stale element issues.");

        // Short pause before interacting with the checkbox (can be replaced with an explicit wait)
        try {
            Thread.sleep(3000);  // Adjust delay if needed (500-1000ms)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Selecting city filter checkbox.");
        WaitUtil.waitForElementClickable(driver, cityFilterCheckbox).click();

    }


    public void clearFilter() {
        log.info("Waiting for Clear Filter button and clicking it.");
        WaitUtil.waitForElementClickable(driver, clearFilterButton).click();
        log.info("Filter has been cleared successfully.");
    }

    public String getAlertMessage() {
        log.info("Checking for alert message when applying filter without input.");
        WebElement alertElement = WaitUtil.waitForElementVisible(driver, alertMessage);
        return (alertElement != null) ? alertElement.getText() : "";
    }
}
