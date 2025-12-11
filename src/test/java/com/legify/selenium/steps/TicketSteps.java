package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.pages.TicketPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketSteps {

    @Autowired
    private TicketPage ticketPage;

    @Autowired
    private LoginPage loginPage;

    @Given("I am logged in with valid credentials2")
    public void loginWithValidCredentials() {
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");

        loginPage.loginToApp(username, password);
    }


    @When("I click the Need Assistance button")
    public void clickNeedAssistanceButton() {
        ticketPage.clickNeedAssistanceButton();
    }

    @And("I click the Tickets menu icon")
    public void clickTicketsMenuIcon() {
        ticketPage.navigateToTicketsModule();
    }

    @Then("I should see the Tickets page")
    public void verifyTicketsPage() {
        assertTrue(ticketPage.verifyTicketsPageVisible(), "Tickets page is not displayed");
    }

    @When("I click the Open Ticket button")
    public void clickOpenTicketButton() {
        ticketPage.clickOpenTicketButton();
    }

    @Then("The Open Ticket window should be displayed")
    public void verifyOpenTicketWindow() {
        assertTrue(ticketPage.isOpenTicketWindowDisplayed(), "Open Ticket window is not displayed");
    }

    @When("I fill the Ticket form with test data {string}")
    public void fillTicketFormWithTestData(String ticketKey) {

        String priority = JsonReader.getTicketData(ticketKey, "priority");
        String category = JsonReader.getTicketData(ticketKey, "category");
        String subject = JsonReader.getTicketData(ticketKey, "subject");
        String description = JsonReader.getTicketData(ticketKey, "description");

       // ticketPage.selectPriority(priority);
        //ticketPage.selectCategory(category);
        ticketPage.selectFromDropdown("priority", priority);
        ticketPage.selectFromDropdown("category", category);
        ticketPage.enterSubject(subject);
        ticketPage.enterDescription(description);
    }

    @And("I submit the new ticket")
    public void submitNewTicket() {
        ticketPage.clickSubmit();
    }

    @Then("The new ticket should be created successfully")
    public void verifyTicketCreatedSuccessfully() {
        boolean isSubmitted = ticketPage.isTicketSubmitted();
        System.out.println("Ticket submission validation: " + isSubmitted);
    }
}
