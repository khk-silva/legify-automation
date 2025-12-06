package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.pages.UserPage;
import com.legify.selenium.runners.Hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps {

    @Autowired
    LoginPage loginPage;

    @Autowired
    private Hook hooks;

    @Autowired
    private UserPage userPage;

    @Given("I am logged in with valid credentials")
    public void iAmLoggedInWithValidCredentials() {
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");
        loginPage.loginToApp(username, password);
        // Wait for Users menu icon to be visible after login
        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("side_menu_list_item_ico_users")));
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


}
