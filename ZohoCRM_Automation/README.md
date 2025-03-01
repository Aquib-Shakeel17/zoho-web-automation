# Zoho CRM Web Automation Framework

This is an automation framework designed for testing Zoho CRM's web application. The framework uses **Selenium WebDriver**, **TestNG**, and **ExtentReports** for test execution, reporting, and logging. It follows a **Page Object Model** design and incorporates browser handling, logging, reporting, and configuration management.

---

## Framework Structure

### Folder Structure

/Users/apple/IdeaProjects/zoho-web-automation/ZohoCRM_Automation
├── /logs
│   └── test.log
├── /reports
├── /src
│   ├── /main/java/com/zoho/config
│   │   ├── ConfigManager.java
│   │   └── AppConfig.java
│   ├── /main/java/com/zoho/utils
│   │   └── BrowserFactory.java
│   ├── /test/java/com/zoho/base
│   │   └── BaseTest.java
│   ├── /test/java/com/zoho/tests
│   │   └── LoginTest.java
│   ├── /test/resources
│   │   ├── testdata
│   │   ├── config.properties
│   │   ├── log4j2.xml
│   │   └── testng.xml
├── pom.xml


---

## Key Components

- **`ConfigManager.java`**: Handles loading properties from `config.properties` and provides a utility method `getProperty()` to retrieve configuration values.

- **`Constants.java`**: Defines constants like the base URL and browser type for the automation suite, pulled from the configuration file.

- **`BrowserFactory.java`**: A factory class that initializes and manages WebDriver instances for different browsers (Chrome, Firefox, Edge) using **WebDriverManager**.

- **`BaseTest.java`**: The base test class extended by individual test classes. It manages browser session setup/teardown, report generation, and logging.

- **`LoginTest.java`**: A sample test class for testing the login functionality of Zoho CRM.

---

## Dependencies

- **Selenium WebDriver**
- **TestNG**
- **ExtentReports**
- **Log4j2**
- **WebDriverManager**

---

## Setup Instructions

### 1. Clone the Repository:


git clone <repository_url>
cd ZohoCRM_Automation
### 2. Install Dependencies:
Ensure you have Maven installed, then run the following command to download the required dependencies:

mvn install
### 3. Configure the config.properties File:
Update the src/test/resources/config.properties file with your Zoho CRM base URL and desired browser (chrome, firefox, edge).

Example:

properties

base_url=https://www.zoho.com/crm
browser=chrome
### 4. Log Configuration:
The log4j2.xml file is pre-configured to handle logging. You can modify it according to your logging requirements.

### 5. Run the Tests:
Execute the following Maven command to run the tests:


mvn clean test
### 6. Report Generation:
The framework generates an Extent Report after test execution. You can find the generated report in the /reports folder.

Running Specific Tests
To run specific tests using TestNG, modify the testng.xml file or use the following command:


mvn test -Dtest=LoginTest

### Reporting
The framework uses ExtentReports to generate detailed HTML reports. These reports can be found in the /reports folder and provide test execution details, including passed/failed tests, screenshots (if configured), and logs.

### Logging
Logs for the test execution are captured using Log4j2 and can be found in the logs/test.log file. You can modify logging levels and output in the log4j2.xml configuration.

### Utilities
BrowserFactory: Provides browser instances based on the browser name passed to the getDriver() method.

ConfigManager: Loads the configuration file (config.properties) and makes it accessible for other classes.

ExtentReports: Generates detailed test reports in HTML format.

