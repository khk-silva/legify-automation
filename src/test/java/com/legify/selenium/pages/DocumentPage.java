package com.legify.selenium.pages;

import com.legify.selenium.runners.Hook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
public class DocumentPage implements BasePage {

    @Autowired
    private Hook hooks;

    @Autowired
    public DocumentPage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }

    // Locators
    private final By createNewDocBtnBy = By.xpath("//button[.//text()='Create New Document']");
    private final By documentTemplatesPageBy = By.cssSelector(".template-view-container");
    private final By uploadDocumentBtnBy = By.cssSelector("button.plus-icon-button");
    private final By uploadNewDocWindowBy = By.cssSelector("legify-ai-upload-document-template-popup");
    By uploadDocumentBtnBy1 = By.cssSelector("button.plus-icon-button[aria-label='Upload new']");

    private final By loaderBy = By.cssSelector(".loading-overlay");

    // Navigate to Document module (assuming some side menu)
    public void navigateToDocumentModule() {
        waitForLoaderToDisappear();// Optional: wait a bit if API/Angular is slow
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        By documentsMenuBy = By.xpath("//h4[normalize-space()='Documents']");
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement documentsMenu = hooks.getWait().until(
                        ExpectedConditions.elementToBeClickable(documentsMenuBy)
                );

                ((JavascriptExecutor) hooks.getDriver())
                        .executeScript("arguments[0].scrollIntoView({block: 'center'});", documentsMenu);

                // Try normal click first
                try {
                    documentsMenu.click();
                } catch (ElementClickInterceptedException e) {
                    // Fallback to JS click if normal click fails
                    ((JavascriptExecutor) hooks.getDriver())
                            .executeScript("arguments[0].click();", documentsMenu);
                }
                waitForLoaderToDisappear(); // wait for page to load after click
                break; // exit loop if click succeeds
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) {
                    throw e; // rethrow after 3 failed attempts
                }
            }
        }
    }


    public void waitForLoaderToDisappear() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
    }

    public void verifyAndClickCreateNewDocumentButton() {
        // Locator for the "Create New Document" button
        By createNewDocBtnBy = By.xpath("//button[.//span[contains(text(),'Create New Document')]]");

        // Wait for loader/spinner to disappear if any
        waitForLoaderToDisappear();

        try {
            // Wait for visibility
            WebElement button = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(createNewDocBtnBy)
            );

            // Scroll button into view
            ((JavascriptExecutor) hooks.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

            // Wait until clickable
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(button));

            // Click using JS (safer for Angular apps)
            ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", button);

            System.out.println("Create New Document button is visible and clicked successfully.");
        } catch (TimeoutException e) {
            throw new RuntimeException("Create New Document button is not visible or clickable!", e);
        }
    }


    public void clickCreateNewDocumentButton() {

        // Optional: wait a bit if API/Angular is slow
        try {
            Thread.sleep(45000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Wait for any loader/spinner to disappear
        waitForLoaderToDisappear();

        // Correct XPath that targets the span text inside the button
        //By buttonBy = By.xpath("//button[.//span[contains(text(),'Create New Document')]]");
        By buttonBy = By.xpath("//span[i[contains(@class,'bi-plus')] and contains(., 'Create New Document')]");

        // Retry a few times if stale
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(buttonBy));

                // Scroll into view
                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", button);

                // Click
                button.click();
                System.out.println("Create Newwwwwww");
                break; // success
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }


    public boolean isUploadDocumentOptionVisible() {
        try {
            // Wait until the button is visible (but not necessarily clickable)
            WebElement uploadBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy1)
            );

            // Scroll into view just for safety
            ((JavascriptExecutor) hooks.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", uploadBtn);

            return uploadBtn.isDisplayed(); // returns true if visible
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false; // button not visible
        }
    }


    public void verifyDocumentTemplatesPageIsVisible() {


        // Wait for loader to disappear first (if exists)
        waitForLoaderToDisappear();

        // Retry with JS check if element is present and visible
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement container = hooks.getWait().until(
                        ExpectedConditions.visibilityOfElementLocated(documentTemplatesPageBy)
                );
                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", container);
                assertTrue(container.isDisplayed(), "Document Templates page is not visible");
                break;
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
            }
        }
    }


    public void scrollPageUp() {
        ((JavascriptExecutor) hooks.getDriver())
                .executeScript("window.scrollTo({ top: 0, behavior: 'smooth' });");
    }

    public void clickUploadDocumentOption() {
        // Wait for any loader/spinner to disappear
        waitForLoaderToDisappear();

        int attempts = 0;
        while (attempts < 3) {
            try {
                // Wait for visibility first
                WebElement uploadBtn = hooks.getWait().until(
                        ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy)
                );

                // Scroll to center
                ((JavascriptExecutor) hooks.getDriver())
                        .executeScript("arguments[0].scrollIntoView({block: 'center'});", uploadBtn);

                // Wait a bit more until clickable
                hooks.getWait().until(ExpectedConditions.elementToBeClickable(uploadBtn));

                // Click via JS to avoid intercepted click
                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", uploadBtn);

                break; // success
            } catch (TimeoutException | StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) {
                    throw e; // fail after 3 retries
                }
                // small wait before retry
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }
        }
    }



    public void verifyUploadNewDocumentWindowIsVisible() {
        WebElement popup = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(uploadNewDocWindowBy));
        assertTrue(popup.isDisplayed(), "Upload New Document window is not visible");
    }

    // Fill document form example
    public void fillDocumentForm(String title, String jurisdiction, String type) {
        // Title field
        By titleFieldBy = By.cssSelector("input[formcontrolname='title']");
        WebElement titleField = hooks.getWait().until(ExpectedConditions.elementToBeClickable(titleFieldBy));
        titleField.clear();
        titleField.sendKeys(title);

        // Jurisdiction select
        By jurisdictionSelectBy = By.cssSelector("mat-select[formcontrolname='jurisdiction']");
        selectMatSelectOption(jurisdictionSelectBy, jurisdiction);

        // Type select
        By typeSelectBy = By.cssSelector("mat-select[formcontrolname='type']");
        selectMatSelectOption(typeSelectBy, type);
    }

    public void submitNewDocument() {
        By createBtnBy = By.xpath("//button[normalize-space()='Create']");
        WebElement createBtn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(createBtnBy));
        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", createBtn);
        createBtn.click();
        waitForLoaderToDisappear();
    }

    // Helper for mat-select dropdowns
    private void selectMatSelectOption(By selectBy, String optionText) {
        WebElement select = hooks.getWait().until(ExpectedConditions.elementToBeClickable(selectBy));
        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", select);
        select.click();

        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", optionText));
        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
        option.click();
    }
}
