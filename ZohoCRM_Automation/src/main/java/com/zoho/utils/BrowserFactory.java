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
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static synchronized WebDriver getDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driverInstance;
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverInstance = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driverInstance = new EdgeDriver();
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized");
                    options.addArguments("--disable-notifications");
                    driverInstance = new ChromeDriver(options);
                    break;
            }
            driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            log.info("Initialized WebDriver for browser: " + browser);
            driverThreadLocal.set(driverInstance);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            try {
                driverThreadLocal.get().quit();
                log.info("Browser session closed successfully.");
            } catch (Exception e) {
                log.error("Error quitting the driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}

