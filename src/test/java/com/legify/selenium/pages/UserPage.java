package com.legify.selenium.pages;

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
    public UserPage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }


    private final By userMenuIconBy = By.id("side_menu_list_item_ico_users");
    private final By usersPageHeaderBy = By.id("side_menu_list_item_users");
    private final By loaderBy = By.cssSelector(".loading-overlay");
    private final By createUserModalBy = By.cssSelector("div.user-form");

    private final By salutationDropdownBy =
            By.xpath("//mat-select[@formcontrolname='salutation']//div[contains(@class,'mat-select-trigger')]");
    private String salutationOptionXpath = "//mat-option//span[normalize-space()='%s']";

    private final By firstNameBy = By.id("firstName");

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    private final By countryDropdownBy =
            By.xpath("//mat-select[@formcontrolname='country']//div[contains(@class,'mat-select-trigger')]");
    private String countryOptionXpath = "//mat-option//span[normalize-space()='%s']";

    private final By roleRadioGroupBy = By.xpath("//mat-radio-group[@formcontrolname='role']");
    private String roleOptionXpath =
            "//mat-radio-button//span[contains(@class,'mat-radio-label-content') and normalize-space()='%s']";

    private final By submitBtnBy = By.xpath("//button[normalize-space()='Submit']");


    @FindBy(xpath = "//button[normalize-space()='Create New User']")
    private WebElement createNewUserButton;



    public void navigateToUsersPage() {

        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement icon = hooks.getWait().until(ExpectedConditions.elementToBeClickable(userMenuIconBy));
                icon.click();
                break; // success
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    public void showUserPage() {

        boolean isDisplayed = false;
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement usersPageHeader = hooks.getWait().until(
                        ExpectedConditions.visibilityOfElementLocated(usersPageHeaderBy)
                );
                isDisplayed = usersPageHeader.isDisplayed();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }
        assertTrue(isDisplayed, "Users page is not visible");

        hooks.getWait().until(ExpectedConditions.visibilityOf(createNewUserButton));
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(createNewUserButton));
    }

    public void clickCreateNewUserButton() {
        try {
            System.out.println("Thread uuuuuuuuuuuuuuuuuu");
            Thread.sleep(45000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hooks.getWait().until(ExpectedConditions.elementToBeClickable(createNewUserButton));
        createNewUserButton.click();
    }

    public void showCreateUserModal() {

        boolean isDisplayed = false;
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement modal = hooks.getWait().until(
                        ExpectedConditions.visibilityOfElementLocated(createUserModalBy)
                );
                isDisplayed = modal.isDisplayed();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }
        assertTrue(isDisplayed, "Create New User modal is not visible");
    }

    public void waitForLoaderToDisappear() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
    }

    public void selectSalutation(String value) {

        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));

        WebElement dropdown = hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(salutationDropdownBy));

        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdown.click();

        By optionBy = By.xpath(String.format(salutationOptionXpath, value));
        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(optionBy));

        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
        option.click();
    }

    public void enterFirstName(String firstName) {

        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));

        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(firstNameBy));

        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", input);

        input.clear();
        input.sendKeys(firstName);
    }

    public void enterLastName(String last) {
        waitForLoaderToDisappear();
        lastNameField.clear();
        lastNameField.sendKeys(last);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void selectCountry(String countryName) {

        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));

        WebElement dropdown = hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(countryDropdownBy));

        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdown.click();

        By optionBy = By.xpath(String.format(countryOptionXpath, countryName));
        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(optionBy));

        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
        option.click();
    }

    public void selectRole(String roleName) {

        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(roleRadioGroupBy));

        By radioOptionBy = By.xpath(String.format(roleOptionXpath, roleName));
        WebElement radioOption = hooks.getWait().until(ExpectedConditions.elementToBeClickable(radioOptionBy));

        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", radioOption);
        radioOption.click();
    }

    public void clickSubmitButton() {

        WebElement submitBtn = hooks.getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(submitBtnBy));

        hooks.getWait().until(driver -> submitBtn.isEnabled());

        ((JavascriptExecutor) hooks.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", submitBtn);

        hooks.getWait().until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".loading-overlay, .spinner, .mat-progress-spinner")));
    }

}

