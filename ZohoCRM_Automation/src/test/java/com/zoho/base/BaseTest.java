package com.zoho.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.zoho.config.AppConfig;
import com.zoho.utils.BrowserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTest {
    protected static ExtentReports extent; // Shared ExtentReports instance
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>(); // Thread-local for parallel execution
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver; // WebDriver instance for test execution

    @BeforeSuite
    public void setupReport() {
        // Generate a timestamped report file path
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timestamp + ".html";

        // Initialize ExtentReports with SparkReporter
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        log.info("Extent Reports initialized: " + reportPath);
    }

    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser, Method method) {
        // Launch the specified browser
        driver = BrowserFactory.getDriver(browser);
        log.info("Launching browser: " + browser);

        // Navigate to the base URL
        driver.get(AppConfig.BASE_URL);
        log.info("Navigating to Zoho CRM URL: " + AppConfig.BASE_URL);

        // Initialize ExtentTest for the current test method
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after each test
            log.info("Browser closed.");
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush(); // Ensure ExtentReports data is saved at the end of test execution
        log.info("Extent Reports flushed.");
    }
}
