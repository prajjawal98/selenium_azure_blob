Feature: WebDriver BiDi with Cucumber

  Scenario: Navigate and listen to console logs
    Given I navigate to website
    When Network conditions are set to offline
    Then I should see the console logs in the output


