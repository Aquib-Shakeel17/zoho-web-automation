package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.zoho.utils.WaitUtil;

public class LeadPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LeadPage.class);

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

    private By cancelText = By.xpath("//div[@class='crm-heading-font-size crmHeadingColor crm-font-bold fontSmooth']");

    public LeadPage(WebDriver driver) {
        super(driver);
    }

    // Method to fill lead details (First Name, Last Name, Company, Email)
    public void enterLeadDetails(String firstName, String lastName, String company, String email) {
        log.info("Entering lead details: FirstName: {}, LastName: {}, Company: {}, Email: {}", firstName, lastName, company, email);
        WaitUtil.waitForElementVisible(driver, firstNameField);
        sendKeys(firstNameField, firstName);

        WaitUtil.waitForElementVisible(driver, lastNameField);
        sendKeys(lastNameField, lastName);

        WaitUtil.waitForElementVisible(driver, companyField);
        sendKeys(companyField, company);

        WaitUtil.waitForElementVisible(driver, emailField);
        sendKeys(emailField, email);
    }

    // Method to click the Save button
    public void clickSave() {
        log.info("Clicking 'Save' button.");
        WaitUtil.waitForElementClickable(driver, saveButton);
        click(saveButton);
    }

    // Method to click the Cancel button
    public void clickCancel() {
        log.info("Clicking 'Cancel' button.");
        WaitUtil.waitForElementClickable(driver, cancelButton);
        click(cancelButton);
    }

    // Method to fetch and return the company error message
    public String getCompanyErrorMessage() {
        log.info("Fetching Company error message.");
        WaitUtil.waitForElementVisible(driver, companyError);
        return getText(companyError);
    }

    // Method to fetch and return the last name error message
    public String getLastNameErrorMessage() {
        log.info("Fetching Last Name error message.");
        WaitUtil.waitForElementVisible(driver, lastNameError);
        return getText(lastNameError);
    }

    // Method to fetch and return the email error message
    public String getEmailErrorMessage() {
        log.info("Fetching Email error message.");
        WaitUtil.waitForElementVisible(driver, emailError);
        return getText(emailError);
    }

    // Method to fetch and return the "Create Lead" text
    public String getCreateLeadText() {
        log.info("Fetching 'Create Lead' text.");
        WaitUtil.waitForElementVisible(driver, createLeadText);
        return getText(createLeadText);
    }


    // Method to check if the 'Cancel' text is displayed
    public boolean isCancelTextDisplayed() {
        log.info("Checking if 'Cancel' text is displayed.");
        WaitUtil.waitForElementVisible(driver, cancelText);
        return driver.findElement(cancelText).isDisplayed();
    }
}
