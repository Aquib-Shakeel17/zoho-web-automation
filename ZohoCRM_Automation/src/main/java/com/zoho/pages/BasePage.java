package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.zoho.utils.WaitUtil;

/**
 * BasePage contains common reusable Selenium actions for all page classes.
 */
public class BasePage {
    protected WebDriver driver; // WebDriver instance for browser interactions
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    // Constructor to initialize WebDriver
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Clicks on an element after waiting for it to be clickable
    protected void click(By locator) {
        log.info("Clicking on element: " + locator);
        WaitUtil.waitForElementClickable(driver, locator).click();
    }

    // Clears existing text and enters new text into an input field
    protected void sendKeys(By locator, String text) {
        log.info("Entering text into element: " + locator);
        WaitUtil.waitForElementVisible(driver, locator).clear(); // Clear text field
        WaitUtil.waitForElementVisible(driver, locator).sendKeys(text); // Enter text
    }

    // Retrieves text from an element
    protected String getText(By locator) {
        log.info("Getting text from element: " + locator);
        return WaitUtil.waitForElementVisible(driver, locator).getText();
    }

    // Checks if an element is displayed on the page
    protected boolean isElementDisplayed(By locator) {
        try {
            return WaitUtil.waitForElementVisible(driver, locator).isDisplayed();
        } catch (Exception e) {
            log.error("Element not found or not visible: " + locator, e);
            return false; // Return false if the element is not found
        }
    }
}
