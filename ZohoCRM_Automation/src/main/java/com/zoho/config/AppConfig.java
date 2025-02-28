package com.zoho.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppConfig {
    private static final Logger log = LogManager.getLogger(AppConfig.class);

    public static final String BASE_URL = ConfigManager.getProperty("base_url");
    public static final String BROWSER = ConfigManager.getProperty("browser");
    public static final String LOGIN_EMAIL = ConfigManager.getProperty("login_email");
    public static final String LOGIN_PASSWORD = ConfigManager.getProperty("login_password");
    public static final String EXPECTED_HOME_URL = ConfigManager.getProperty("expected_home_url");
    public static final String EXPECTED_USER_NAME = ConfigManager.getProperty("expected_user_name");
}
