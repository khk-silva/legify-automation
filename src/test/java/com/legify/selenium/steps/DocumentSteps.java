package com.legify.selenium.steps;

import com.legify.selenium.helpers.JsonReader;
import com.legify.selenium.pages.DocumentPage;
import com.legify.selenium.pages.LoginPage;
import com.legify.selenium.runners.Hook;
import com.legify.selenium.helpers.JsonReader;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentSteps {

    @Autowired
    private Hook hooks;

    @Autowired
    private DocumentPage documentPage;

    @Autowired
    LoginPage loginPage;


    @Given("I am logged in with valid credentials4")
    public void iAmLoggedIn() {
        String username = JsonReader.getUsername("validUser");
        String password = JsonReader.getPassword("validUser");


        loginPage.loginToApp(username, password);
    }

    @When("I navigate to the Document module")
    public void navigateToDocumentModule() {
        documentPage.navigateToDocumentModule();
    }

    @Then("I should see the {string} button")
    public void iShouldSeeButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Create New Document")) {
          //  documentPage.waitForLoaderToDisappear();
            documentPage.verifyAndClickCreateNewDocumentButton();
        }
    }

    @When("I click the {string} button")
    public void clickButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Create New Document")) {
            documentPage.clickCreateNewDocumentButton();
        }
    }

    @Then("I should see the Document Templates page")
    public void verifyDocumentTemplatesPage() {
        //documentPage.verifyDocumentTemplatesPageIsVisible();
        documentPage.isUploadDocumentOptionVisible();
    }

    @When("I click the Upload Document option")
    public void clickUploadDocument() {
        documentPage.clickUploadDocumentOption();
    }

    @Then("I should see the {string} window")
    public void verifyUploadNewDocumentWindow(String windowName) {
        if (windowName.equalsIgnoreCase("Upload Document")) {
            documentPage.isUploadNewDocumentWindowDisplayed();
        }
    }



    @When("I fill the document form with test data {string}")
    public void fillDocumentFormWithJsonData(String documentKey) {

        String title = JsonReader.getDocumentData(documentKey, "title");
        String jurisdiction = JsonReader.getDocumentData( documentKey, "jurisdiction");
        String type = JsonReader.getDocumentData( documentKey, "type");
        String recipient = JsonReader.getDocumentData( documentKey, "recipient");
        String fileName = JsonReader.getDocumentData( documentKey, "fileName");

       documentPage.enterDocumentTitle(title);
       documentPage.selectDocumentJurisdiction(jurisdiction);
       documentPage.selectDocumentType(type);
       documentPage.uploadDocumentFile(fileName);
    }


    @And("I create the new document")
    public void submitNewDocument() {
        documentPage.clickDocumentCreateButton();
    }

    @And("I click the Let's go Button")
    public void clickOnLetsGoButton() {
        documentPage.clickLetsGoButtonAfterProcessing();
    }

//    @Then("the document should be created successfully")
//    public void verifyDocumentCreation() {
//        // Optional: Add verification if document exists in template list
//        System.out.println("Document creation verified");
//        documentPage.waitForDocumentPopupToClose();
//    }

    // --------------------- SHARE DOCUMENT SCENARIO ---------------------


    @Given("I have a document NewUploadDoc1 created")
    public void verifyDocumentCreation() {
        documentPage.waitForDocumentPopupToClose();
    }

    @When("I click the Collaborate button")
    public void clickCollaborateButton() {
        documentPage.clickCollaborateButton();
    }

    @Then("the Collaborate popup should be displayed")
    public void verifyCollaboratePopup() {
        documentPage.verifyCollaboratePopupDisplayed();
    }

    @When("I add collaborator {string}")
    public void addCollaborator(String collaboratorKey) {
        String collaboratorEmail = JsonReader.getCollaboratorData( collaboratorKey, "collaboratorEmail");
        documentPage.addCollaboratorEmail(collaboratorEmail);
    }

    @And("I click the Invite button")
    public void clickInviteButton() {
        documentPage.clickInviteButton();
    }

    @Then("the collaborator added snackbar message should be displayed")
    public void verifyCollaboratorAddedSnackbar() {
        documentPage.verifySnackbarMessageDisplayed();
    }

    /////////////////////////

    @When("I drag and drop the Signature element into the document editor")
    public void dragAndDropSignature() {
        documentPage.dragAndDropSignatureIntoEditor();
    }

    @And("I click on the Signature element")
    public void clickSignatureElement() {
        documentPage.clickSignatureElement();
    }

    @Then("the Signature card popup should be displayed")
    public void verifySignatureCardPopup() {
        documentPage.verifySignatureCardPopupDisplayed();
    }

    @When("I enter recipient  details {string}")
    public void enterRecipientNameAndEmail(String recipientKey) {
        String recipientEmail = JsonReader.getRecipientData(recipientKey, "recipientEmail");
        String recipientName = JsonReader.getRecipientData(recipientKey, "name");

        documentPage.addRecipientName(recipientEmail);
        documentPage.addRecipientName(recipientName);
    }

    @And("I click the Assign button")
    public void clickAssignButton() {
        documentPage.clickAssignButton();
    }

    @Then("the signature assigned snackbar message should be displayed")
    public void verifySignatureAssignedSnackbar() {
        documentPage.verifySignatureAssignedSnackbar();
    }
}

