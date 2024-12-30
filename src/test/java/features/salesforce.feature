Feature: Salesforce Lead Management
  As a Salesforce user
  I want to manage leads
  So that I can track potential customers
  @Retry
  @LT-TC-6
  @LT-TC-7
  @LT-TC-9
  @LT-TC-8
  Scenario: Login to Salesforce and create a new lead
    Given I am on the Salesforce login page
    When I login with username "saikat@cloudkaptan.com.dev" and password "S1234569"
    And I navigate to the Leads tab
    And I create a new lead with following details
      | LastName | CompanyName |
      | TestUser | TestCompany |
    Then I should be able to convert the lead