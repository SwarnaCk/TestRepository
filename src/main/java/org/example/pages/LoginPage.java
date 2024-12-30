package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "username")
    private WebElement txtUsername;

    @FindBy(id = "password")
    private WebElement txtPassword;

    @FindBy(id = "Login")
    private WebElement btnLogin;

    @FindBy(xpath = "//div[contains(@class, 'error') or contains(@class, 'loginError')]")
    private WebElement loginErrorMessage;

    public LoginPage() {
        setupDriver();
        PageFactory.initElements(driver, this);
    }

    private void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.setPageLoadTimeout(Duration.ofSeconds(15));
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navigateToLoginPage() {
        driver.get("https://ck-qe-dev-ed.develop.my.salesforce.com/");
    }

    public LeadPage loginToSalesforce(String username, String password) {
        waitAndSendKeys(txtUsername, username);
        waitAndSendKeys(txtPassword, password);
        waitAndClick(btnLogin);
        return new LeadPage(driver);
    }

    public boolean isLoginErrorDisplayed() {
        try {
            return loginErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
        }
    }

    protected void waitAndSendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
