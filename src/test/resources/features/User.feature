Feature: Create new user flow

  @users
  Scenario: Create a new user successfully
    Given I am logged in with valid credentials
    When I click the Users menu icon
    Then I should see the Users page
    When I click the Create New User button
    Then I should see the Create New User window
    And I fill the Create User form with test data "newUser1"
    And I save the new user
#    Then The new user should be created successfully
