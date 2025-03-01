package com.zoho.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
    private static final Logger log = LogManager.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        try {
            // Load properties from config file
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
            fis.close();
            log.info("✅ Config properties loaded successfully.");
        } catch (IOException e) {
            log.error("❌ Failed to load config.properties", e);
            throw new RuntimeException("Could not load configuration file.");
        }
    }

    /**
     * Retrieves the value of a configuration property.
     * @param key The property key to fetch.
     * @return The property value or an empty string if not found.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            log.warn("Property '{}' is missing or empty in config.properties!", key);
            return "";
        }
        return value;
    }
}
