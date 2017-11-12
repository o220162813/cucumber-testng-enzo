@TestingOnFirefox
Feature: To execute test cases on the Firefox browser

  Scenario: F000 - Enter string into Google search field
    Given Tester goes to the "https://www.google.com" website
    When Tester enters "test case executed on Firefox" into the search field
    Then Tester verifies the search inputs are entered