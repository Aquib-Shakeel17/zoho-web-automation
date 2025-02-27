package com.zoho.tests;

import com.zoho.base.BaseTest;
import com.zoho.config.Constants;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void testLaunchGoogle() {
        log.info("Starting test: testLaunchGoogle");

        driver.get(Constants.BASE_URL); // Fetch URL from config.properties

        log.info("Opened " + Constants.BASE_URL + " successfully.");

        // Initialize ExtentTest if it's null
        if (test.get() == null) {
            test.set(extent.createTest("testLaunchGoogle"));
        }

        test.get().pass("Test Passed: " + Constants.BASE_URL + " opened successfully.");

    }
}
