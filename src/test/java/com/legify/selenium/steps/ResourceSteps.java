package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.pages.ResourcePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceSteps {

    @Autowired
    private ResourcePage resourcePage;

    @Autowired
    private LoginPage loginPage;

    @Given("I am logged in with valid credentials3")
    public void loginWithValidCredentials() {
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");


        loginPage.loginToApp(username, password);
    }

    @When("I click the Resource menu icon")
    public void clickResourceMenuIcon() {
        resourcePage.navigateToResourceModule();
    }

    @Then("I should see the Resource page")
    public void verifyResourcePageIsVisible() {
        resourcePage.showResourcePage();
    }

    @When("I click the Create New Resource button")
    public void clickCreateNewResourceButton() {
        resourcePage.clickCreateNewResourceButton();

    }

    @Then("I should see the Create New Resource window")
    public void verifyCreateNewResourceWindowIsVisible() {
        resourcePage.isResourceCreationWindowDisplayed();
    }

    @When("I fill the Resource form with test data {string}")
    public void fillResourceFormWithTestData(String resourceKey) {
        String title = JsonReader.getResourceData(resourceKey, "title");
        String type = JsonReader.getResourceData(resourceKey, "type");
        String applicableTypes = JsonReader.getResourceData(resourceKey, "applicableTypes");
        String jurisdiction = JsonReader.getResourceData(resourceKey, "jurisdiction");
        String tags = JsonReader.getResourceData(resourceKey, "tags");
        String attachment = JsonReader.getResourceData(resourceKey, "attachment");

        // Enter Title
        resourcePage.enterTitle(title);

        // Select Type
        resourcePage.selectType(type);

        // Select Applicable Document Types
        resourcePage.selectApplicableDocumentType(applicableTypes);

        // Select Jurisdiction
        resourcePage.selectJurisdiction(jurisdiction);

        // Enter Tags (press enter to create tag)
        resourcePage.addTag(tags);

        // Upload Document
        resourcePage.uploadDocument(attachment);
    }

    @And("I submit the new resource")
    public void submitNewResource() {
        resourcePage.clickCreateButton();
    }

    @Then("The new resource should be created successfully {string}")
    public void verifyResourceCreatedSuccessfully( String resourceKey) {
        String title = JsonReader.getResourceData(resourceKey, "title");
        boolean isCreated = resourcePage.isResourceCreatedSuccessfully(title);
        System.out.println("Resource creation validation: " + isCreated);
        assertTrue(isCreated, "Resource creation failed");
    }

}
