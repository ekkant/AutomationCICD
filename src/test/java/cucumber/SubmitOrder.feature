@tag
Feature: Purchase the order for ecommerce website
  I want to use this template for my feature file

Background:
Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Sumbitting the order
    Given Logged in with username <name> and password <password>
    When I added product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "Thankyou for the order." is displayed on ConfirmationPage

    Examples: 
      | name               | value    | productName  |
      | ekkant@example.com |Ek123456@ |ZARA COAT 3   |
