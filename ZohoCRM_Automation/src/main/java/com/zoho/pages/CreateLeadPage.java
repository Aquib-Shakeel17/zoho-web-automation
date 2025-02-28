package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateLeadPage extends BasePage {
    private static final Logger log = LogManager.getLogger(CreateLeadPage.class);

    // Locators for Lead Form Fields
    private By firstNameField = By.id("Crm_Leads_FIRSTNAME_LInput");
    private By lastNameField = By.id("Crm_Leads_LASTNAME_LInput");
    private By companyField = By.xpath("//crm-create-fields[@id='Leads_fldRow_COMPANY']//input[1]");
    private By emailField = By.id("Crm_Leads_EMAIL_LInput");
    private By saveButton = By.xpath("//lyte-button[@id='crm_create_savebutnLeads']//lyte-yield[1]");
    private By cancelButton = By.xpath("//button[@id='crm_create_cancelbutn']//lyte-yield[contains(@yield-name,'text')][normalize-space()='Cancel']");

    // Locators for Validation Error Messages
    private By companyError = By.xpath("//span[@id='errorMsg_Crm_Leads_COMPANY']");
    private By lastNameError = By.xpath("//span[@id='errorMsg_Crm_Leads_LASTNAME']");
    private By emailError = By.xpath("//span[@id='errorMsg_Crm_Leads_EMAIL']");

    // Locator for UI Text Verification
    private By createLeadText = By.xpath("//label[normalize-space()='Create Lead']");
    private By createLeadButton = By.xpath("//button[normalize-space()='Create Lead']");

    public CreateLeadPage(WebDriver driver) {
        super(driver);
    }

    // Fill lead details
    public void enterLeadDetails(String firstName, String lastName, String company, String email) {
        log.info("Entering lead details: {} {}, Company: {}", firstName, lastName, company);
        sendKeys(firstNameField, firstName);
        sendKeys(lastNameField, lastName);
        sendKeys(companyField, company);
        sendKeys(emailField, email);
    }

    // Click Save
    // Click Save
    public void clickSave() {
        log.info("Clicking 'Save' button.");
        click(saveButton);
    }

    // Click Cancel
    public void clickCancel() {
        log.info("Clicking 'Cancel' button.");
        click(cancelButton);
    }

    // Get specific error messages
    public String getCompanyErrorMessage() {
        log.info("Fetching Company error message.");
        return getText(companyError);
    }

    public String getLastNameErrorMessage() {
        log.info("Fetching Last Name error message.");
        return getText(lastNameError);
    }

    public String getEmailErrorMessage() {
        log.info("Fetching Email error message.");
        return getText(emailError);
    }

    // Get "Create Lead" text
    public String getCreateLeadText() {
        log.info("Fetching 'Create Lead' text.");
        return getText(createLeadText);
    }

    // Click Create Lead
    public CreateLeadPage clickCreateLead() {
        log.info("Clicking on 'Create Lead' button.");
        click(createLeadButton);
        return this; // Return current instance instead of creating a new one
    }
}