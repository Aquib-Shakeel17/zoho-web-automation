package com.zoho.pages;

import com.zoho.utils.WaitUtil;
import org.openqa.selenium.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FilterPage extends BasePage {
    private static final Logger log = LogManager.getLogger(FilterPage.class);

    private By cityFilterCheckbox = By.xpath("//lyte-checkbox[@id='field_City']//span[@class='lyteCheckBoxDefault']");
    private By companyFilterCheckbox = By.xpath("//lyte-checkbox[@id='field_Company']//span[@class='lyteCheckBoxDefault']");
    private By filterInputField = By.xpath("//input[@placeholder='Type here']");
    private By applyFilterButton = By.xpath("//lyte-yield[normalize-space()='Apply Filter']");
    private By saveFilterButton = By.xpath("//button[@id='saveFilterBtn']");
    private By saveFilterInputField = By.xpath("//lyte-wormhole[@case='true']//input[1]");
    private By confirmSaveButton = By.xpath("//lyte-yield[normalize-space()='Save']");
    private By clearFilterButton = By.xpath("//span[@id='layerClearFilter']");
    private By alertMessage = By.xpath("//span[contains(@class,'alertHeader')]");
    private By savedFiltersList = By.xpath("//ul[@id='savedFilterSort']/li");

    public FilterPage(WebDriver driver) {
        super(driver);
    }

    public void applyCityFilter(String cityName){
        log.info("Pausing before selecting the city filter checkbox to prevent stale element issues.");
        WaitUtil.sleepFor(3000);  // Adding a small delay after the refresh

        // Handle dynamic XPath and select the FIRST matching accordion header
        log.info("Clicking on the first accordion to expand filters.");
        WebElement accordionElement = WaitUtil.waitForElementClickable(driver, By.xpath("(//lyte-accordion-header[starts-with(@id, 'lyte_accordion_header_')])[1]"));
        accordionElement.click();

        WaitUtil.sleepFor(1000);  // Adding a small delay after the refresh

        log.info("Selecting city filter checkbox.");
        WebElement cityCheckbox = WaitUtil.waitForElementClickable(driver, cityFilterCheckbox);
        cityCheckbox.click();

        log.info("Waiting for filter input field to appear.");
        WebElement inputField = WaitUtil.waitForElementVisible(driver, filterInputField);
        inputField.sendKeys(cityName);

        log.info("Entered city name: {}", cityName);

        WaitUtil.sleepFor(1000);  // Adding a small delay after the refresh

        // Ensure Apply Filter button is visible before clicking
        WebElement applyFilterElement = WaitUtil.waitForElementVisible(driver, applyFilterButton);

        log.info("Clicking on Apply Filter button.");
        WaitUtil.sleepFor(500);  // Adding a small delay after the refresh
        applyFilterElement.click();

        log.info("Successfully applied the filter.");
    }


    public String saveFilterName() {
        log.info("Waiting for Save Filter button and clicking it.");

        // Wait for Save Filter button to be clickable
        WebElement saveButton = WaitUtil.waitForElementClickable(driver, saveFilterButton);

        try {
            // Attempt to click the Save Filter button
            saveButton.click();
        } catch (ElementClickInterceptedException e) {
            // Handle overlay/modal interception
            log.warn("Click intercepted, handling overlay/modal...");

            // Wait for the overlay or modal to disappear
            try {
                log.info("Waiting for modal/overlay to disappear...");
                WaitUtil.waitForElementInvisibility(driver, By.xpath("//lyte-modal-footer")); // Update the XPath to your modal/overlay element
            } catch (TimeoutException te) {
                log.error("Timeout waiting for overlay to disappear.");
                throw new RuntimeException("Overlay did not disappear in time", te);
            }

            // Retry clicking the Save Filter button
            saveButton.click();
        }

        // Generate a random filter name
        String randomFilterName = "Filter_" + new Random().nextInt(10000);
        log.info("Entering filter name: {}", randomFilterName);
        WebElement inputField = WaitUtil.waitForElementVisible(driver, saveFilterInputField);
        inputField.clear();
        inputField.sendKeys(randomFilterName);

        // Wait briefly before proceeding (after entering the filter name)
        try {
            log.info("Waiting for 1 second before finding the save button...");
            Thread.sleep(1000); // Wait for 1 second (you can adjust this time as needed)
        } catch (InterruptedException ie) {
            log.error("Thread sleep interrupted: {}", ie.getMessage());
        }

        log.info("Clicking the Save button.");
        WebElement confirmSaveButtonElement = WaitUtil.waitForElementClickable(driver, confirmSaveButton);

        try {
            confirmSaveButtonElement.click(); // Click confirm save button
            log.info("Filter saved successfully with name: {}", randomFilterName);

            // Wait for 4 seconds after clicking the save button
            log.info("Waiting for 4 seconds after save button click...");
            Thread.sleep(4000); // Wait for 4 seconds before moving forward
        } catch (ElementClickInterceptedException e) {
            log.error("Click intercepted again, retrying after handling the modal.");
            WaitUtil.sleepFor(2000);  // Adding a small delay after the refresh

            // Retry clicking the confirm button
            confirmSaveButtonElement.click();
        } catch (InterruptedException e) {
            log.error("Thread sleep interrupted: {}", e.getMessage());
        }
        return randomFilterName;  // Return the random filter name to be used for validation
    }


    public boolean isFilterPresent(String filterName) {
        log.info("Validating if the saved filter '{}' is present in the list.", filterName);

        // Refresh the page
        driver.navigate().refresh();
        WaitUtil.sleepFor(3000);  // Adding a small delay after the refresh

        // Wait until the filters list is visible and loaded
        WaitUtil.waitForElementPresence(driver, savedFiltersList);

        // Find all the filters
        List<WebElement> filters = driver.findElements(savedFiltersList);
        log.info("Total filters found: {}", filters.size()); // Log total number of filters found

        // Normalize the filter name for comparison
        String normalizedFilterName = filterName.trim(); // Trim the expected filter name to avoid extra spaces

        // Iterate through the filters
        for (WebElement filter : filters) {
            String currentFilterName = filter.getText().trim();  // Trim the current filter name

            // Log the name of each filter being checked for debugging
            log.info("Checking filter: '{}'", currentFilterName);

            // Log Unicode values for debugging
            log.info("Current filter name (Unicode): '{}'", currentFilterName.chars().mapToObj(Integer::toHexString).collect(Collectors.joining(" ")));
            log.info("Expected filter name (Unicode): '{}'", normalizedFilterName.chars().mapToObj(Integer::toHexString).collect(Collectors.joining(" ")));

            // Clean the filter name by removing unwanted characters like newlines, spaces, or non-ASCII characters
            String cleanedFilterName = currentFilterName.replaceAll("[^\\x20-\\x7e]", "").trim();

            // Compare the cleaned current filter name with the expected filter name
            if (cleanedFilterName.equals(normalizedFilterName)) {
                log.info("Filter '{}' found in the saved filters list.", filterName);
                return true;
            }
        }

        // If the filter was not found, log a warning and return false
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

        WaitUtil.sleepFor(3000);  // Adding a small delay after the refresh
        log.info("Selecting city filter checkbox.");
        WaitUtil.waitForElementClickable(driver, cityFilterCheckbox).click();
    }


    public String getAlertMessage() {
        log.info("Checking for alert message when applying filter without input.");
        WebElement alertElement = WaitUtil.waitForElementVisible(driver, alertMessage);
        return (alertElement != null) ? alertElement.getText() : "";
    }
}
