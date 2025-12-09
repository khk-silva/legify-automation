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
public class TicketPage implements BasePage {

    @Autowired
    private Hook hooks;

    @Autowired
    private VisibilityHelper visibilityHelper;

    @Autowired
    public TicketPage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }

    // ---------------- Locators ----------------


    private final By needAssistanceButton = By.xpath(
            "//div[contains(@class,'menu-container__switch-menu-section')]//*[contains(normalize-space(),'Need assistance')]"
    );

    @FindBy(xpath = "//span[normalize-space()='Tickets']")
    private WebElement ticketsHeaderText;

    @FindBy(id = "side_menu_list_item_ico_tickets")
    private WebElement ticketsMenuIcon;

    @FindBy(xpath = "//button[contains(.,'Open Ticket')]")
    private WebElement openTicketButton;

    private final By subjectInputBy = By.id("subject");
    private final By descriptionInputBy = By.id("description");
    private final By submitBtnBy = By.xpath("//button[normalize-space()='Submit']");
    private final By loaderBy = By.cssSelector(".loading-overlay");


    // ---------------- Navigate Tickets ----------------


    public void clickNeedAssistanceButton() {
        // Optional sleep if needed (keep original logic)
        visibilityHelper.safeSleep(15000); // short wait for UI update

        // Retry click action using VisibilityHelper
        visibilityHelper.retryElementAction(() -> {
            // Wait for element to be clickable
            WebElement button = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(needAssistanceButton)
            );

            // Scroll to center and click
            visibilityHelper.jsScrollToCenter(button);
            visibilityHelper.jsClick(button);
        });


    }

    public void navigateToTicketsModule() {
        visibilityHelper.retryElementAction(() -> {
            visibilityHelper.jsScrollToCenter(ticketsMenuIcon);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(ticketsMenuIcon));
            visibilityHelper.jsClick(ticketsMenuIcon);
        });
    }

    public boolean verifyTicketsPageVisible() {

        visibilityHelper.safeSleep(15000); // short wait for UI update

        visibilityHelper.retryElementAction(() -> {
            WebElement header = hooks.getWait().until(
                    ExpectedConditions.visibilityOf(ticketsHeaderText)
            );
            assertTrue(header.isDisplayed(), "Tickets page is not visible");
        });
        return true;
    }


    // ---------------- Open Ticket Window ----------------

    public void clickOpenTicketButton() {
        visibilityHelper.safeSleep(5000); // your original logic

        visibilityHelper.retryElementAction(() -> {
            visibilityHelper.jsScrollToCenter(openTicketButton);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(openTicketButton));
            visibilityHelper.jsClick(openTicketButton);
        });
    }

    public boolean isOpenTicketWindowDisplayed() {
        try {
            hooks.getWait().until(ExpectedConditions.visibilityOf(openTicketButton));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    // ---------------- Input Fields ----------------

    public void enterSubject(String subject) {
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(subjectInputBy)
            );
            visibilityHelper.jsScrollToCenter(input);
            input.clear();
            input.sendKeys(subject);
        });
    }

    public void enterDescription(String description) {
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(descriptionInputBy)
            );
            visibilityHelper.jsScrollToCenter(input);
            input.clear();
            input.sendKeys(description);
        });
    }


    // ---------------- Dropdown Selection ----------------

    public void selectFromDropdown(String formControlName, String value) {

        By dropdownBy = By.xpath("//mat-select[@formcontrolname='" + formControlName + "']");
        By optionBy = By.xpath("//mat-option//span[normalize-space()='" + value + "']");
        By panelBy = By.cssSelector(".mat-select-panel");

        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(dropdownBy));
            visibilityHelper.jsScrollToCenter(dropdown);
            dropdown.click();

            hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(panelBy));

            WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
            option.click();
        });
    }


    // ---------------- Submit Ticket ----------------

    public void clickSubmit() {
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {

            WebElement submitBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(submitBtnBy)
            );

            hooks.getWait().until(driver -> submitBtn.isEnabled());

            visibilityHelper.jsScrollToCenter(submitBtn);
            visibilityHelper.jsClick(submitBtn);
        });

        visibilityHelper.waitForLoaderToDisappear();
    }

    public boolean isTicketSubmitted() {
        try {
            // Wait until the Submit button disappears
            hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(submitBtnBy));

            // Optional: wait for a loader or confirmation message to disappear/appear
            visibilityHelper.safeSleep(3000); // short wait for UI update


            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}

