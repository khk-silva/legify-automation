package com.legify.selenium.pages;

import com.legify.selenium.helpers.VisibilityHelper;
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
    private VisibilityHelper visibilityHelper;

    @Autowired
    public DocumentPage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }

    // ---------------- Locators ----------------

    private final By uploadDocumentBtnBy = By.xpath("//button[contains(@class,'plus-icon-button')]//i[contains(@class,'bi-upload')]");
    private final By uploadDocumentBtnBy1 = By.cssSelector("button.plus-icon-button[aria-label='Upload new']");

      private final By popupContainerBy = By.xpath("//mat-dialog-container");

    private final By docTitleInputBy = By.xpath("//input[@formcontrolname='title']");
    private final By docJurisdictionDropdownBy = By.xpath("//mat-select[@formcontrolname='jurisdiction']");
    private final By docTypeDropdownBy = By.xpath("//mat-select[@formcontrolname='type']");
    private final By docRecipientInputBy = By.xpath("//input[@placeholder='Start by typing email']");
    private final By docFileUploadInputBy = By.xpath("//input[@type='file']");

    private final By uploadNewDocumentTitleBy = By.xpath("//div[contains(@class,'title-padding') and normalize-space()='Upload New Document']");

    private final By processedTextBy = By.xpath("//p[contains(@class,'loading-text') and contains(text(),'successfully processed')]");
    private final By letsGoBtnBy = By.xpath("//button[normalize-space()=\"Let's Go\"]");

    // ---------------- Share Document Locators ----------------

    private final By documentCardBy = By.xpath("//div[contains(@class,'doc-card')]");  // dummy
    private final By collaborateBtnBy = By.xpath("//button[.//span[contains(text(),'Collaborate')]]");
    private final By collaboratePopupBy = By.cssSelector("mat-dialog-container invite-collaborators-popup");
    private final By collaboratorEmailInputBy = By.xpath("//input[@placeholder='Search for Collaborators']");
    private final By inviteBtnBy = By.xpath("//mat-dialog-container//button[normalize-space()='Invite']");
    private final By snackbarMessageBy = By.xpath("//simple-snack-bar//span"); // dummy


    // ---------------- Signature & Stamp Locators ----------------

    // "Signature and Stamp" element in the side menu
    private final By signatureElementBy = By.xpath("//span[text()='Signature and Stamp']");

    // Document editor drop area
    private final By documentEditorBy = By.cssSelector("div.empty-state.mt-1");

    // Signature card popup title ("Assign New Signer")
    private final By signatureCardPopupBy = By.xpath("//span[text()='Assign New Signer']");

    // Recipient Name input
    private final By recipientNameInputBy = By.xpath("//input[@placeholder='John Doe']");

    // Recipient Email input
    private final By recipientEmailInputBy = By.xpath("//input[@placeholder='Start by typing email']");

    // Assign Button
    private final By assignBtnBy = By.xpath("//button[normalize-space()='Assign']");

    // Snackbar message after assigning signature
    private final By signatureSnackbarBy = By.xpath("//simple-snack-bar//span[contains(text(),'assigned')]");


    // ---------------- Navigate to Document module ----------------
    public void navigateToDocumentModule() {
        visibilityHelper.waitForLoaderToDisappear();

        By documentsMenuBy = By.xpath("//h4[normalize-space()='Documents']");
        visibilityHelper.retryElementAction(() -> {
            WebElement docMenu = hooks.getWait().until(ExpectedConditions.elementToBeClickable(documentsMenuBy));
            visibilityHelper.jsScrollToCenter(docMenu);
            try {
                docMenu.click();
            } catch (Exception e) {
                visibilityHelper.jsClick(docMenu);
            }
            visibilityHelper.waitForLoaderToDisappear();
        });
    }

    // ---------------- Create New Document Button ----------------
    public void verifyAndClickCreateNewDocumentButton() {
        visibilityHelper.safeSleep(25000);
        visibilityHelper.waitForLoaderToDisappear();

        By btnBy = By.xpath("//button[.//span[contains(text(),'Create New Document')]]");

        visibilityHelper.retryElementAction(() -> {
            WebElement button = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(btnBy));
            visibilityHelper.jsScrollToCenter(button);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(button));
            visibilityHelper.jsClick(button);
        });
    }

    public void clickCreateNewDocumentButton() {
        visibilityHelper.safeSleep(15000);
        visibilityHelper.waitForLoaderToDisappear();

        By buttonBy = By.xpath("//span[i[contains(@class,'bi-plus')] and contains(., 'Create New Document')]");

        visibilityHelper.retryElementAction(() -> {
            WebElement button = hooks.getWait().until(ExpectedConditions.elementToBeClickable(buttonBy));
            visibilityHelper.jsScrollToCenter(button);
            button.click();
        });
    }

    // ---------------- Upload Document Option ----------------
    public void isUploadDocumentOptionVisible() {
        try {
            WebElement uploadBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy1));
            visibilityHelper.jsScrollToCenter(uploadBtn);
            uploadBtn.isDisplayed();
        } catch (Exception ignored) {}
    }

    public void clickUploadDocumentOption() {
        visibilityHelper.waitForLoaderToDisappear();
        visibilityHelper.safeSleep(10000);

        visibilityHelper.retryElementAction(() -> {
            WebElement uploadBtn = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadDocumentBtnBy));
            visibilityHelper.jsScrollToCenter(uploadBtn);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(uploadBtn));
            visibilityHelper.jsClick(uploadBtn);
        });
    }
    


    public void isUploadNewDocumentWindowDisplayed() {
        visibilityHelper.safeSleep(5000);

        visibilityHelper.retryElementAction(() -> {
            WebElement title = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(uploadNewDocumentTitleBy));
            assertTrue(title.isDisplayed(), "Upload New Document popup title is not visible");
        });
    }

    // ---------------- Popup Fields ----------------
    public void enterDocumentTitle(String title) {
        visibilityHelper.waitForLoaderToDisappear();
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docTitleInputBy));
        input.clear();
        input.sendKeys(title);
    }

    public void selectDocumentJurisdiction(String jurisdiction) {
        visibilityHelper.waitForLoaderToDisappear();
        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docJurisdictionDropdownBy));
        dropdown.click();

        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", jurisdiction));
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy)).click();
    }

    public void selectDocumentType(String type) {
        visibilityHelper.waitForLoaderToDisappear();
        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docTypeDropdownBy));
        dropdown.click();

        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", type));
        hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy)).click();
    }

    public void addRecipient(String email) {
        visibilityHelper.waitForLoaderToDisappear();
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(docRecipientInputBy));
        input.sendKeys(email);
        input.sendKeys(Keys.ENTER);
    }

    public void uploadDocumentFile(String fileRelativePath) {
        visibilityHelper.waitForLoaderToDisappear();

        String fullPath = System.getProperty("user.dir")
                + "/src/test/resources/testFiles/"
                + fileRelativePath;

        WebElement uploadInput = hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(docFileUploadInputBy));
        uploadInput.sendKeys(fullPath);

        visibilityHelper.safeSleep(2000);
    }

    // ---------------- Create Button ----------------
    public void clickDocumentCreateButton() {
        By createBtn = By.xpath("//button[normalize-space()='Create']");

        visibilityHelper.retryElementAction(() -> {
            visibilityHelper.waitForLoaderToDisappear();
            WebElement btn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(createBtn));
            visibilityHelper.jsScrollToCenter(btn);
            visibilityHelper.jsClick(btn);
            visibilityHelper.waitForLoaderToDisappear();
        });
    }

    // ---------------- After Processing ----------------
    public void clickLetsGoButtonAfterProcessing() {
        hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(processedTextBy));

        WebElement letsGoBtn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(letsGoBtnBy));
        visibilityHelper.jsScrollToCenter(letsGoBtn);
        visibilityHelper.jsClick(letsGoBtn);

        visibilityHelper.safeSleep(15000);
    }

    // ---------------- Popup Close ----------------
    public void waitForDocumentPopupToClose() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(popupContainerBy));
    }


    public void verifyDocumentIsDisplayed() {
        visibilityHelper.retryElementAction(() -> {
            WebElement doc = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(documentCardBy)
            );
            assertTrue(doc.isDisplayed(), "Document card is not visible");
        });
    }
    public void clickCollaborateButton() {
        visibilityHelper.safeSleep(15000);
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement collabBtn = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(collaborateBtnBy)
            );
            visibilityHelper.jsScrollToCenter(collabBtn);
            visibilityHelper.jsClick(collabBtn);
            System.out.println("Collab button clicked");
        });
    }

    public void verifyCollaboratePopupDisplayed() {
        visibilityHelper.safeSleep(2000);
        visibilityHelper.retryElementAction(() -> {
            WebElement popup = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(collaboratePopupBy)
            );
            assertTrue(popup.isDisplayed(), "Collaborate popup is not visible");
        });
    }

    public void addCollaboratorEmail(String email) {
        visibilityHelper.safeSleep(2000);
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(collaboratorEmailInputBy)
            );
            input.sendKeys(email);
            input.sendKeys(Keys.ENTER);
        });
    }

    public void clickInviteButton() {
        visibilityHelper.safeSleep(5000);
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement btn = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(inviteBtnBy)
            );
            visibilityHelper.jsScrollToCenter(btn);
            visibilityHelper.jsClick(btn);
            visibilityHelper.safeSleep(15000);
        });
    }

    public void verifySnackbarMessageDisplayed() {
        visibilityHelper.retryElementAction(() -> {
            WebElement snack = hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(snackbarMessageBy)
            );
            assertTrue(snack.isDisplayed(), "Snackbar message is not visible");
        });
    }


    /**
     * Scrolls the "sectionList" container fully to the top.
     */
    public void scrollSectionListToTop() {
        try {
            WebElement sectionList = hooks.getDriver().findElement(By.id("sectionList"));
            ((JavascriptExecutor) hooks.getDriver())
                    .executeScript("arguments[0].scrollTop = 0;", sectionList);
            visibilityHelper.safeSleep(500);
        } catch (Exception e) {
            System.out.println("Failed to scroll sectionList to top: " + e.getMessage());
        }
    }



    // ---------------- Drag & Drop Signature ----------------
    public void dragAndDropSignatureIntoEditor() {
        scrollSectionListToTop();
        visibilityHelper.safeSleep(15000);
        visibilityHelper.retryElementAction(() -> {
            WebElement signature = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(signatureElementBy));
            WebElement editor = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(documentEditorBy));

            new org.openqa.selenium.interactions.Actions(hooks.getDriver())
                    .dragAndDrop(signature, editor)
                    .perform();
        });
    }

    // ---------------- Click Signature Element ----------------
    public void clickSignatureElement() {
        visibilityHelper.safeSleep(15000);
        visibilityHelper.retryElementAction(() -> {
            WebElement signature = hooks.getWait().until(ExpectedConditions.elementToBeClickable(signatureElementBy));
            visibilityHelper.jsScrollToCenter(signature);
            signature.click();
        });
    }

    // ---------------- Verify Signature Card Popup ----------------
    public void verifySignatureCardPopupDisplayed() {
        visibilityHelper.safeSleep(15000);
        visibilityHelper.retryElementAction(() -> {
            WebElement popup = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(signatureCardPopupBy));
            assertTrue(popup.isDisplayed(), "Signature Card popup is not visible");
        });
    }

    // ---------------- Enter Recipient Name & Email ----------------

    public void addRecipientName(String name) {
        visibilityHelper.safeSleep(5000);
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(recipientNameInputBy)
            );
            input.sendKeys(name);
            input.sendKeys(Keys.ENTER);
        });
    }

    public void addRecipientEmail(String email) {
        visibilityHelper.safeSleep(5000);
        visibilityHelper.waitForLoaderToDisappear();

        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(
                    ExpectedConditions.elementToBeClickable(recipientEmailInputBy)
            );
            input.sendKeys(email);
            input.sendKeys(Keys.ENTER);
        });
    }

    // ---------------- Click Assign Button ----------------
    public void clickAssignButton() {
        visibilityHelper.safeSleep(15000);
        visibilityHelper.retryElementAction(() -> {
            WebElement btn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(assignBtnBy));
            visibilityHelper.jsScrollToCenter(btn);
            visibilityHelper.jsClick(btn);
        });
    }

    // ---------------- Verify Signature Snackbar ----------------
    public void verifySignatureAssignedSnackbar() {
        visibilityHelper.retryElementAction(() -> {
            WebElement snack = hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(signatureSnackbarBy));
            assertTrue(snack.isDisplayed(), "Signature assigned snackbar message is not visible");
        });
    }


}

