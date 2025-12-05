package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.runners.Hook;
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
    private Hook hooks;

    @Autowired
    LoginPage loginPage;

    // ----------------------------
    // Step Definitions
    // ----------------------------

    @Given("I am on the login page")
    public void i_am_on_login_page() {
        hooks.getDriver();
    }

    @When("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        System.out.println("i_login_with_valid_credentials");
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");
        loginPage.loginToApp(username, password);
    }

    @Then("I should see the launchpad")
    public void i_should_see_launchpad() {
        WebElement launchpadElement = hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".greeting-line"))
        );
        assertTrue(launchpadElement.isDisplayed(), "Login failed: Launchpad not visible");
    }

    @When("I login with invalid credentials")
    public void i_login_with_invalid_credentials() {
        String username = JsonReader.getUsername("invalidUser");
        String password = JsonReader.getPassword("invalidUser");
        loginPage.loginToApp(username, password);
    }

    @Then("I should see an error message")
    public void i_should_see_error_message() {
        String actualError = loginPage.getErrorMessage();
        String expectedError = "The email address or password you entered is incorrect. Please try again.";
        assertEquals(expectedError, actualError, "Error message mismatch");
    }
}
