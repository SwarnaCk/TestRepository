package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.example.framework.AioIntegration;
import org.example.framework.ResultUploaderToAIOTest;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/salesforce.feature",
        glue = {"stepDefinations"}, // Ensure this package matches your step definitions' location
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },
        monochrome = true,
        dryRun = false
)
public class TestRunner {
        @AfterClass
        public static void uploadTestResultsAndScreenshots() {
                File cucumberJsonFile = new File("target/cucumber-reports/CucumberTestReport.json");

                System.out.println("Cucumber JSON file path: " + cucumberJsonFile.getAbsolutePath());
                System.out.println("File exists: " + cucumberJsonFile.exists());
                if (cucumberJsonFile.exists()) {
                        System.out.println("Uploading Cucumber JSON test results...");
                        ResultUploaderToAIOTest uploader = new ResultUploaderToAIOTest();
                        uploader.uploadTestResults(cucumberJsonFile);
                } else {
                        System.err.println("Cucumber JSON file not found: " + cucumberJsonFile.getAbsolutePath());
                        return;
                }

                 //Upload screenshots for failed tests
                System.out.println("Uploading screenshots for failed tests...");
                AioIntegration.uploadAllFailedScreenshots();
        }
}
