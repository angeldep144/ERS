# What is Cucumber?
  # Cucumber is a BDD framework. When we use the term BDD, we are referring to Behavioral Driven Development

# What is BDD?
  # BDD attempts to describe tests in a human readable format so non technical people can also understand
  # NOTE: BDD is still TDD (Test Driven Development), but it emphasizes thinking about our tests at a high level
  # Technical aspects of the program/application are described using a language that is assessible to everyone on your team (non-technical people)
  # Cucumber allows us to achieve this with a language called GHERKIN

# Gherkin Keywords:
  # Feature - collection of scenarios and steps to complete the scenario
  # Scenario - defines some business logic or a way a user can use my application
  # Given - first step of the console (describes precondition that must be met first)
  # When - the event a user takes
  # Then - expected result of the event taken
  # And - "sugar syntax" for a Given, When or Then

Feature: ERS App Scenarios
  # Background sets a default GIVEN for all scenarios
  Background: A user is on the ERS App
  Scenario: Creating a new reimbursement request displays a new reimbursement request in the list of reimbursements
    #Given A user is on the ERS App
    When A user inputs a new reimbursement request in the input form
    Then A new reimbursement request will display in the list of reimbursements