@DocumentModule
Feature: Document Module
  As a user
  I want to create, share and sign documents
  So that document workflows can be completed smoothly

  @CreateDocument
  Scenario: Create a document by uploading a document template
    Given I am logged in with valid credentials4
    When I navigate to the Document module
    Then I should see the "Create New Document" button
#    When I click the "Create New Document" button
    Then I should see the Document Templates page
    When I click the Upload Document option
    Then I should see the "Upload Document" window
    When I fill the document form with test data "NewUploadDoc1"
    And I create the new document
    And I click the Let's go Button
#    Then the document should be created successfully


  @ShareDocument
  Scenario: Share a document with a collaborator
    Given I have a document NewUploadDoc1 created
    When I click the Collaborate button
    Then the Collaborate popup should be displayed
    When I add collaborator "collaboratorKey"
    And I click the Invite button
#    Then the collaborator added snackbar message should be displayed


  @AddSignatureAndShare
  Scenario: Add signature card and share document to recipient
    Given I have a document NewUploadDoc1 created
    When I drag and drop the Signature element into the document editor
    And I click on the Signature element
    Then the Signature card popup should be displayed
    When I enter recipient  details "recipientKey"
    And I click the Assign button
##    Then the signature assigned snackbar message should be displayed
