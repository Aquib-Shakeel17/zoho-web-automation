package com.zoho.utils;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowUtils {

    public static void switchToNewWindow(WebDriver driver) {
        String originalWindow = driver.getWindowHandle();

        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public static void switchToOriginalWindow(WebDriver driver, String originalWindowHandle) {
        driver.switchTo().window(originalWindowHandle);
    }
}
