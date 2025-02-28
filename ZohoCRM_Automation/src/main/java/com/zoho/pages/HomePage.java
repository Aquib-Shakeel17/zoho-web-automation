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

    // Locators
    private By userProfileName = By.xpath("//span[@id='show-uName']");
    private By leadsTab = By.xpath("//lyte-text[normalize-space()='Leads']");
    private By createLeadButton = By.xpath("//button[normalize-space()='Create Lead']"); // Add Create Lead Button

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Checks if the user profile name is displayed on the HomePage
    public boolean isUserProfileNameDisplayed() {
        log.info("Checking if user profile name is displayed.");
        return isElementDisplayed(userProfileName);
    }

    // Navigate directly to Create Lead Page
    public CreateLeadPage navigateToCreateLeadPage() {
        log.info("Navigating to Create Lead Page.");
        click(leadsTab);
        click(createLeadButton); // Directly clicking on create lead
        return new CreateLeadPage(driver);
    }
}
