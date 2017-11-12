@TestingOnChrome
Feature: To execute test cases on the Chrome browser

  Scenario: C000 - Enter string into Google search field
    Given Tester goes to the "https://www.google.com" website
    When Tester enters "test case executed on Chrome" into the search field
    Then Tester verifies the search inputs are entered

  Scenario: C001 - Show a failed test case for cucumber report
    Given Tester fails a test case

