//package com.legify.selenium.pages;
//
//import com.legify.selenium.runners.Hook;
//import io.cucumber.plugin.event.Node;
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.PageFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@Component
//public class DocumentPage implements BasePage {
//
//    @Autowired
//    private Hook hooks;
//
//    @Autowired
//    public DocumentPage(Hook hooks) {
//        this.hooks = hooks;
//        PageFactory.initElements(hooks.getDriver(), this);
//    }
//
//    // Locators
//    private final By createNewDocBtnBy = By.xpath("//button[.//text()='Create New Document']");
//    private final By documentTemplatesPageBy = By.cssSelector(".template-view-container");
//    //private final By uploadDocumentBtnBy = By.cssSelector("button.plus-icon-button");
//    By uploadDocumentBtnBy = By.xpath("//button[contains(@class,'plus-icon-button')]//i[contains(@class,'bi-upload')]");
//
//    private final By uploadNewDocWindowBy = By.cssSelector("legify-ai-upload-document-template-popup");
//    By uploadDocumentBtnBy1 = By.cssSelector("button.plus-icon-button[aria-label='Upload new']");
//
//    private final By loaderBy = By.cssSelector(".loading-overlay");
//
//    // Locators – Popup
//    // -------------------------------------
//    private final By popupContainerBy = By.xpath("//mat-dialog-container");
//
//    private final By docTitleInputBy = By.xpath("//input[@formcontrolname='title']");
//    private final By docJurisdictionDropdownBy = By.xpath("//mat-select[@formcontrolname='jurisdiction']");
//    private final By docTypeDropdownBy = By.xpath("//mat-select[@formcontrolname='type']");
//    private final By docRecipientInputBy = By.xpath("//input[@placeholder='Start by typing email']");
//    private final By docFileUploadInputBy = By.xpath("//input[@type='file']");
//
//    private final By docCreateBtnBy = By.xpath("//button[contains(@class,'create-button')]");
//    //private final By docCreateEnabledBtnBy = By.xpath("//button[contains(@class,'create-button') and not(@disabled)]");
//    By docCreateEnabledBtnBy = By.xpath("//button[contains(@class,'la-btn__filled') and normalize-space()='Create']");
//
//    private final By uploadNewDocumentTitleBy =
//            By.xpath("//div[contains(@class,'title-padding') and normalize-space()='Upload New Document']");
//
//    By processedTextBy = By.xpath("//p[contains(@class,'loading-text') and contains(text(),'successfully processed')]");
//    By letsGoBtnBy = By.xpath("//button[normalize-space()=\"Let's Go\"]");
//
//
//
//
//    // Navigate to Document module (assuming some side menu)
//    public void navigateToDocumentModule() {
//        waitForLoaderToDisappear();// Optional: wait a bit if API/Angular is slow
//
//
//        By documentsMenuBy = By.xpath("//h4[normalize-space()='Documents']");
//        int attempts = 0;
//
//        while (attempts < 3) {
//            try {
//                WebElement documentsMenu = hooks.getWait().until(
//                        ExpectedConditions.elementToBeClickable(documentsMenuBy)
//                );
//
//                ((JavascriptExecutor) hooks.getDriver())
//                        .executeScript("arguments[0].scrollIntoView({block: 'center'});", documentsMenu);
//
//                // Try normal click first
//                try {
//                    documentsMenu.click();
//                } catch (ElementClickInterceptedException e) {
//                    // Fallback to JS click if normal click fails
//                    ((JavascriptExecutor) hooks.getDriver())
//                            .executeScript("arguments[0].click();", documentsMenu);
//                }
//                waitForLoaderToDisappear(); // wait for page to load after click
//                break; // exit loop if click succeeds
//            } catch (StaleElementReferenceException e) {
//                attempts++;
//                if (attempts == 3) {
//                    throw e; // rethrow after 3 failed attempts
//                }
//            }
//        }
//    }
//
//
//    public void waitForLoaderToDisappear() {
//        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
//    }
//
//    public void verifyAndClickCreateNewDocumentButton() {
//        // Locator for the "Create New Document" button
//
//        try {
//            Thread.sleep(35000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        By createNewDocBtnBy = By.xpath("//button[.//span[contains(text(),'Create New Document')]]");
//
//        // Wait for loader/spinner to disappear if any
//        waitForLoaderToDisappear();
//
//        try {
//            // Wait for visibility
//            WebElement button = hooks.getWait().until(
//                    ExpectedConditions.visibilityOfElementLocated(createNewDocBtnBy)
//            );
//
//            // Scroll button into view
//            ((JavascriptExecutor) hooks.getDriver())
//                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
//
//            // Wait until clickable
//            hooks.getWait().until(ExpectedConditions.elementToBeClickable(button));
//
//            // Click using JS (safer for Angular apps)
//            ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", button);
//
//            System.out.println("Create New Document button is visible and clicked successfully.");
//        } catch (TimeoutException e) {
//            throw new RuntimeException("Create New Document button is not visible or clickable!", e);
//        }
//    }
//
//
//    public void clickCreateNewDocumentButton() {
//
//        // Optional: wait a bit if API/Angular is slow
//        try {
//            Thread.sleep(45000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Wait for any loader/spinner to disappear
//        waitForLoaderToDisappear();
//
//        // Correct XPath that targets the span text inside the button
//        //By buttonBy = By.xpath("//button[.//span[contains(text(),'Create New Document')]]");
//        By buttonBy = By.xpath("//span[i[contains(@class,'bi-plus')] and contains(., 'Create New Document')]");
//
//        // Retry a few times if stale
//        int attempts = 0;
//        while (attempts < 3) {
//            try {
//                WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(buttonBy));
//
//                // Scroll into view
//                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", button);
//
//                // Click
//                button.click();
//                System.out.println("Create Newwwwwww");
//                break; // success
//            } catch (StaleElementReferenceException e) {
//                attempts++;
//            }
//        }
//    }
//
//
//    public void isUploadDocumentOptionVisible() {
//        try {
//            // Wait until the button is visible (but not necessarily clickable)
//            WebElement uploadBtn = hooks.getWait().until(
//                    ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy1)
//            );
//
//            // Scroll into view just for safety
//            ((JavascriptExecutor) hooks.getDriver())
//                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", uploadBtn);
//
//            uploadBtn.isDisplayed();
//        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException ignored) {
//        }
//    }
//
//
//    public void verifyDocumentTemplatesPageIsVisible() {
//
//
//        // Wait for loader to disappear first (if exists)
//        waitForLoaderToDisappear();
//
//        // Retry with JS check if element is present and visible
//        int attempts = 0;
//        while (attempts < 3) {
//            try {
//                WebElement container = hooks.getWait().until(
//                        ExpectedConditions.visibilityOfElementLocated(documentTemplatesPageBy)
//                );
//                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", container);
//                assertTrue(container.isDisplayed(), "Document Templates page is not visible");
//                break;
//            } catch (StaleElementReferenceException | TimeoutException e) {
//                attempts++;
//            }
//        }
//    }
//
//
//    public void scrollPageUp() {
//        ((JavascriptExecutor) hooks.getDriver())
//                .executeScript("window.scrollTo({ top: 0, behavior: 'smooth' });");
//    }
//
//    public void clickUploadDocumentOption() {
//        // Wait for any loader/spinner to disappear
//        waitForLoaderToDisappear();
//
//        try {
//            System.out.println("Thread upload");
//            Thread.sleep(25000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        int attempts = 0;
//        while (attempts < 3) {
//            try {
//                // Wait for visibility first
//                WebElement uploadBtn = hooks.getWait().until(
//                        ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy)
//                );
//
//                // Scroll to center
//                ((JavascriptExecutor) hooks.getDriver())
//                        .executeScript("arguments[0].scrollIntoView({block: 'center'});", uploadBtn);
//
//                // Wait a bit more until clickable
//                hooks.getWait().until(ExpectedConditions.elementToBeClickable(uploadBtn));
//
//                // Click via JS to avoid intercepted click
//                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", uploadBtn);
//
//                break; // success
//            } catch (TimeoutException | StaleElementReferenceException e) {
//                attempts++;
//                if (attempts == 3) {
//                    throw e; // fail after 3 retries
//                }
//                // small wait before retry
//                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
//            }
//        }
//    }
//
//
//
//    public void verifyUploadNewDocumentWindowIsVisible() {
//        WebElement popup = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(uploadNewDocWindowBy));
//        assertTrue(popup.isDisplayed(), "Upload New Document window is not visible");
//    }
//
//    // Fill document form example
//    public void fillDocumentForm(String title, String jurisdiction, String type) {
//        // Title field
//        By titleFieldBy = By.cssSelector("input[formcontrolname='title']");
//        WebElement titleField = hooks.getWait().until(ExpectedConditions.elementToBeClickable(titleFieldBy));
//        titleField.clear();
//        titleField.sendKeys(title);
//
//        // Jurisdiction select
//        By jurisdictionSelectBy = By.cssSelector("mat-select[formcontrolname='jurisdiction']");
//        selectMatSelectOption(jurisdictionSelectBy, jurisdiction);
//
//        // Type select
//        By typeSelectBy = By.cssSelector("mat-select[formcontrolname='type']");
//        selectMatSelectOption(typeSelectBy, type);
//    }
//
//    public void submitNewDocument() {
//        By createBtnBy = By.xpath("//button[normalize-space()='Create']");
//        WebElement createBtn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(createBtnBy));
//        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", createBtn);
//        createBtn.click();
//        waitForLoaderToDisappear();
//    }
//
//    // Helper for mat-select dropdowns
//    private void selectMatSelectOption(By selectBy, String optionText) {
//        WebElement select = hooks.getWait().until(ExpectedConditions.elementToBeClickable(selectBy));
//        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", select);
//        select.click();
//
//        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", optionText));
//        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
//        option.click();
//    }
//
//    public void isUploadNewDocumentWindowDisplayed() {
//
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int attempts = 0;
//
//        while (attempts < 3) {
//            try {
//                WebElement title = hooks.getWait()
//                        .until(ExpectedConditions.visibilityOfElementLocated(uploadNewDocumentTitleBy));
//
//                assertTrue(title.isDisplayed(),
//                        "Upload New Document popup title is not visible");
//
//                return; // success
//            } catch (StaleElementReferenceException | TimeoutException e) {
//                attempts++;
//                if (attempts == 3) throw e;
//            }
//        }
//    }
//
//
//    // -------------------------------------
//    // Popup Field Methods
//    // -------------------------------------
//
//    public void enterDocumentTitle(String title) {
//        waitForLoaderToDisappear();
//        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docTitleInputBy));
//        input.clear();
//        input.sendKeys(title);
//    }
//
//    public void selectDocumentJurisdiction(String jurisdiction) {
//        waitForLoaderToDisappear();
//        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docJurisdictionDropdownBy));
//        dropdown.click();
//
//        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", jurisdiction));
//        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
//        option.click();
//    }
//
//    public void selectDocumentType(String type) {
//        waitForLoaderToDisappear();
//        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docTypeDropdownBy));
//        dropdown.click();
//
//        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", type));
//        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
//        option.click();
//    }
//
//    public void addRecipient(String email) {
//        waitForLoaderToDisappear();
//        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docRecipientInputBy));
//        input.sendKeys(email);
//        input.sendKeys(Keys.ENTER);
//    }
//
//    public void uploadDocumentFile(String fileRelativePath) {
//        waitForLoaderToDisappear();
//
//        String fullPath = System.getProperty("user.dir")
//                + "/src/test/resources/testFiles/"
//                + fileRelativePath;
//
//        WebElement uploadInput = hooks.getWait()
//                .until(ExpectedConditions.presenceOfElementLocated(docFileUploadInputBy));
//
//        uploadInput.sendKeys(fullPath);
//
//        try { Thread.sleep(2000); } catch (Exception ignored) {}
//    }
//
//    // -------------------------------------
//    // Create Button
//    // -------------------------------------
//
//    public void clickDocumentCreateButton() {
//
//        By createBtn = By.xpath("//button[normalize-space()='Create']");
//
//        int attempts = 0;
//
//        while (attempts < 3) {
//            try {
//                waitForLoaderToDisappear();
//
//                WebElement btn = hooks.getWait()
//                        .until(ExpectedConditions.elementToBeClickable(createBtn));
//
//                ((JavascriptExecutor) hooks.getDriver())
//                        .executeScript("arguments[0].scrollIntoView(true);", btn);
//
//                ((JavascriptExecutor) hooks.getDriver())
//                        .executeScript("arguments[0].click();", btn);
//
//                waitForLoaderToDisappear();
//                return;
//
//            } catch (StaleElementReferenceException | TimeoutException e) {
//                attempts++;
//                if (attempts == 3) throw e;
//            }
//        }
//    }
//
//    public void clickLetsGoButtonAfterProcessing() {
//
//
//        // 1️⃣ Wait until the success text appears
//        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(processedTextBy));
//
//        System.out.println("Document processed message appeared.");
//
//        // 2️⃣ Wait for “Let's Go” button to be visible + clickable
//        WebElement letsGoBtn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(letsGoBtnBy));
//
//        // 3️⃣ Scroll & JS Click
//        ((JavascriptExecutor) hooks.getDriver())
//                .executeScript("arguments[0].scrollIntoView(true);", letsGoBtn);
//
//        ((JavascriptExecutor) hooks.getDriver())
//                .executeScript("arguments[0].click();", letsGoBtn);
//
//        System.out.println("Clicked Let's Go button.");
//
//        // 4️⃣ After clicking, wait 1 minute for document to fully load
//        try {
//            Thread.sleep(60000); // 60 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Document load wait completed.");
//    }
//
//
//    // -------------------------------------
//    // Wait for popup close
//    // -------------------------------------
//    public void waitForDocumentPopupToClose() {
//        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(popupContainerBy));
//    }
//}

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

    // ---------------- Locators ----------------
    private final By createNewDocBtnBy = By.xpath("//button[.//text()='Create New Document']");
    private final By documentTemplatesPageBy = By.cssSelector(".template-view-container");

    private final By uploadDocumentBtnBy = By.xpath("//button[contains(@class,'plus-icon-button')]//i[contains(@class,'bi-upload')]");
    private final By uploadDocumentBtnBy1 = By.cssSelector("button.plus-icon-button[aria-label='Upload new']");

    private final By loaderBy = By.cssSelector(".loading-overlay");
    private final By popupContainerBy = By.xpath("//mat-dialog-container");

    private final By docTitleInputBy = By.xpath("//input[@formcontrolname='title']");
    private final By docJurisdictionDropdownBy = By.xpath("//mat-select[@formcontrolname='jurisdiction']");
    private final By docTypeDropdownBy = By.xpath("//mat-select[@formcontrolname='type']");
    private final By docRecipientInputBy = By.xpath("//input[@placeholder='Start by typing email']");
    private final By docFileUploadInputBy = By.xpath("//input[@type='file']");

    private final By docCreateBtnBy = By.xpath("//button[contains(@class,'create-button')]");
    private final By docCreateEnabledBtnBy = By.xpath("//button[contains(@class,'la-btn__filled') and normalize-space()='Create']");

    private final By uploadNewDocWindowBy = By.cssSelector("legify-ai-upload-document-template-popup");
    private final By uploadNewDocumentTitleBy = By.xpath("//div[contains(@class,'title-padding') and normalize-space()='Upload New Document']");

    private final By processedTextBy = By.xpath("//p[contains(@class,'loading-text') and contains(text(),'successfully processed')]");
    private final By letsGoBtnBy = By.xpath("//button[normalize-space()=\"Let's Go\"]");


    // ---------------- Utility methods ----------------
    private void safeSleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    private void jsScrollToCenter(WebElement element) {
        ((JavascriptExecutor) hooks.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", element);
    }

    private void retryElementAction(Runnable action) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                action.run();
                return;
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
                if (attempts == 3) throw e;
                safeSleep(800);
            }
        }
    }

    public void waitForLoaderToDisappear() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
    }

    // ---------------- Navigate to Document module ----------------
    public void navigateToDocumentModule() {
        waitForLoaderToDisappear();

        By documentsMenuBy = By.xpath("//h4[normalize-space()='Documents']");
        retryElementAction(() -> {
            WebElement docMenu = hooks.getWait().until(ExpectedConditions.elementToBeClickable(documentsMenuBy));
            jsScrollToCenter(docMenu);
            try {
                docMenu.click();
            } catch (Exception e) {
                jsClick(docMenu);
            }
            waitForLoaderToDisappear();
        });
    }

    // ---------------- Create New Document Button ----------------
    public void verifyAndClickCreateNewDocumentButton() {
        safeSleep(35000);
        waitForLoaderToDisappear();

        By btnBy = By.xpath("//button[.//span[contains(text(),'Create New Document')]]");

        retryElementAction(() -> {
            WebElement button = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(btnBy));
            jsScrollToCenter(button);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(button));
            jsClick(button);
        });
    }

    public void clickCreateNewDocumentButton() {
        safeSleep(45000);
        waitForLoaderToDisappear();

        By buttonBy = By.xpath("//span[i[contains(@class,'bi-plus')] and contains(., 'Create New Document')]");

        retryElementAction(() -> {
            WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(buttonBy));
            jsScrollToCenter(button);
            button.click();
        });
    }

    // ---------------- Upload Document Option ----------------
    public void isUploadDocumentOptionVisible() {
        try {
            WebElement uploadBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy1));
            jsScrollToCenter(uploadBtn);
            uploadBtn.isDisplayed();
        } catch (Exception ignored) {}
    }

    public void clickUploadDocumentOption() {
        waitForLoaderToDisappear();
        safeSleep(25000);

        retryElementAction(() -> {
            WebElement uploadBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy));
            jsScrollToCenter(uploadBtn);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(uploadBtn));
            jsClick(uploadBtn);
        });
    }

    // ---------------- Template & Popup ----------------
    public void verifyDocumentTemplatesPageIsVisible() {
        waitForLoaderToDisappear();

        retryElementAction(() -> {
            WebElement container = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(documentTemplatesPageBy));
            jsScrollToCenter(container);
            assertTrue(container.isDisplayed(), "Document Templates page is not visible");
        });
    }

    public void verifyUploadNewDocumentWindowIsVisible() {
        WebElement popup = hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(uploadNewDocWindowBy));
        assertTrue(popup.isDisplayed(), "Upload New Document window is not visible");
    }

    public void isUploadNewDocumentWindowDisplayed() {
        safeSleep(15000);

        retryElementAction(() -> {
            WebElement title = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadNewDocumentTitleBy));
            assertTrue(title.isDisplayed(), "Upload New Document popup title is not visible");
        });
    }

    // ---------------- Popup Fields ----------------
    public void enterDocumentTitle(String title) {
        waitForLoaderToDisappear();
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docTitleInputBy));
        input.clear();
        input.sendKeys(title);
    }

    public void selectDocumentJurisdiction(String jurisdiction) {
        waitForLoaderToDisappear();
        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docJurisdictionDropdownBy));
        dropdown.click();

        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", jurisdiction));
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy)).click();
    }

    public void selectDocumentType(String type) {
        waitForLoaderToDisappear();
        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docTypeDropdownBy));
        dropdown.click();

        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", type));
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy)).click();
    }

    public void addRecipient(String email) {
        waitForLoaderToDisappear();
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docRecipientInputBy));
        input.sendKeys(email);
        input.sendKeys(Keys.ENTER);
    }

    public void uploadDocumentFile(String fileRelativePath) {
        waitForLoaderToDisappear();

        String fullPath = System.getProperty("user.dir")
                + "/src/test/resources/testFiles/"
                + fileRelativePath;

        WebElement uploadInput = hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(docFileUploadInputBy));
        uploadInput.sendKeys(fullPath);

        safeSleep(2000);
    }

    // ---------------- Create Button ----------------
    public void clickDocumentCreateButton() {
        By createBtn = By.xpath("//button[normalize-space()='Create']");

        retryElementAction(() -> {
            waitForLoaderToDisappear();
            WebElement btn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(createBtn));
            jsScrollToCenter(btn);
            jsClick(btn);
            waitForLoaderToDisappear();
        });
    }

    // ---------------- After Processing ----------------
    public void clickLetsGoButtonAfterProcessing() {
        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(processedTextBy));

        WebElement letsGoBtn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(letsGoBtnBy));
        jsScrollToCenter(letsGoBtn);
        jsClick(letsGoBtn);

        safeSleep(60000);
    }

    // ---------------- Popup Close ----------------
    public void waitForDocumentPopupToClose() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(popupContainerBy));
    }
}

