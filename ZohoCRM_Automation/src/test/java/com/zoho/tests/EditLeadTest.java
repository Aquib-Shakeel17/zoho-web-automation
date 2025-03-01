package com.zoho.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.zoho.base.BaseTest;
import com.zoho.pages.EditLeadPage;
import com.zoho.pages.FilterPage;
import com.zoho.pages.LeadPage;
import com.zoho.pages.HomePage;
import com.zoho.utils.JsonDataReader;
import com.zoho.utils.WaitUtil;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EditLeadTest extends BaseTest {
    private LeadPage leadPage;
    private EditLeadPage editLeadPage;
    private HomePage homePage;
    private FilterPage filterPage;
    private static final Logger log = LogManager.getLogger(EditLeadTest.class);
    private JsonDataReader jsonDataReader;

    @BeforeClass
    public void loadTestData() {
        jsonDataReader = new JsonDataReader("src/test/resources/testdata.json");
    }

    @Test(priority = 1)
    public void testApplyFilterAndEditLead() throws InterruptedException {
        test.get().log(Status.INFO, "Starting test: Apply Filter by City, Save it, and Edit Lead");

        homePage = new HomePage(getDriver());
        leadPage = homePage.navigateToLeadPage();
        filterPage = new FilterPage(getDriver());

        // Retrieve test data for filtering
        JsonNode testCase = jsonDataReader.getTestData("testFilterByCity");
        if (testCase == null) {
            throw new RuntimeException("Test data for 'testFilterByCity' not found in testdata.json");
        }

        String cityName = testCase.get("cityname").asText();
        test.get().log(Status.INFO, "Applying filter for city: " + cityName);

        // Apply the city filter and save
        filterPage.applyCityFilter(cityName);
        filterPage.saveFilterName();
        test.get().log(Status.INFO, "Successfully saved filter.");

        // Wait for leads to be visible after applying filter
        List<WebElement> leadElements = getDriver().findElements(By.xpath("//lyte-text[contains(normalize-space(), 'Test')]"));

        if (leadElements.isEmpty()) {
            throw new RuntimeException("No leads found after applying filter.");
        }

        // Click on the first available lead dynamically
        WebElement firstLead = leadElements.get(0);
        test.get().log(Status.INFO, "Clicking on first lead: " + firstLead.getText());

        // Handle modal if present
        By modalLocator = By.xpath("//lyte-modal-freeze");
        if (!getDriver().findElements(modalLocator).isEmpty()) {
            log.info("Modal detected. Attempting to close.");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].remove();",
                    getDriver().findElement(modalLocator));
        }

        // Ensure element is clickable before clicking
        By firstLeadLocator = By.xpath("//lyte-text[contains(normalize-space(), '" + firstLead.getText() + "')]");
        WebElement firstLeadElement = WaitUtil.waitForElementClickable(getDriver(), firstLeadLocator);

        // Scroll into view before clicking
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", firstLeadElement);

        // Click using JavaScript if necessary
        try {
            firstLeadElement.click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted, retrying with JavaScript.");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", firstLeadElement);
        }

        // Retrieve test data for editing lead
        JsonNode editTestCase = jsonDataReader.getTestData("testCreateLeadWithValidData");
        if (editTestCase == null) {
            throw new RuntimeException("Test data for 'testEditLeadDetails' not found in testdata.json");
        }

        String newLastName = editTestCase.get("lastName").asText();
        String newCompany = editTestCase.get("company").asText();
        String newEmail = editTestCase.get("email").asText();

        test.get().log(Status.INFO, "Navigated to Edit Lead page.");
        editLeadPage = new EditLeadPage(getDriver());
        System.out.println("Pass");
    }
}
