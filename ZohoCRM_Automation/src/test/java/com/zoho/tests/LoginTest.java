package com.zoho.tests;

import com.zoho.base.BaseTest;
import com.zoho.pages.HomePage;
import com.zoho.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @BeforeMethod
    public void setupTest() {
        log.info("Initializing LoginPage and HomePage objects.");
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    public void testLogin() {
        log.info("Starting Login Test");
        test.get().info("Starting Login Test");

        // Perform login
        test.get().info("Navigating to login page and entering credentials");
        loginPage.signIn();

        // Verify login by checking if the URL contains "Home"
        String currentUrl = driver.getCurrentUrl();
        boolean isLoginSuccessful = currentUrl.contains("Home");

        if (isLoginSuccessful) {
            test.get().pass("URL verification passed. Login successful.");
        } else {
            test.get().fail("Login failed! URL does not contain 'Home'.");
        }
        Assert.assertTrue(isLoginSuccessful, "Login failed! URL does not contain 'Home'.");

        // Verify that the user profile name is displayed after login
        boolean isProfileVisible = homePage.isUserProfileNameDisplayed();

        if (isProfileVisible) {
            test.get().pass("User profile name is displayed.");
        } else {
            test.get().fail("User profile name is NOT visible after login.");
        }
        Assert.assertTrue(isProfileVisible, "User profile name is not visible after login.");

        log.info("Login successful!");
        test.get().pass("Login Test Passed ✅");
    }
}
