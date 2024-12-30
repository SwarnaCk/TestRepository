package org.example.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LeadPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//div[@class='slds-icon-waffle']")
    private WebElement btnAppLauncher;

    @FindBy(xpath = "//input[@placeholder='Search apps and items...']")
    private WebElement txtSearchApps;

    @FindBy(xpath = "//b[normalize-space()='Leads']")
    private WebElement linkLeadsOption;

    @FindBy(xpath = "//div[@title='New']|//a[@title='New']|//a[text()='New' or contains(@class, 'forceActionLink') and .//div[text()='New']]")
    private WebElement btnNew;

    @FindBy(xpath = "//input[contains(@name, 'lastName') or @placeholder='Last Name']")
    private WebElement txtLastName;

    @FindBy(xpath = "//input[contains(@name, 'Company')]")
    private WebElement txtCompany;

    @FindBy(xpath = "//button[@name='SaveEdit']")
    private WebElement btnSave;

    @FindBy(xpath = "//lightning-button-menu[@class='menu-button-item slds-dropdown-trigger slds-dropdown-trigger_click']//lightning-primitive-icon[@variant='bare']")
    private WebElement btnConvertMenu;

    @FindBy(xpath = "//lightning-menu-item[@data-target-selection-name='sfdc:StandardButton.Lead.Convert']//a[@role='menuitem']")
    private WebElement btnConvertOption;

    @FindBy(xpath = "//button[@class='slds-button slds-button_brand']")
    private WebElement btnFinalConvert;

    public LeadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void navigateToLeadsTab() {
        waitAndClick(btnAppLauncher);
        pause(1000);
        waitAndSendKeys(txtSearchApps, "Leads");
        pause(1000);
        waitAndClick(linkLeadsOption);
        pause(1000);
    }

    public void createNewLead(String lastName, String companyName) {
        waitAndClick(btnNew);

        waitAndClick(txtLastName);
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
        pause(1000);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", txtCompany);
        waitAndClick(txtCompany);
        txtCompany.clear();
        txtCompany.sendKeys(companyName);
        pause(1000);

        waitAndClick(btnSave);
        pause(1000);
    }

    public void convertLead() {
        try {
            waitAndClick(btnConvertMenu);
            pause(1000);
            waitAndClick(btnConvertOption);
            pause(1000);

            if (btnFinalConvert.isEnabled()) {
                pause(1000);
                waitAndClick(btnFinalConvert);
                pause(1000);
            } else {
                System.out.println("Convert button is disabled. Check required fields.");
            }
        } catch (Exception e) {
            System.out.println("Error during lead conversion: " + e.getMessage());
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

    protected void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}