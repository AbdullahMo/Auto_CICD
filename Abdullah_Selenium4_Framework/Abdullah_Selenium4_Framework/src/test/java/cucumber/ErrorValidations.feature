Feature: Validate error validations from ECommerce Website
  Background:
    Given I landed on ECommerce pagee

    @ErrorValidation
  Scenario Outline: Negative Test for ECommerce validations
    Given I Log in with invalid username <name> or password <password>
    Then "Incorrect email or password." message is displayed on LoginPage
    Examples:
      |name  | password |
      |abdullah123@test.com | P@ssword12333 |