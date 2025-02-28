package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * HomePage represents the landing page after a successful login.
 */
public class HomePage extends BasePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);

    // Locator for the user profile name element
    private By userProfileName = By.xpath("//span[@id='show-uName']");

    // Constructor to initialize WebDriver and inherit BasePage methods
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Checks if the user profile name is displayed on the HomePage
    public boolean isUserProfileNameDisplayed() {
        log.info("Checking if user profile name is displayed.");
        return isElementDisplayed(userProfileName);
    }
}
