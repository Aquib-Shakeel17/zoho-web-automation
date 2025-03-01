package com.zoho.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.zoho.base.BaseTest;
import com.zoho.pages.LeadPage;
import com.zoho.pages.HomePage;
import com.zoho.utils.JsonDataReader;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class CreateLeadTest extends BaseTest {
    private LeadPage leadPage;
    private HomePage homePage;
    private static final Logger log = LogManager.getLogger(CreateLeadTest.class);
    // Store lead details for validation
    private static final Map<String, String> createdLeadDetails = new HashMap<>();
    private JsonDataReader jsonDataReader;

    @BeforeClass
    public void loadTestData() {
        jsonDataReader = new JsonDataReader("src/test/resources/testdata.json");
    }


    @Test(priority = 1)
    public void testValidateCreateLeadText() {
        test.get().log(Status.INFO, "Starting test: Validate Create Lead Text");

        homePage = new HomePage(getDriver());
        leadPage = homePage.navigateToCreateLeadPage(); // Directly navigating

        String createLeadText = leadPage.getCreateLeadText();
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
        leadPage = homePage.navigateToCreateLeadPage();

        leadPage.clickSave();
        test.get().log(Status.INFO, "Clicked Save without entering mandatory fields.");

        String companyError = leadPage.getCompanyErrorMessage();
        String lastNameError = leadPage.getLastNameErrorMessage();

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
        leadPage = homePage.navigateToCreateLeadPage();

        leadPage.enterLeadDetails(
                testData.get("firstName").asText(),
                testData.get("lastName").asText(),
                testData.get("company").asText(),
                testData.get("email").asText()
        );

        test.get().log(Status.INFO, "Entered lead details with invalid email: " + testData.get("email").asText());

        leadPage.clickSave();
        test.get().log(Status.INFO, "Clicked Save after entering invalid email.");

        String emailError = leadPage.getEmailErrorMessage();

        try {
            Assert.assertEquals(emailError, "Please enter a valid Email.", "Email format error message is incorrect.");
            test.get().log(Status.PASS, "Email validation error displayed correctly.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Email validation failed: " + e.getMessage());
            throw e;
        }
    }


// ✅ **Step 4: Valid Lead Creation**
    @Test(priority = 4)
    public void testCreateLeadWithValidData() {
        test.get().log(Status.INFO, "Starting test: Create Lead with Valid Data");

        // Fetch dynamically generated test data
        JsonNode testData = jsonDataReader.getTestData("testCreateLeadWithValidData");

        // Extract test data values
        String firstName = testData.get("firstName").asText();
        String lastName = testData.get("lastName").asText();
        String company = testData.get("company").asText();
        String email = testData.get("email").asText();

        // Store details for validation after lead creation
        createdLeadDetails.put("firstName", firstName);
        createdLeadDetails.put("lastName", lastName);

        // Navigate to the 'Create Lead' page
        homePage = new HomePage(getDriver());  // Using same driver instance
        leadPage = homePage.navigateToCreateLeadPage();
        test.get().log(Status.INFO, "Navigated to Create Lead page");

        // Enter lead details and submit
        leadPage.enterLeadDetails(firstName, lastName, company, email);
        test.get().log(Status.INFO, "Entered lead details: " + firstName + " " + lastName + ", " + company + ", " + email);

        leadPage.clickSave();
        test.get().log(Status.INFO, "Clicked Save to create the lead");

        // ✅ **Inline Validation: Verify if the lead is created successfully**
        WebDriver driver = getDriver(); // Ensure we get the same driver instance
        String expectedLeadName = firstName + " " + lastName;

        // Dynamic XPath to locate the created lead
        String leadXpath = "//span[contains(text(),'" + expectedLeadName + "')]";

        // **Use the same driver instance without reassigning it**
        boolean isLeadPresent = getDriver().findElements(By.xpath(leadXpath)).size() > 0;

        try {
            Assert.assertTrue(isLeadPresent, "Lead was not found on the next page.");
            test.get().log(Status.PASS, "Lead created and validated successfully: " + expectedLeadName);
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Lead validation failed: " + e.getMessage());
            throw e;
        }
    }

    // ✅ **Step 5: Cancel Lead**
    @Test(priority = 5)
    public void testCancelLeadCreation() {
        test.get().log(Status.INFO, "Starting test: Cancel Lead Creation");

        // Retrieve randomized test data
        JsonNode testData = jsonDataReader.getTestData("testCreateLeadWithValidData");

        // Extract randomized values
        String firstName = testData.get("firstName").asText();
        String lastName = testData.get("lastName").asText();
        String company = testData.get("company").asText();
        String email = testData.get("email").asText();

        test.get().log(Status.INFO, "Generated Test Data - First Name: " + firstName + ", Last Name: " + lastName +
                ", Company: " + company + ", Email: " + email);

        // Navigate to the 'Create Lead' page
        homePage = new HomePage(getDriver());  // Using same driver instance
        leadPage = homePage.navigateToCreateLeadPage();

        // Enter lead details
        leadPage.enterLeadDetails(firstName, lastName, company, email);
        test.get().log(Status.INFO, "Entered lead details.");

        // Click Cancel instead of Save
        leadPage.clickCancel();
        test.get().log(Status.INFO, "Clicked Cancel - Lead should not be created.");

        // **Verification: Ensure we are back on the same page**
        boolean isCreateTextVisible = leadPage.isCancelTextDisplayed();
        Assert.assertTrue(isCreateTextVisible, "User was redirected discard pop-up.");
        test.get().log(Status.PASS, "Verified redirection back to Cancel Pop-up.");
    }

}
