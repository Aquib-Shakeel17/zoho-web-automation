package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.zoho.utils.WaitUtil;

/**
 * HomePage represents the landing page after a successful login.
 */
public class HomePage extends BasePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);

    // Locators for HomePage elements
    private By userProfileName = By.xpath("//span[@id='show-uName']");
    private By leadsTab = By.xpath("//lyte-text[normalize-space()='Leads']");
    private By createLeadButton = By.xpath("//button[normalize-space()='Create Lead']");

    // Constructor to initialize WebDriver
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Method to check if the user profile name is displayed
    public boolean isUserProfileNameDisplayed() {
        log.info("Checking if user profile name is displayed.");
        WaitUtil.waitForElementVisible(driver, userProfileName);
        return isElementDisplayed(userProfileName);
    }

    // Method to navigate to the Create Lead page
    public LeadPage navigateToCreateLeadPage() {
        log.info("Navigating to Create Lead Page.");
        WaitUtil.waitForElementClickable(driver, leadsTab);
        click(leadsTab);
        WaitUtil.waitForElementClickable(driver, createLeadButton);
        click(createLeadButton);
        return new LeadPage(driver);
    }

    // Method to navigate to the Lead Page
    public LeadPage navigateToLeadPage() {
        log.info("Navigating to Lead Page.");
        WaitUtil.waitForElementClickable(driver, leadsTab);
        click(leadsTab);
        return new LeadPage(driver); // Return LeadPage object to interact with the lead page
    }
}
