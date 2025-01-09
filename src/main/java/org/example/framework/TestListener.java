package org.example.framework;

import org.example.framework.AioIntegration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TestListener {

//    private WebDriver driver;
//
//    public TestListener(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    public void onTestFailure(String testCaseId) {
//        try {
//            // Capture screenshot
//            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            String screenshotPath = "screenshots/" + testCaseId + ".png";
//            File destination = new File(screenshotPath);
//
//            // Save the screenshot to the specified path
//            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//            // Add the screenshot to AIOIntegration
//            AioIntegration.addFailedTestScreenshot(testCaseId, screenshotPath);
//
//            System.out.println("Screenshot captured for Test Case: " + testCaseId);
//        } catch (IOException e) {
//            System.err.println("Failed to capture screenshot for Test Case: " + testCaseId);
//            e.printStackTrace();
//        }
//    }
}
