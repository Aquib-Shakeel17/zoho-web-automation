## Zoho CRM Web Automation Framework

This is an automation framework designed for testing Zoho CRM's web application.
The framework uses **Selenium WebDriver**, **TestNG**, and **ExtentReports** for test execution,
reporting, and logging.
It follows a **Page Object Model** design and incorporates browser handling, logging, reporting, and configuration management.


## Framework Structure


`/ZohoCRM_Automation
│
├── /logs                          # Logs generated during test execution
│   └── test.log                   # Log file capturing runtime information
│
├── /reports                       # Test execution reports
│   └── ExtentReport               # ExtentReports HTML report
│
├── /src/main/java/com/zoho        # Main source code
│   ├── /config                    # Configuration classes
│   │   ├── AppConfig.java         # Centralized application configuration
│   │   └── ConfigManager.java     # Configuration management
│   │
│   ├── /pages                     # Page Object Model classes
│   │   ├── BasePage.java          # Base page with common methods
│   │   ├── DeleteLeadPage.java    # Page object for 'Delete Lead' functionality
│   │   ├── EditLeadPage.java      # Page object for 'Edit Lead' functionality
│   │   ├── FilterPage.java        # Page object for 'Filter' functionality
│   │   ├── HomePage.java          # Page object for 'Home' page
│   │   ├── LeadPage.java          # Page object for 'Lead' page
│   │   └── LoginPage.java         # Page object for 'Login' page
│   │
│   ├── /utils                     # Utility classes
│   │   ├── BrowserFactory.java    # Browser initialization and WebDriver management
│   │   ├── JsonDataReader.java    # Utility for reading JSON data
│   │   ├── SessionManager.java    # Session management during tests
│   │   ├── WaitUtil.java          # Wait strategies for handling dynamic elements
│   │   └── WindowUtils.java       # Utilities for managing browser windows
│   │
│   └── /test                      # Test classes
│       ├── BaseTest.java          # Base test class for setup and teardown
│       ├── CreateLeadTest.java    # Test case for creating a lead
│       ├── DeleteLeadTest.java    # Test case for deleting a lead
│       ├── EditLeadTest.java      # Test case for editing a lead
│       ├── FilterTest.java        # Test case for filtering leads
│       └── ...
│
├── /src/test/resources            # Resources for testing
│   ├── config.properties          # Configuration file for environment settings
│   ├── log4j2.xml                 # Log configuration file
│   ├── testdata.json              # JSON file containing test data
│   └── testng.xml                 # TestNG configuration for defining test suite
│
├── pom.xml                        # Maven project file for managing dependencies`


**Key Components**

## Pages:
    The framework follows the Page Object Model (POM) design pattern.
    Each page or screen in the application is represented by a corresponding Java class under the /pages folder.
    These classes contain methods that interact with the web elements on their respective pages.

## Config:
   The /config folder contains configuration-related classes such as:

    AppConfig.java: Holds static configuration values for easy access throughout the framework.
    ConfigManager.java: Loads properties from the config.properties file and provides a method to retrieve individual configuration values.

## Utilities:
   Utility classes are present under the /utils folder to assist with various tasks like:

    BrowserFactory: Initializes and manages the WebDriver.
    JsonDataReader: Handles reading and parsing data from external JSON files.
    WaitUtil: Implements wait strategies for handling dynamic web elements.
    SessionManager: Manages the browser session during tests.

## Test Classes:

   Test classes reside in the /test folder and contain the actual test cases.
   They typically extend BaseTest.java for common setup and teardown operations.
   Example test cases include:

    CreateLeadTest.java
    EditLeadTest.java
    FilterLeadTest.java
    DeleteLeadTest.java

## Test Configuration and Data:

    config.properties: Contains environment-specific settings, such as the base URL and browser configurations.
    testng.xml: Defines the test suite, which includes test execution configuration, such as which tests to run, parallel execution, etc.
    testdata.json: Stores data needed for test execution (e.g., sample input for creating a lead).

## Reports and Logs:

    ExtentReports: Provides a detailed HTML report (ExtentReport_YYYYMMDD_HHMMSS.html) for each test run.
    test.log: Captures logs during the execution of tests to help with debugging and tracking test progress.

## Maven (pom.xml):
   The project uses Maven as the build and dependency management tool.
   The pom.xml contains dependencies for libraries such as Selenium, TestNG, ExtentReports, and others.

## Prerequisites:

    Before executing the tests, ensure the following prerequisites are in place:

    Java (JDK 11 or higher) is installed.
    Maven is installed and configured.
    Selenium WebDriver dependencies are properly configured in the pom.xml file.
    TestNG is configured for running the test suites.
    Ensure the config.properties file is configured with the correct environment settings (e.g., base URL, browser settings).

## Running the Tests:

    To run the tests, execute the following Maven command:

   **mvn clean test**

This framework is designed to be modular and scalable, supporting easy addition of new test cases, page objects, and utilities.
It adheres to best practices for test automation, ensuring maintainability and readability.


