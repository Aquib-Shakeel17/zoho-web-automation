package com.zoho.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.zoho.base.BaseTest;
import com.zoho.pages.CreateLeadPage;
import com.zoho.pages.HomePage;
import com.zoho.utils.JsonDataReader;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateLeadTest extends BaseTest {
    private CreateLeadPage createLeadPage;
    private HomePage homePage;
    private static final Logger log = LogManager.getLogger(CreateLeadTest.class);
    private JsonDataReader jsonDataReader;

    @BeforeClass
    public void loadTestData() {
        jsonDataReader = new JsonDataReader("src/test/resources/testdata.json");
    }

    @Test(priority = 1)
    public void testValidateCreateLeadText() {
        test.get().log(Status.INFO, "Starting test: Validate Create Lead Text");

        homePage = new HomePage(getDriver());
        createLeadPage = homePage.navigateToCreateLeadPage(); // Directly navigating

        String createLeadText = createLeadPage.getCreateLeadText();
        test.get().log(Status.INFO, "Fetched Create Lead page text: " + createLeadText);

        try {
            Assert.assertEquals(createLeadText, "Create Lead", "Create Lead text is incorrect.");
            test.get().log(Status.PASS, "Create Lead text is displayed correctly.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Create Lead text validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void testCreateLeadWithMissingMandatoryFields() {
        test.get().log(Status.INFO, "Starting test: Create Lead with Missing Mandatory Fields");

        homePage = new HomePage(getDriver());
        createLeadPage = homePage.navigateToCreateLeadPage();

        createLeadPage.clickSave();
        test.get().log(Status.INFO, "Clicked Save without entering mandatory fields.");

        String companyError = createLeadPage.getCompanyErrorMessage();
        String lastNameError = createLeadPage.getLastNameErrorMessage();

        try {
            Assert.assertEquals(companyError, "Company cannot be empty.", "Company error message is incorrect.");
            test.get().log(Status.PASS, "Company validation error displayed correctly.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Company validation failed: " + e.getMessage());
            throw e;
        }

        try {
            Assert.assertEquals(lastNameError, "Last Name cannot be empty.", "Last Name error message is incorrect.");
            test.get().log(Status.PASS, "Last Name validation error displayed correctly.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Last Name validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    public void testCreateLeadWithInvalidEmailFormat() {
        test.get().log(Status.INFO, "Starting test: Create Lead with Invalid Email Format");

        JsonNode testData = jsonDataReader.getTestData("testCreateLeadWithInvalidEmailFormat");

        homePage = new HomePage(getDriver());
        createLeadPage = homePage.navigateToCreateLeadPage();

        createLeadPage.enterLeadDetails(
                testData.get("firstName").asText(),
                testData.get("lastName").asText(),
                testData.get("company").asText(),
                testData.get("email").asText()
        );

        test.get().log(Status.INFO, "Entered lead details with invalid email: " + testData.get("email").asText());

        createLeadPage.clickSave();
        test.get().log(Status.INFO, "Clicked Save after entering invalid email.");

        String emailError = createLeadPage.getEmailErrorMessage();

        try {
            Assert.assertEquals(emailError, "Please enter a valid Email.", "Email format error message is incorrect.");
            test.get().log(Status.PASS, "Email validation error displayed correctly.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Email validation failed: " + e.getMessage());
            throw e;
        }
    }
}
