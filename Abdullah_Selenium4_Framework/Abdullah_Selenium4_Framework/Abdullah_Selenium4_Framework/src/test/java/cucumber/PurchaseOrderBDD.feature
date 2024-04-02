Feature: Purchase the Order from ECommerce Website
  Background:
    Given I landed on ECommerce page

    @Regression
  Scenario Outline: Positive Test for Submitting an Order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart
    And Checkout <productName> and submit order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
    Examples:
      |name  | password | productName |
      |abdullah123@test.com | P@ssword123 | ZARA COAT 3 |


