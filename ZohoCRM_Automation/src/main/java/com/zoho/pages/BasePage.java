package com.zoho.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Updated Click method to handle StaleElementReferenceException
    protected void click(By locator) {
        int attempts = 0;
        while (attempts < 3) { // Retry mechanism with a delay
            try {
                log.info("Attempting to click on element: " + locator + " | Attempt: " + (attempts + 1));
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                log.info("Successfully clicked on element: " + locator);
                return;
            } catch (StaleElementReferenceException e) {
                log.warn("StaleElementReferenceException encountered. Retrying in 500ms... Attempt: " + (attempts + 1));
                try {
                    Thread.sleep(500); // Small delay before retrying
                } catch (InterruptedException ignored) {}
            }
            attempts++;
        }
        log.error("Failed to click on element after retries: " + locator);
        throw new RuntimeException("Failed to click on element after retrying: " + locator);
    }


    // SendKeys method with explicit wait
    protected void sendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        log.info("Entered text '" + text + "' in element: " + locator);
    }

    // Get Text method
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // Check if an element is displayed
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            boolean displayed = element.isDisplayed();
            log.info("Element displayed: " + locator + " - " + displayed);
            return displayed;
        } catch (Exception e) {
            log.error("Element not displayed: " + locator);
            return false;
        }
    }
}
