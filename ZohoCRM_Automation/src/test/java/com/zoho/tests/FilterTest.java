package com.zoho.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.zoho.base.BaseTest;
import com.zoho.pages.FilterPage;
import com.zoho.pages.HomePage;
import com.zoho.pages.LeadPage;
import com.zoho.utils.JsonDataReader;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FilterTest extends BaseTest {
    private FilterPage filterPage;
    private HomePage homePage;
    private LeadPage leadPage;
    private JsonDataReader jsonDataReader;
    private static final Logger log = LogManager.getLogger(FilterTest.class);

    @BeforeClass
    public void loadTestData() {
        jsonDataReader = new JsonDataReader("src/test/resources/testdata.json");
    }

    @Test(priority = 1)
    public void testFilterByCityAndSave() throws InterruptedException {
        test.get().log(Status.INFO, "Starting test: Apply Filter by City and Save it");

        homePage = new HomePage(getDriver());
        leadPage = homePage.navigateToLeadPage();
        filterPage = new FilterPage(getDriver());

        // Corrected: Get the test case directly
        JsonNode testCase = jsonDataReader.getTestData("testFilterByCity");

        if (testCase == null) {
            throw new RuntimeException("Test data for 'testFilterByCity' not found in testdata.json");
        }

        String cityName = testCase.get("cityname").asText();
        test.get().log(Status.INFO, "Applying filter for city: " + cityName);

        // Apply the city filter
        filterPage.applyCompanyFilter(cityName);

        // Save filter with a random name
        filterPage.saveFilterName();

        test.get().log(Status.INFO, "Successfully saved filter with a random name.");

        filterPage.isFilterPresent("randomFilterName");
    }


    @Test(priority = 2)
    public void testApplyFilterWithoutInput() {
        test.get().log(Status.INFO, "Starting test: Apply Filter without Input and Verify Alert");

        // Navigate to the Leads Page
        homePage = new HomePage(getDriver());
        leadPage = homePage.navigateToLeadPage();
        filterPage = new FilterPage(getDriver());

        test.get().log(Status.INFO, "Navigated to Leads Page.");

        // Retrieve test data
        JsonNode testCase = jsonDataReader.getTestData("testFilterByCity");

        if (testCase == null) {
            throw new RuntimeException("Test data for 'testFilterByCity' not found in testdata.json");
        }

        test.get().log(Status.INFO, "Test data loaded successfully. Skipping input value.");

        // Select the City filter checkbox (but do not enter any value)
        filterPage.selectCityFilter();
        test.get().log(Status.INFO, "Selected the City filter checkbox.");

        // Click Apply Filter without entering any input
        filterPage.clickApplyFilterWithoutInput();
        test.get().log(Status.INFO, "Clicked on Apply Filter button without entering any input.");

        // Verify that an alert message appears
        String alertText = filterPage.getAlertMessage();
        test.get().log(Status.INFO, "Captured alert message: " + alertText);

        // Assert that the alert is not empty
        Assert.assertFalse(alertText.isEmpty(), "Expected an alert message but none appeared!");

        test.get().log(Status.PASS, "Verified alert message appears as expected.");
    }

}
