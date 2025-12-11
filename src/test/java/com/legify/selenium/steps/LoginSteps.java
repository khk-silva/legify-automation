package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {


    @Autowired
    LoginPage loginPage;

    // ----------------------------
    // Step Definitions
    // ----------------------------

    @Given("I am on the login page")
    public void navigateToLoginPage() {
        loginPage.verfiyLoginPage();
    }

    @When("I login with valid credentials")
    public void loginWithValidCredentials() {
        System.out.println("i_login_with_valid_credentials");
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");
        loginPage.loginToApp(username, password);
    }

    @Then("I should see the launchpad")
    public void verifyLaunchpadIsVisible() {
        loginPage.showLaunchpad();
    }

    @When("I login with invalid credentials")
    public void loginWithInvalidCredentials() {
        String username = JsonReader.getUsername("invalidUser");
        String password = JsonReader.getPassword("invalidUser");
        loginPage.loginToApp(username, password);
    }

    @Then("I should see an error message")
    public void verifyErrorMessageIsDisplayed() {
        String actualError = loginPage.getErrorMessage();
        String expectedError = "The email address or password you entered is incorrect. Please try again.";
        assertEquals(expectedError, actualError, "Error message mismatch");
    }
}



