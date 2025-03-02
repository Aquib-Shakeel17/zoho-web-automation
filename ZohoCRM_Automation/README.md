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


    Before running the framework, ensure that the following dependencies are installed and configured:
    Create a new gmail account
    Sign up on zoho dashboard and set up account(Company name and size) -> Get started and Skip
    Verify the account from gmail account
    Sign out from zoho dashboard
    Sign in again on Zoho dashboard -> update the location
    Sign Out again
    Sign In again and -> skip (Secure your account using MFA)
    Update the login_email and login_password in project and save
    Java (JDK 11)
    Check Java installation:
    java -version
    If not installed, download from Oracle JDK or install OpenJDK.
    Apache Maven (Build Automation Tool)
    Check Maven installation:
    mvn -version
    If not installed, download from Maven and set it up in the system environment variables.
    Git (Version Control System)
    Check Git installation:
    git --version
    If not installed, download from Git.
    Chrome Browser (For Selenium WebDriver Tests)
    Ensure the latest version of Chrome is installed.
    Clone the Repository: git clone https://github.com/Aquib-Shakeel17/zoho-web-automation.git
    Navigate into the Project Directory: cd zoho-web-automation
    Verify the Branch: git branch -a (Check available branches) → git checkout master (Switch to master branch)
    Set Up Dependencies: mvn clean install (Downloads and installs required libraries)
    Configure Properties : Update config.properties in src/test/resources for credentials (login_email, login_password) and browser settings.
    Run All Test Cases: mvn clean test (Executes the test suite).

## Running the Tests:

    To run the tests, execute the following Maven command:

   **mvn clean test**

This framework is designed to be modular and scalable, supporting easy addition of new test cases, page objects, and utilities.
It adheres to best practices for test automation, ensuring maintainability and readability.


