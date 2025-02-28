package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.zoho.config.AppConfig;

/**
 * LoginPage represents the login screen where users can authenticate.
 */
public class LoginPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // Locators for login elements
    private By signInLink = By.xpath("//a[@class='zgh-login'][normalize-space()='Sign In']"); // "Sign In" link
    private By emailAddress = By.id("login_id"); // Email input field
    private By password = By.xpath("//input[@id='password']"); // Password input field
    private By signInButton = By.xpath("//button[@id='nextbtn']//span[contains(text(),'Sign in')]"); // "Sign In" button
    private By nextButton = By.xpath("//span[normalize-space()='Next']"); // "Next" button after entering email

    // Constructor to initialize WebDriver and inherit BasePage methods
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Performs login using credentials from AppConfig
    public void signIn() {
        log.info("Navigating to login page and signing in.");
        click(signInLink); // Click on "Sign In" link
        sendKeys(emailAddress, AppConfig.LOGIN_EMAIL); // Enter email
        click(nextButton); // Click "Next" button
        sendKeys(password, AppConfig.LOGIN_PASSWORD); // Enter password
        click(signInButton); // Click "Sign In" button to log in
    }
}
