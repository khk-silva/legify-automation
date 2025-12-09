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
    And I submit the new document
#    Then the document should be created successfully


#  @ShareDocument
#  Scenario: Share a document with a collaborator
#    Given a document is already created
#    When I share the document with collaborator "john@example.com"
#    Then the collaborator should receive access to the document
#
#  @AddSignatureAndShare
#  Scenario: Add signature card and share document to recipient
#    Given a document is already created
#    And I add a signature card for the user "Hiruni"
#    When I share the signed document to recipient "test@mail.com"
#    Then the recipient should receive the document for signing
