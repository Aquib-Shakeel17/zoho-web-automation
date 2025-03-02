package com.zoho.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BrowserFactory {
    private static final Logger log = LogManager.getLogger(BrowserFactory.class);

    // Static method to get WebDriver instance based on browser type
    public static WebDriver getDriver(String browser) {
        WebDriver driverInstance;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();  // Setup Firefox WebDriver
                driverInstance = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();  // Setup Edge WebDriver
                driverInstance = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();  // Setup Chrome WebDriver
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");  // Open Chrome maximized
                options.addArguments("--disable-notifications");  // Disable notifications
                driverInstance = new ChromeDriver(options);
                break;
        }
        driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        log.info("Initialized WebDriver for browser: " + browser);
        return driverInstance;
    }

    // Method to quit the WebDriver and clean up resources
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                log.info("Browser session closed successfully.");
            } catch (Exception e) {
                log.error("Error quitting the driver: " + e.getMessage());
            }
        }
    }
}