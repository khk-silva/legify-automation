//package com.legify.selenium.pages;
//
//
//import com.legify.selenium.runners.Hook;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LoginPage implements BasePage{
//
//    @Autowired
//    private Hook hooks;
//
//    // PageFactory locators
//    @FindBy(id = "email")
//    private WebElement emailField;
//
//    @FindBy(id = "password")
//    private WebElement passwordField;
//
//    @FindBy(className = "login-page__login-button")
//    private WebElement loginButton;
//
//    private By errorMessageLocator = By.className("login-page__alert");
//
//    // Methods
//    public void enterEmail(String mailAddress) {
//        hooks.getWait().until(ExpectedConditions.visibilityOf(emailField));
//        emailField.clear();
//        emailField.sendKeys(mailAddress);
//    }
//
//    public void enterPassword(String password) {
//        hooks.getWait().until(ExpectedConditions.visibilityOf(passwordField));
//        passwordField.clear();
//        passwordField.sendKeys(password);
//    }
//
//    public void clickLogin() {
//        hooks.getWait().until(ExpectedConditions.elementToBeClickable(loginButton));
//        loginButton.click();
//    }
//
//    public void loginToApp(String mailAddress, String password) {
//        enterEmail(mailAddress);
//        enterPassword(password);
//        clickLogin();
//    }
//
//    public String getErrorMessage() {
//        WebElement error = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
//        return error.getText();
//    }
//}



package com.legify.selenium.pages;


import com.legify.selenium.runners.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class LoginPage implements BasePage{

    @Autowired
    private Hook hooks;

    // PageFactory locators
    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(className = "login-page__login-button")
    private WebElement loginButton;

    private By errorMessageLocator = By.className("login-page__alert");

    // Methods
    public void enterEmail(String mailAddress) {
        hooks.getWait().until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(mailAddress);
    }

    public void enterPassword(String password) {
        hooks.getWait().until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void verfiyLoginPage(){
        hooks.getDriver();
    }

    public void loginToApp(String mailAddress, String password) {
        enterEmail(mailAddress);
        enterPassword(password);
        clickLogin();
    }

    public void showLaunchpad(){
        WebElement launchpadElement = hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".greeting-line"))
        );
        assertTrue(launchpadElement.isDisplayed(), "Login failed: Launchpad not visible");
    }

    public String getErrorMessage() {
        WebElement error = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
        return error.getText();
    }
}

