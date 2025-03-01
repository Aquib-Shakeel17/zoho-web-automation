package com.zoho.utils;

import com.zoho.pages.HomePage;
import com.zoho.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class SessionManager {
    private static final Logger log = LogManager.getLogger(SessionManager.class);

    public static void ensureUserIsLoggedIn(WebDriver driver) {
        log.info("Checking if user session is active...");

        String currentUrl = driver.getCurrentUrl();

        // Check if user is already logged in
        if (currentUrl.contains("home")) {
            log.info("User is already logged in. No need to log in again.");
            return;
        }

        log.info("No active session found or user is on the login page. Proceeding with login...");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.signIn();
    }
}
