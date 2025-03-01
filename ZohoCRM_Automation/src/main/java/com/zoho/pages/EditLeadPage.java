package com.zoho.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditLeadPage extends BasePage {
    private static final Logger log = LogManager.getLogger(EditLeadPage.class);

    private String leadByFirstNameTemplate = "//lyte-text[starts-with(normalize-space(),'%s')]";
    private By editLeadTitle = By.xpath("//lyte-yield[normalize-space()='Edit']");
    private By emailField = By.id("Crm_Leads_EMAIL_LInput");
    private By saveButton = By.xpath("//button[@id='saveLeadButton']");
    private By lastNameField = By.id("Crm_Leads_LASTNAME_LInput");
    private By lastNameError = By.xpath("//span[@id='errorMsg_Crm_Leads_LASTNAME']");
    private By emailError = By.xpath("//span[@id='errorMsg_Crm_Leads_EMAIL']");
    private By successMessage = By.xpath("//span[@class='success-msg']");

    public EditLeadPage(WebDriver driver) {
        super(driver);
    }

    public String getEditLeadTitle() { // Returns the Edit Lead page title
        return getText(editLeadTitle);
    }

    public void enterEmail(String email) { // Enters email into the input field
        log.info("Entering email: " + email);
        sendKeys(emailField, email);
    }

    public String getEmailErrorMessage() { // Retrieves the email error message
        return getText(emailError);
    }

    public void clickSave() { // Clicks the Save button
        log.info("Clicking Save button.");
        click(saveButton);
    }

    public String getSuccessMessage() { // Retrieves the success message
        return getText(successMessage);
    }

    public void enterLastName(String lastName) { // Enters last name into the field
        sendKeys(lastNameField, lastName);
    }

    public String getLastNameErrorMessage() { // Retrieves the last name error message
        return getText(lastNameError);
    }

    public void updateLeadDetails(String firstName, String lastName, String company, String email) { // Updates lead details
        sendKeys(By.id("Crm_Leads_FIRSTNAME_LInput"), firstName);
        sendKeys(By.id("Crm_Leads_LASTNAME_LInput"), lastName);
        sendKeys(By.id("Crm_Leads_COMPANY_LInput"), company);
        sendKeys(By.id("Crm_Leads_EMAIL_LInput"), email);
    }

    public void clickOnLeadByFirstName(String leadKeyword) {
        String dynamicXPath = "//lyte-text[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + leadKeyword.toLowerCase() + "')]";
        By leadElement = By.xpath(dynamicXPath);

        log.info("Clicking on lead containing: " + leadKeyword);
        click(leadElement);

    }
}