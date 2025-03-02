package com.zoho.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.zoho.config.AppConfig;
import com.zoho.utils.BrowserFactory;
import com.zoho.utils.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTest {
    // Extent Reports for logging test execution
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Logger for logging events
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    // WebDriver instance
    protected WebDriver driver;

    /**
     * Initializes the Extent Reports once before the entire test suite starts.
     */
    @BeforeSuite
    public void setupSuite() {
        log.info("Initializing Extent Reports for test suite...");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        log.info("Extent Reports initialized at: " + reportPath);
    }

    /**
     * Opens the browser before each test method.
     */
    @BeforeMethod
    @Parameters("browser")  // Pass the browser parameter from the TestNG XML
    public void setupMethod(@Optional("chrome") String browser) {
        log.info("Starting new browser instance for test...");
        driver = BrowserFactory.getDriver(browser);
        driver.manage().window().maximize();
        driver.get(AppConfig.BASE_URL);
        log.info("Navigated to: " + AppConfig.BASE_URL);

        // Ensure user is logged in before test execution
        SessionManager.ensureUserIsLoggedIn(driver);
    }

    /**
     * Runs before each test method to create an Extent Report entry.
     */
    @BeforeMethod
    public void setupTest(Method method) {
        log.info("Starting test: " + method.getName());
        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    /**
     * Returns the WebDriver instance for the current method.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Runs after each test method to close the browser.
     */
    @AfterMethod
    public void tearDownMethod() {
        log.info("Closing browser after test execution.");
        BrowserFactory.quitDriver(driver);  // Quit the driver associated with the test
        driver = null;  // Clear the driver reference
    }

    /**
     * Flushes the Extent Reports at the end of the test suite.
     */
    @AfterSuite
    public void tearDownSuite() {
        log.info("Tearing down the test suite.");
        extent.flush();
        log.info("Extent Reports flushed.");
    }
}