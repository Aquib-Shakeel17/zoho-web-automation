package com.zoho.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.zoho.base.BaseTest;
import com.zoho.pages.EditLeadPage;
import com.zoho.utils.JsonDataReader;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EditLeadTest extends BaseTest {
    private EditLeadPage editLeadPage;
    private JsonDataReader jsonDataReader;
    private static final Logger log = LogManager.getLogger(EditLeadTest.class);

    @BeforeClass
    public void loadTestData() {
        jsonDataReader = new JsonDataReader("src/test/resources/testdata.json");
    }

    @Test(priority = 1)
    public void testClickLeadByName() {
        test.get().log(Status.INFO, "Starting test: testClickLeadByName");

        // Get test data from JSON
        JsonNode testData = jsonDataReader.getTestData("testClickLeadByName");
        String leadName = testData.get("leadName").asText(); // Getting lead name dynamically from JSON

        // Initialize EditLeadPage
        editLeadPage = new EditLeadPage(getDriver());

        // Click the lead with the dynamic name
        log.info("Clicking lead by name: " + leadName);
        editLeadPage.clickLeadByName(leadName);

        // Validate if Edit button is shown
        boolean isEditButtonVisible = editLeadPage.isEditButtonVisible();
        try {
            Assert.assertTrue(isEditButtonVisible, "Edit button is not visible after clicking the lead");
            test.get().log(Status.PASS, "Edit button is visible after clicking the lead.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Edit button validation failed: " + e.getMessage());
            throw e;
        }

        editLeadPage.clickEditLeadButton();

        // Capture the text inside the company field
        String companyText = editLeadPage.getCompanyEditFieldText();
        log.info("Captured company field text: " + companyText);
        test.get().log(Status.INFO, "Captured company field text: " + companyText);

        // Clear the company field and try saving
        editLeadPage.clearCompanyEditFieldAndSave();

        // Validate that the error message is displayed
        String companyErrorMessage = editLeadPage.getCompanyErrorMessage();
        try {
            Assert.assertEquals(companyErrorMessage, "Company cannot be empty.", "Error message is incorrect.");
            test.get().log(Status.PASS, "Error message displayed correctly: " + companyErrorMessage);
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Error message validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void testClearCompanyAndEnterNewName() {
        test.get().log(Status.INFO, "Starting test: testClearCompanyAndEnterNewName");

        // Get test data from JSON for clicking the lead
        JsonNode testDataClickLead = jsonDataReader.getTestData("testClickLeadByName");
        String leadName = testDataClickLead.get("leadName").asText(); // Getting lead name dynamically from JSON

        // Initialize EditLeadPage
        editLeadPage = new EditLeadPage(getDriver());

        // Click the lead with the dynamic name
        log.info("Clicking lead by name: " + leadName);
        editLeadPage.clickLeadByName(leadName);

        // Validate if Edit button is shown
        boolean isEditButtonVisible = editLeadPage.isEditButtonVisible();
        try {
            Assert.assertTrue(isEditButtonVisible, "Edit button is not visible after clicking the lead");
            test.get().log(Status.PASS, "Edit button is visible after clicking the lead.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Edit button validation failed: " + e.getMessage());
            throw e;
        }

        editLeadPage.clickEditLeadButton();

        // Fetch the test data for clearing company and entering new name
        JsonNode testDataClearCompany = jsonDataReader.getTestData("testClearCompanyAndEnterNewName");
        String companyName = testDataClearCompany.get("company").asText();

        // Clear the company field and enter the new company name
        editLeadPage.clearCompanyEditFieldAndSave(companyName);

        // Verify that the company field is updated with the new value
        String companyFieldText = editLeadPage.getCompanyEditFieldText();
        Assert.assertEquals(companyFieldText, companyName, "Company name is not updated correctly.");

        test.get().log(Status.PASS, "Company name updated successfully to: " + companyName);

        // Validate the updated company name text
        String updatedCompanyText = editLeadPage.getCompanyText();  // Get the updated company text from the span
        try {
            Assert.assertEquals(updatedCompanyText, "Testing", "Company name is not updated correctly.");
            test.get().log(Status.PASS, "Company name updated successfully to: " + updatedCompanyText);
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Company name update validation failed: " + e.getMessage());
            throw e;
        }
    }
}
