package com.legify.selenium.pages;

import com.legify.selenium.helpers.VisibilityHelper;
import com.legify.selenium.runners.Hook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class UserPage implements BasePage {

    @Autowired
    private Hook hooks;

    @Autowired
    private VisibilityHelper visibilityHelper;

    @Autowired
    public UserPage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }

    // ---------------- Locators ----------------
    private final By userMenuIconBy = By.id("side_menu_list_item_ico_users");
    private final By usersPageHeaderBy = By.id("side_menu_list_item_users");
    private final By loaderBy = By.cssSelector(".loading-overlay");

    private final By createUserModalBy = By.cssSelector("div.user-form");

    private final By salutationDropdownBy =
            By.xpath("//mat-select[@formcontrolname='salutation']//div[contains(@class,'mat-select-trigger')]");

    private final By firstNameBy = By.id("firstName");

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    private final By countryDropdownBy =
            By.xpath("//mat-select[@formcontrolname='country']//div[contains(@class,'mat-select-trigger')]");

    private final By roleRadioGroupBy = By.xpath("//mat-radio-group[@formcontrolname='role']");

    private final By submitBtnBy = By.xpath("//button[normalize-space()='Submit']");

    @FindBy(xpath = "//button[normalize-space()='Create New User']")
    private WebElement createNewUserButton;


    // ---------------- Navigate Users Page ----------------
    public void navigateToUsersPage() {
        visibilityHelper.retryElementAction(() -> {
            WebElement icon = hooks.getWait().until(ExpectedConditions.elementToBeClickable(userMenuIconBy));
            visibilityHelper.jsScrollToCenter(icon);
            visibilityHelper.jsClick(icon);
        });
    }

    public void showUserPage() {
        visibilityHelper.retryElementAction(() -> {
            WebElement header = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(usersPageHeaderBy)
            );
            assertTrue(header.isDisplayed(), "Users page is not visible");
        });

        hooks.getWait().until(ExpectedConditions.visibilityOf(createNewUserButton));
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(createNewUserButton));
    }

    // ---------------- Create User ----------------
    public void clickCreateNewUserButton() {
        visibilityHelper.safeSleep(10000); // keeping your original logic

        visibilityHelper.retryElementAction(() -> {
            visibilityHelper.jsScrollToCenter(createNewUserButton);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(createNewUserButton));
            visibilityHelper.jsClick(createNewUserButton);
        });
    }

    public void showCreateUserModal() {
        visibilityHelper.retryElementAction(() -> {
            WebElement modal = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(createUserModalBy)
            );
            assertTrue(modal.isDisplayed(), "Create New User modal is not visible");
        });
    }

    public void waitForLoaderToDisappear() {
        visibilityHelper.waitForLoaderToDisappear();
    }

    // ---------------- Salutation ----------------
    public void selectSalutation(String value) {
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement dropdown = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(salutationDropdownBy)
            );

            visibilityHelper.jsScrollToCenter(dropdown);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(dropdown));
            dropdown.click();

            By optionBy = By.xpath("//mat-option//span[normalize-space()='" + value + "']");
            WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
            option.click();
        });
    }

    // ---------------- Name fields ----------------
    public void enterFirstName(String firstName) {
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(firstNameBy));
            visibilityHelper.jsScrollToCenter(input);
            input.clear();
            input.sendKeys(firstName);
        });
    }

    public void enterLastName(String last) {
        visibilityHelper.waitForLoaderToDisappear();
        lastNameField.clear();
        lastNameField.sendKeys(last);
    }

    public void enterEmail(String email) {
        visibilityHelper.waitForLoaderToDisappear();
        emailField.clear();
        emailField.sendKeys(email);
    }

    // ---------------- Country ----------------
    public void selectCountry(String countryName) {
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement dropdown = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(countryDropdownBy)
            );

            visibilityHelper.jsScrollToCenter(dropdown);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(dropdown));
            dropdown.click();

            By optionBy = By.xpath("//mat-option//span[normalize-space()='" + countryName + "']");
            WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
            option.click();
        });
    }

    // ---------------- Role ----------------
    public void selectRole(String roleName) {
        visibilityHelper.retryElementAction(() -> {
            hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(roleRadioGroupBy));

            By radioOptionBy = By.xpath("//mat-radio-button//span[contains(@class,'mat-radio-label-content') and normalize-space()='" + roleName + "']");
            WebElement radioOption = hooks.getWait().until(ExpectedConditions.elementToBeClickable(radioOptionBy));

            visibilityHelper.jsScrollToCenter(radioOption);
            visibilityHelper.jsClick(radioOption);
        });
    }

    public void clickSubmitButton() {

        visibilityHelper.retryElementAction(() -> {
            WebElement submitBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(submitBtnBy)
            );

            // Wait until enabled
            hooks.getWait().until(driver -> submitBtn.isEnabled());

            // Scroll
            visibilityHelper.jsScrollToCenter(submitBtn);

            // Click
            visibilityHelper.jsClick(submitBtn);
        });

        // Wait for loader to disappear
        visibilityHelper.waitForLoaderToDisappear();
    }


    public void isUserCreatedSuccessfully(String email) {

        visibilityHelper.retryElementAction(() -> {

            // Wait until users table loads
            hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//th[text()='User ID']")
                    )
            );

            // Check email exists in table
            By emailCell = By.xpath("//tbody//td/div[text()='" + email + "']");

            hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(emailCell)
            );
        });
    }

}
