package com.zoho.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.concurrent.TimeUnit;

public class BrowserFactory {

    // ThreadLocal ensures WebDriver instances are thread-safe in parallel execution
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initializes and returns a WebDriver instance based on the provided browser name.
     * @param browser The name of the browser to launch.
     * @return WebDriver instance.
     */
    public static WebDriver getDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver;
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized"); // Ensures Chrome starts maximized
                    driver = new ChromeDriver(options);
                    break;
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Set implicit wait
            driverThreadLocal.set(driver); // Store driver instance in ThreadLocal
        }
        return driverThreadLocal.get();
    }

    /**
     * Quits the WebDriver instance and removes it from ThreadLocal storage.
     */
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit(); // Close browser
            driverThreadLocal.remove(); // Prevent memory leaks
        }
    }
}
