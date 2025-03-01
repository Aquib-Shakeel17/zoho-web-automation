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

                if (!clonedTestCase.has("leadName")) {
                    clonedTestCase.put("leadName", "Default Lead Name");
                }

                log.info("Generated Test Data for: " + testName + " -> " + clonedTestCase.toPrettyString());
                return clonedTestCase;
            }
        }
        throw new RuntimeException("Test data not found for test case: " + testName);
    }
}
