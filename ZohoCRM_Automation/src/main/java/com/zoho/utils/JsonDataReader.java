package com.zoho.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

public class JsonDataReader {
    private static final Logger log = LogManager.getLogger(JsonDataReader.class);
    private JsonNode testData;
    private final Random random = new Random();

    public JsonDataReader(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File(filePath);
            if (!jsonFile.exists()) {
                throw new RuntimeException("Test data file not found at: " + filePath);
            }
            testData = objectMapper.readTree(jsonFile).get("testCases");
            if (testData == null || !testData.isArray()) {
                throw new RuntimeException("Invalid JSON format: 'testCases' array is missing.");
            }
            log.info("Test data loaded successfully from: " + filePath);
        } catch (IOException e) {
            log.error("Failed to load test data from JSON file: " + filePath, e);
            throw new RuntimeException("Failed to load test data from JSON file.");
        }
    }

    public JsonNode getTestData(String testName) {
        Iterator<JsonNode> elements = testData.elements();
        while (elements.hasNext()) {
            JsonNode testCase = elements.next();
            if (testCase.has("testName") && testCase.get("testName").asText().equals(testName)) {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode clonedTestCase = objectMapper.valueToTree(testCase);

                // Check if the email field needs to be random
                if (clonedTestCase.has("email") && clonedTestCase.get("email").asText().equals("RANDOM")) {
                    String randomEmail = generateRandomEmail();
                    clonedTestCase.put("email", randomEmail);
                    log.info("Generated random email: " + randomEmail);
                }

                // If the firstName is "RANDOM", generate in format First123
                if (clonedTestCase.has("firstName") && clonedTestCase.get("firstName").asText().equals("RANDOM")) {
                    clonedTestCase.put("firstName", generateRandomFirstName());
                }

                // If the lastName is "RANDOM", generate in format Last (Sample)
                if (clonedTestCase.has("lastName") && clonedTestCase.get("lastName").asText().equals("RANDOM")) {
                    clonedTestCase.put("lastName", generateRandomLastName());
                }

                // Default leadName if not present
                if (!clonedTestCase.has("leadName")) {
                    clonedTestCase.put("leadName", "Default Lead Name");
                }

                log.info("Generated Test Data for: " + testName + " -> " + clonedTestCase.toPrettyString());
                return clonedTestCase;
            }
        }
        throw new RuntimeException("Test data not found for test case: " + testName);
    }

    // Method to generate a random email address in the format "testuser12345@test.com"
    private String generateRandomEmail() {
        int randomNum = random.nextInt(100000);  // Random number between 0 and 99999
        return "testuser" + randomNum + "@test.com";
    }

    // Method to generate first name in format "First123"
    private String generateRandomFirstName() {
        int randomNum = random.nextInt(1000);  // Random number between 0 and 999
        return "First" + randomNum;
    }

    // Method to generate last name in format "Last (Sample)"
    private String generateRandomLastName() {
        return "Last (Sample)";
    }
}
