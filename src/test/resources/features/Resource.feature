Feature: Resource Management
  As a user
  I want to create a new resource
  So that I can manage AI-powered document resources in the system

  @resource
  Scenario: Create a new resource successfully
    Given I am logged in with valid credentials3
    When I click the Resource menu icon
    Then I should see the Resource page
    When I click the Create New Resource button
    Then I should see the Create New Resource window
    When I fill the Resource form with test data "NewResource1"
#    And I submit the new resource
#    Then The new resource should be created successfully "NewResource1"
