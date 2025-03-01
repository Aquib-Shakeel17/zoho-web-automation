package com.zoho.pages;

import com.zoho.utils.WaitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EditLeadPage extends BasePage {
    private static final Logger log = LogManager.getLogger(EditLeadPage.class);

    // Element locators
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

    // Captures the text inside the company edit field
    public String getCompanyEditFieldText() {
        log.info("Waiting for company edit field to be visible.");
        WaitUtil.waitForElementVisible(driver, companyEditField);
        log.info("Capturing text inside company edit field.");
        WebElement field = driver.findElement(companyEditField);
        return field.getAttribute("value"); // Captures the value of the field
    }

    // Gets the text of the company name
    public String getCompanyText() {
        log.info("Waiting for company text to be visible.");
        WaitUtil.waitForElementVisible(driver, companyText);
        WebElement companyTextElement = driver.findElement(companyText);
        return companyTextElement.getText();
    }

    // Clears the existing text in companyEditField and clicks the save button
    public void clearCompanyEditFieldAndSave() {
        log.info("Waiting for company edit field to be visible.");
        WaitUtil.waitForElementVisible(driver, companyEditField);
        WebElement companyField = driver.findElement(companyEditField);

        log.info("Clearing the existing text in company edit field.");
        companyField.clear();

        log.info("Waiting for Save button to be clickable.");
        WaitUtil.waitForElementClickable(driver, saveButton);

        log.info("Clicking on Save button.");
        click(saveButton);
    }

    // Clears the company edit field and enters a new company name before saving
    public void clearCompanyEditFieldAndSave(String companyName) {
        log.info("Waiting for company edit field to be visible.");
        WaitUtil.waitForElementVisible(driver, companyEditField);
        WebElement companyField = driver.findElement(companyEditField);

        log.info("Clearing the existing text in company edit field.");
        companyField.clear();

        log.info("Entering new company name: " + companyName);
        companyField.sendKeys(companyName);

        log.info("Waiting for Save button to be clickable.");
        WaitUtil.waitForElementClickable(driver, saveButton);

        log.info("Clicking on Save button.");
        click(saveButton);
    }

    // Clicks the Edit button after ensuring it is clickable
    public void clickEditLeadButton() {
        log.info("Waiting for Edit Lead button to be clickable.");
        WaitUtil.waitForElementClickable(driver, editLeadButton);
        log.info("Clicking on Edit Lead button.");
        click(editLeadButton);
    }

    // Clicks on a lead by its name, allowing partial matches
    public void clickLeadByName(String leadName) {
        String dynamicXPath = "//lyte-text[contains(normalize-space(),'" + leadName + "')]"; // Dynamic XPath for partial text match
        By leadLocator = By.xpath(dynamicXPath);

        log.info("Waiting for lead with name containing: " + leadName + " to be clickable.");
        WaitUtil.waitForElementClickable(driver, leadLocator);
        log.info("Clicking on lead with name containing: " + leadName);
        click(leadLocator);
    }

    // Checks if the Edit button is visible
    public boolean isEditButtonVisible() {
        try {
            log.info("Checking if Edit button is visible.");
            WebElement editButton = driver.findElement(editLeadButton);
            return editButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Captures the error message for the company field
    public String getCompanyErrorMessage() {
        log.info("Waiting for company error message to be visible.");
        WaitUtil.waitForElementVisible(driver, mandatoryErrorForCompany);
        WebElement errorMessageElement = driver.findElement(mandatoryErrorForCompany);
        return errorMessageElement.getText();
    }
}
