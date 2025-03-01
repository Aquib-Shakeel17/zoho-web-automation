package com.zoho.pages;

import com.zoho.utils.WaitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EditLeadPage extends BasePage {
    private static final Logger log = LogManager.getLogger(EditLeadPage.class);

    // Make editLeadButton accessible for WaitUtil method

    public By companyText = By.xpath("//span[@id='subvalue_COMPANY']");
    public By mandatoryErrorForCompany = By.xpath("//span[@id='errorMsg_Crm_Leads_COMPANY']");
    public By companyEditField = By.xpath("//input[@id='inputId']");
    public By editLeadButton = By.xpath("//lyte-yield[normalize-space()='Edit']");
    private By emailField = By.id("Crm_Leads_EMAIL_LInput");
    private By saveButton = By.xpath("//lyte-button[@id='crm_create_savebutnLeads']//lyte-yield[1]");
    private By lastNameField = By.id("Crm_Leads_LASTNAME_LInput");
    private By lastNameError = By.xpath("//span[@id='errorMsg_Crm_Leads_LASTNAME']");
    private By emailError = By.xpath("//span[@id='errorMsg_Crm_Leads_EMAIL']");
    private By successMessage = By.xpath("//span[@class='success-msg']");

    public EditLeadPage(WebDriver driver) {
        super(driver);
    }

    // Method to capture the text inside the companyEditField
    public String getCompanyEditFieldText() {
        log.info("Waiting for company edit field to be visible.");
        WaitUtil.waitForElementVisible(driver, companyEditField); // Wait for the field to be visible
        log.info("Capturing text inside company edit field.");
        WebElement field = driver.findElement(companyEditField);
        return field.getAttribute("value"); // Captures the value of the field
    }

    // Method to get the text of the company name
    public String getCompanyText() {
        log.info("Waiting for company text to be visible.");
        WaitUtil.waitForElementVisible(driver, companyText); // Wait for the company text to be visible
        WebElement companyTextElement = driver.findElement(companyText); // Find the company text element
        return companyTextElement.getText(); // Return the text inside the company span
    }

    // Method to clear the existing text in companyEditField and click the save button
    public void clearCompanyEditFieldAndSave() {
        log.info("Waiting for company edit field to be visible.");
        WaitUtil.waitForElementVisible(driver, companyEditField); // Wait for the field to be visible
        WebElement companyField = driver.findElement(companyEditField);

        log.info("Clearing the existing text in company edit field.");
        companyField.clear(); // Clear existing text

        log.info("Waiting for Save button to be clickable.");
        WaitUtil.waitForElementClickable(driver, saveButton); // Wait for the Save button to be clickable

        log.info("Clicking on Save button.");
        click(saveButton); // Click the save button
    }

    public void clearCompanyEditFieldAndSave(String companyName) {
        log.info("Waiting for company edit field to be visible.");
        WaitUtil.waitForElementVisible(driver, companyEditField); // Wait for the field to be visible
        WebElement companyField = driver.findElement(companyEditField);

        log.info("Clearing the existing text in company edit field.");
        companyField.clear(); // Clear existing text

        log.info("Entering new company name: " + companyName);
        companyField.sendKeys(companyName); // Enter the new value

        log.info("Waiting for Save button to be clickable.");
        WaitUtil.waitForElementClickable(driver, saveButton); // Wait for the Save button to be clickable

        log.info("Clicking on Save button.");
        click(saveButton); // Click the save button
    }

    // Method to click the Edit button with waiting
    public void clickEditLeadButton() {
        log.info("Waiting for Edit Lead button to be clickable.");
        WaitUtil.waitForElementClickable(driver, editLeadButton); // Wait until the Edit button is clickable
        log.info("Clicking on Edit Lead button.");
        click(editLeadButton);
    }

    // Method to enter email with waiting
    public void enterEmail(String email) {
        log.info("Waiting for email field to be visible.");
        WaitUtil.waitForElementVisible(driver, emailField); // Wait until the email field is visible
        log.info("Entering email: " + email);
        sendKeys(emailField, email);
    }

    // Method to click on a lead by its name with waiting
    public void clickLeadByName(String leadName) {
        // Use contains() to find the lead by partial match
        String dynamicXPath = "//lyte-text[contains(normalize-space(),'" + leadName + "')]";
        By leadLocator = By.xpath(dynamicXPath);
        log.info("Waiting for lead with name containing: " + leadName + " to be clickable.");
        WaitUtil.waitForElementClickable(driver, leadLocator); // Wait until the lead is clickable
        log.info("Clicking on lead with name containing: " + leadName);
        click(leadLocator);
    }

    // New method to check if the Edit button is visible
    public boolean isEditButtonVisible() {
        try {
            log.info("Checking if Edit button is visible.");
            WebElement editButton = driver.findElement(editLeadButton);
            return editButton.isDisplayed();
        } catch (Exception e) {
            return false; // Return false if the Edit button is not found
        }
    }

    // Method to capture the error message for the company field
    public String getCompanyErrorMessage() {
        log.info("Waiting for company error message to be visible.");
        WaitUtil.waitForElementVisible(driver, mandatoryErrorForCompany); // Wait for the error message to be visible
        WebElement errorMessageElement = driver.findElement(mandatoryErrorForCompany); // Find the error message element
        return errorMessageElement.getText(); // Capture and return the error message text
    }
}
