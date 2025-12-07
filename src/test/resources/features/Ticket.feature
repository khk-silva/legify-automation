Feature: Ticket module flow

  @tickets
  Scenario: Open a new ticket
    Given I am logged in with valid credentials2
    When I click the Need Assistance button
    And I click the Tickets menu icon
    Then I should see the Tickets page
    When I click the Open Ticket button
    Then The Open Ticket window should be displayed
