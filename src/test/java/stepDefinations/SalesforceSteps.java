package stepDefinations;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.pages.LeadPage;
import org.example.pages.LoginPage;

import java.util.List;
import java.util.Map;

public class SalesforceSteps {
    private LoginPage loginPage;
    private LeadPage leadPage;

    @Before
    public void setup() {
        loginPage = new LoginPage(); // Initialize the login page
    }

    @Given("I am on the Salesforce login page")
    public void i_am_on_the_salesforce_login_page() {
        if (loginPage == null) {
            throw new IllegalStateException("LoginPage is not initialized");
        }
        loginPage.navigateToLoginPage();
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        leadPage = loginPage.loginToSalesforce(username, password);
    }

    @When("I navigate to the Leads tab")
    public void i_navigate_to_the_leads_tab() {
        leadPage.navigateToLeadsTab();
    }

    @When("I create a new lead with following details")
    public void i_create_a_new_lead_with_following_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String lastName = data.get(0).get("LastName");
        String companyName = data.get(0).get("CompanyName");
        leadPage.createNewLead(lastName, companyName);
    }

    @Then("I should be able to convert the lead")
    public void i_should_be_able_to_convert_the_lead() {
        leadPage.convertLead();
    }

    @After
    public void tearDown() {
        if (loginPage != null) {
            loginPage.closeBrowser();
        }
    }
}
