package com.legify.selenium.pages;

import com.legify.selenium.runners.Hook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class ResourcePage implements BasePage {

    @Autowired
    private Hook hooks;

    // -----------------------
    // LOCATORS
    // -----------------------

    // Resource menu text
    @FindBy(xpath = "//h4[normalize-space()='Build Your Own AI']")
    private WebElement resourceMenuText;

    // Resource menu icon (clickable)
    @FindBy(xpath = "//h4[normalize-space()='Build Your Own AI']")
    private WebElement resourceMenuIcon;

    // Create New Resource button
    @FindBy(xpath = "//button[contains(.,'Create New Resource')]")
    private WebElement createNewResourceButton;

    // Loader
    private final By loaderBy = By.cssSelector(".loading-overlay");



    // -----------------------
    // NAVIGATION
    // -----------------------

    // Click Resource menu with retry
    public void navigateToResourceModule() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement icon = hooks.getWait().until(ExpectedConditions.elementToBeClickable(resourceMenuIcon));
                icon.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    // Verify Resource page visible
    public boolean showResourcePage() {
        boolean isDisplayed = false;
        int attempts = 0;

        while (attempts < 3) {
            try {
                isDisplayed = hooks.getWait().until(ExpectedConditions.visibilityOf(resourceMenuText)).isDisplayed();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }

        assertTrue(isDisplayed, "Resource page is not visible");
        return isDisplayed;
    }


    // -----------------------
    // CREATE NEW RESOURCE
    // -----------------------

    public void clickCreateNewResourceButton() {
        try {
            Thread.sleep(15000); // same behaviour as TicketPage
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(createNewResourceButton));
                button.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    public boolean isResourceCreationWindowDisplayed() {
        return hooks.getWait().until(ExpectedConditions.visibilityOf(createNewResourceButton)).isDisplayed();
    }


    // -----------------------
    // WAIT FOR LOADER
    // -----------------------
    public void waitForLoaderToDisappear() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
    }



}
