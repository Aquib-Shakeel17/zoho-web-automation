package com.zoho.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.zoho.config.AppConfig;
import com.zoho.utils.WaitUtil; // Importing WaitUtil for better synchronization

/**
 * LoginPage represents the login screen where users can authenticate.
 */
public class LoginPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // Locators for login elements
    private By signInLink = By.xpath("//a[@class='zgh-login'][normalize-space()='Sign In']");
    private By emailAddress = By.id("login_id");
    private By password = By.xpath("//input[@id='password']");
    private By signInButton = By.xpath("//button[@id='nextbtn']//span[contains(text(),'Sign in')]");
    private By nextButton = By.xpath("//span[normalize-space()='Next']");

    // Constructor to initialize WebDriver and inherit BasePage methods
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Performs login using credentials from AppConfig
    public void signIn() {
        log.info("Navigating to login page and signing in.");
        click(signInLink);
        WaitUtil.waitForElementVisible(driver, emailAddress);
        sendKeys(emailAddress, AppConfig.LOGIN_EMAIL);
        click(nextButton);
        WaitUtil.waitForElementVisible(driver, password);
        sendKeys(password, AppConfig.LOGIN_PASSWORD);
        click(signInButton);
    }
}
