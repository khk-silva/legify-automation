Feature: Resource Module
  @resource
  Scenario: Navigate to the Resource module and open the resource creation form
    Given I am logged in with valid credentials3
    When I click the Resource menu icon
    Then I should see the Resource page
    When I click the Create New Resource button
    Then I should see the Create New Resource window
