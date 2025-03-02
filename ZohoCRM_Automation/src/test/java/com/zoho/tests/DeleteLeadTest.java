package com.zoho.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.zoho.base.BaseTest;
import com.zoho.pages.DeleteLeadPage;
import com.zoho.utils.JsonDataReader;
import com.aventstack.extentreports.Status;
import com.zoho.utils.WaitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteLeadTest extends BaseTest {
    private DeleteLeadPage deleteLeadPage;
    private JsonDataReader jsonDataReader;
    private static final Logger log = LogManager.getLogger(DeleteLeadTest.class);

    @BeforeClass
    public void loadTestData() {
        jsonDataReader = new JsonDataReader("src/test/resources/testdata.json");
    }

    @Test(priority = 1)
    public void testDeleteLead() {
        test.get().log(Status.INFO, "Starting test: testDeleteLead");

        // Retrieve lead name dynamically from JSON for this test
        JsonNode testData = jsonDataReader.getTestData("testClickLeadByName");
        String leadName = testData.get("leadName").asText();

        // Initialize DeleteLeadPage and perform actions
        deleteLeadPage = new DeleteLeadPage(getDriver());
        log.info("Capturing lead name before deletion: " + leadName);

        deleteLeadPage.clickLeadByName(leadName);

        // Capture the lead name before deletion
        String leadNameBeforeDeletion = deleteLeadPage.getLeadNameBeforeDeletion();
        log.info("Lead name before deletion: " + leadNameBeforeDeletion);

        deleteLeadPage.clickMoreOptionButton();
        deleteLeadPage.clickDeleteLeadButton();

        // Verify popup text
        String expectedPopupText = "Any associated Activities, Visits, Drafts will also be Deleted";
        String actualPopupText = deleteLeadPage.getTextFromPopup();

        try {
            Assert.assertTrue(actualPopupText.contains(expectedPopupText), "Popup text verification failed. Expected: '"
                    + expectedPopupText + "' but found: '" + actualPopupText + "'");
            test.get().log(Status.PASS, "Popup text verified successfully: " + actualPopupText);
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Popup text verification failed: " + e.getMessage());
            throw e;
        }


        // Click the Delete button on the popup
        deleteLeadPage.clickDeleteButtonOnPopup();

        WaitUtil.sleepFor(3000); // Wait for the deletion to complete

        // After deletion, capture the lead name again
        String leadNameAfterDeletion = deleteLeadPage.getLeadNameAfterDeletion();
        log.info("Lead name after deletion: " + leadNameAfterDeletion);

        // Validate that the lead name before and after deletion are different
        try {
            Assert.assertNotEquals(leadNameBeforeDeletion, leadNameAfterDeletion, "Lead was not deleted successfully.");
            test.get().log(Status.PASS, "Lead was deleted successfully.");
        } catch (AssertionError e) {
            test.get().log(Status.FAIL, "Lead deletion validation failed: " + e.getMessage());
            throw e;
        }
    }
}
