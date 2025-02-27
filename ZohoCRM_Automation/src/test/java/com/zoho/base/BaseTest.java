package com.zoho.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.zoho.config.Constants;
import com.zoho.utils.BrowserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeSuite
    public void setupReport() {
        // Generate a unique timestamp for each report
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = "reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        log.info("Extent Reports initialized: " + reportPath);
    }


    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) {
        driver = BrowserFactory.getDriver(browser);
        log.info("Launching browser: " + browser);
        driver.get(Constants.BASE_URL);
        ExtentTest extentTest = extent.createTest("Test: " + this.getClass().getSimpleName());
        test.set(extentTest);
        test.get().info("Navigated to " + Constants.BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            BrowserFactory.quitDriver();
            log.info("Browser closed.");
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
        log.info("Extent Reports flushed.");
    }
}
