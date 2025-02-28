package com.zoho.utils;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowUtils {

    /**
     * Switches to the newly opened browser window or tab.
     * This method should be called after clicking a link/button that opens a new window/tab.
     * @param driver WebDriver instance.
     */
    public static void switchToNewWindow(WebDriver driver) {
        String originalWindow = driver.getWindowHandle(); // Store the original window handle

        Set<String> allWindowHandles = driver.getWindowHandles(); // Get all available window handles
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(originalWindow)) { // Find the newly opened window
                driver.switchTo().window(windowHandle); // Switch control to the new window
                break;
            }
        }
    }

    /**
     * Switches back to the original browser window.
     * @param driver WebDriver instance.
     * @param originalWindowHandle The handle of the original window before switching.
     */
    public static void switchToOriginalWindow(WebDriver driver, String originalWindowHandle) {
        driver.switchTo().window(originalWindowHandle); // Restore focus to the original window
    }
}
