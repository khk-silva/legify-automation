Feature: User menu navigation

  @users
  Scenario: Click the Users menu icon
    Given I am logged in with valid credentials
    When I click the Users menu icon
    Then I should see the Users page
    When I click the Create New User button
#    Then I should see the Create New User page
