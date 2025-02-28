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
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

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

    @BeforeMethod
    @Parameters("browser")
    public void setupTest(@Optional("chrome") String browser, Method method) {
        log.info("Starting test: " + method.getName());
        driver.set(BrowserFactory.getDriver(browser));
        driver.get().manage().window().maximize();
        driver.get().get(AppConfig.BASE_URL);
        log.info("Navigated to: " + AppConfig.BASE_URL);

        // Ensure the user is logged in before executing the test
        SessionManager.ensureUserIsLoggedIn(getDriver());

        ExtentTest extentTest = extent.createTest(method.getName());
        test.set(extentTest);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterMethod
    public void tearDownTest() {
        log.info("Closing browser after test execution.");
        BrowserFactory.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        log.info("Tearing down the test suite.");
        extent.flush();
        log.info("Extent Reports flushed.");
    }
}