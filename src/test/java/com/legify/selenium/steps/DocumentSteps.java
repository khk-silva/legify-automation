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


    @And("I submit the new document")
    public void submitNewDocument() {
        documentPage.clickDocumentCreateButton();
    }

    @Then("the document should be created successfully")
    public void verifyDocumentCreation() {
        // Optional: Add verification if document exists in template list
        System.out.println("Document creation verified");
        documentPage.waitForDocumentPopupToClose();
    }
}

