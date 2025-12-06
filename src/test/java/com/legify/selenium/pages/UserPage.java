package com.legify.selenium.pages;


import com.legify.selenium.runners.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class UserPage implements BasePage{

    @Autowired
    private Hook hooks;

    // PageFactory locators
    @FindBy(id = "side_menu_list_item_ico_users")
    private WebElement userMenuItem;

    // Use id for Users page header if available
    @FindBy(id = "side_menu_list_item_users")
    private WebElement usersPageHeader;

    // XPath for Create New User button
    @FindBy(xpath = "//button[.//span[contains(.,'Create New User')]]")
    private WebElement createNewUserButton;

    // Constructor to initialize PageFactory elements
    @Autowired
    public UserPage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }

    public void navigateToUsersPage() {
        WebElement userMenuIcon = hooks.getWait().until(
                ExpectedConditions.elementToBeClickable(userMenuItem)
        );
        userMenuIcon.click();
    }

    public void showUserPage(){
        // Wait for the Users page header to be visible and re-locate it fresh to avoid stale element
        boolean isDisplayed = false;
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement usersPageHeader = hooks.getWait().until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("side_menu_list_item_users"))
                );
                isDisplayed = usersPageHeader.isDisplayed();
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }
        assertTrue(isDisplayed, "Users page is not visible");

        // Wait for Create New User button to be visible and clickable
        hooks.getWait().until(
                ExpectedConditions.visibilityOf(createNewUserButton)
        );
        hooks.getWait().until(
                ExpectedConditions.elementToBeClickable(createNewUserButton)
        );
    }

    public void clickCreateNewUserButton() {
        // Wait for the Create New User button to be clickable and click it
        hooks.getWait().until(
                ExpectedConditions.elementToBeClickable(createNewUserButton)
        );
        createNewUserButton.click();
    }

}
