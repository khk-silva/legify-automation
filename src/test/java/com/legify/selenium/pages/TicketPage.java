package com.legify.selenium.pages;

import com.legify.selenium.runners.Hook;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class TicketPage implements BasePage {

    @Autowired
    private Hook hooks;

    // Need Assistance button
    @FindBy(xpath = "//span[normalize-space()='Need assistance?']")
    private WebElement needAssistanceButton;

    @FindBy(xpath = "//h4[normalize-space()='Tickets']")
    private WebElement ticketsMenuText;

    @FindBy(id = "side_menu_list_item_ico_tickets")
    private WebElement ticketsMenuIcon;

    @FindBy(xpath = "//button[contains(.,'Open Ticket')]")
    private WebElement openTicketButton;



    // Click Need Assistance button with retry and wait
    public void clickNeedAssistanceButton() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(needAssistanceButton));
                button.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        // Wait until tickets menu is visible
        hooks.getWait().until(ExpectedConditions.visibilityOf(ticketsMenuText));
    }

    // Click Tickets menu icon with retry
    public void navigateToTicketsModule() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement icon = hooks.getWait().until(ExpectedConditions.elementToBeClickable(ticketsMenuIcon));
                icon.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    // Verify Tickets page is displayed
    public boolean showTicketsPage() {
        boolean isDisplayed = false;
        int attempts = 0;
        while (attempts < 3) {
            try {
                isDisplayed = hooks.getWait().until(ExpectedConditions.visibilityOf(ticketsMenuText)).isDisplayed();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }
        assertTrue(isDisplayed, "Tickets page is not visible");
        return isDisplayed;
    }

    // Click Open Ticket button with retry and optional wait
    public void clickOpenTicketButton() {
        try {
            Thread.sleep(15000); // Optional: wait for any loading
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(openTicketButton));
                button.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    public boolean isOpenTicketWindowDisplayed() {
        return hooks.getWait().until(ExpectedConditions.visibilityOf(openTicketButton)).isDisplayed();
    }
}
