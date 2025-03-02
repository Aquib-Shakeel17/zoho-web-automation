package com.zoho.pages;

import com.zoho.utils.WaitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeleteLeadPage extends BasePage {
    private static final Logger log = LogManager.getLogger(DeleteLeadPage.class);

    // Element locators
    private By moreOption3Dot = By.xpath("//button[@aria-label='More Options']");
    private By leadDeletedMessage = By.xpath("//div[contains(text(), 'Lead deleted successfully')]");

    // Popup elements
    private By popupText = By.xpath("//span[@class='alertSecondaryMsg']");
    private By deleteButtonPopup = By.xpath("//lyte-yield[contains(text(),'Delete')]");

    private By leadNameLocator = By.xpath("//span[@id='subvalue_LASTNAME']");  // XPath to get lead name

    public DeleteLeadPage(WebDriver driver) {
        super(driver);
    }

    // Clicks on the More Options button
    public void clickMoreOptionButton() {
        log.info("Waiting for More Options button to be clickable.");
        WaitUtil.waitForElementClickable(driver, moreOption3Dot);
        log.info("Clicking on More Options button.");
        click(moreOption3Dot);
    }

    // Clicks the Delete Lead button using a dynamic XPath based on the 'id' pattern
    public void clickDeleteLeadButton() {
        // Construct the dynamic XPath to locate the Delete button using the 'id' pattern
        String dynamicXPath = "//lyte-menu-item[contains(@id, 'drop_action_delete_')]";
        By deleteLeadButton = By.xpath(dynamicXPath);

        log.info("Waiting for Delete Lead button to be clickable.");
        WaitUtil.waitForElementClickable(driver, deleteLeadButton);

        log.info("Clicking on Delete Lead button.");
        click(deleteLeadButton);
    }

    // Verifies the popup text
    public boolean isPopupTextCorrect(String expectedText) {
        log.info("Verifying popup text.");
        String actualText = getTextFromPopup();
        return actualText.contains(expectedText);
    }

    // Get the popup text
    public String getTextFromPopup() {
        log.info("Retrieving text from the popup.");
        WebElement popupTextElement = WaitUtil.waitForElementVisible(driver, popupText);
        String popupTextValue = popupTextElement.getText();
        log.info("Popup text: " + popupTextValue);
        return popupTextValue;
    }

    // Clicks the Delete button on the popup
    public void clickDeleteButtonOnPopup() {
        log.info("Waiting for Delete button on popup to be clickable.");
        WaitUtil.waitForElementClickable(driver, deleteButtonPopup);
        log.info("Clicking on Delete button on popup.");
        click(deleteButtonPopup);
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

    // Retrieve the lead name before deletion
    public String getLeadNameBeforeDeletion() {
        log.info("Retrieving lead name before deletion.");
        WebElement leadNameElement = WaitUtil.waitForElementVisible(driver, leadNameLocator);
        String userName1 = leadNameElement.getText();
        log.info("Lead name before deletion: " + userName1);
        return userName1;
    }

    // Retrieve the lead name after deletion
    public String getLeadNameAfterDeletion() {
        log.info("Retrieving lead name after deletion.");
        WebElement leadNameElement = WaitUtil.waitForElementVisible(driver, leadNameLocator);
        String userName2 = leadNameElement.getText();
        log.info("Lead name after deletion: " + userName2);
        return userName2;
    }

}
