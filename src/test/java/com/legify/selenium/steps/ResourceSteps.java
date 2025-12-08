package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.pages.ResourcePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceSteps {

    @Autowired
    private ResourcePage resourcePage;

    @Autowired
    private LoginPage loginPage;

    @Given("I am logged in with valid credentials3")
    public void iAmLoggedInWithValidCredentials3() {
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");


        loginPage.loginToApp(username, password);
    }

    @When("I click the Resource menu icon")
    public void iClickTheResourceMenuIcon() {
        resourcePage.navigateToResourceModule();
    }

    @Then("I should see the Resource page")
    public void iShouldSeeTheResourcePage() {
        resourcePage.showResourcePage();
    }

    @When("I click the Create New Resource button")
    public void iClickTheCreateNewResourceButton() {
        resourcePage.clickCreateNewResourceButton();

    }

    @Then("I should see the Create New Resource window")
    public void iShouldSeeTheCreateNewResourceWindow() {
        resourcePage.isResourceCreationWindowDisplayed();
    }
}
