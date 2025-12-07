package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.pages.UserPage;
import com.legify.selenium.runners.Hook;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSteps {

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private UserPage userPage;

    @Autowired
    private Hook hooks;

    @Given("I am logged in with valid credentials")
    public void iAmLoggedInWithValidCredentials() {
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");

        loginPage.loginToApp(username, password);
    }

    @When("I click the Users menu icon")
    public void iClickTheUsersMenuIcon() {
        userPage.navigateToUsersPage();
    }

    @Then("I should see the Users page")
    public void iShouldSeeTheUsersPage() {
        userPage.showUserPage();
    }

    @When("I click the Create New User button")
    public void iClickTheCreateNewUserButton() {
        userPage.clickCreateNewUserButton();

    }

    @Then("I should see the Create New User window")
    public void iShouldSeeTheCreateNewUserwindow() {
        userPage.showCreateUserModal();
    }

    @When("I fill the Create User form with test data {string}")
    public void iFillCreateUserFormWithTestData(String userKey) {

        String salutation = JsonReader.getUserData(userKey, "salutation");
        String firstName = JsonReader.getUserData(userKey, "firstName");
        String lastName = JsonReader.getUserData(userKey, "lastName");
        String email = JsonReader.getUserData(userKey, "email");
        String country = JsonReader.getUserData(userKey, "country");
        String role = JsonReader.getUserData(userKey, "role");

        userPage.selectSalutation(salutation);
        userPage.enterFirstName(firstName);
        userPage.enterLastName(lastName);
        userPage.enterEmail(email);
        userPage.selectCountry(country);
        //userPage.selectRole(role);
    }

    @When("I save the new user")
    public void iSaveTheNewUser() {
        userPage.clickSubmitButton();
    }

    @Then("The new user should be created successfully")
    public void newUserShouldBeCreatedSuccessfully() {
        // Add validations later (snackbar / table search)
        System.out.println("User creation validation placeholder");
    }
}
