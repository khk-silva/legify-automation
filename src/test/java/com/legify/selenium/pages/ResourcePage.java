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


    // Modal fields
    private final By titleInputBy = By.xpath("//input[@formcontrolname='title']");
    private final By typeDropdownBy = By.xpath("//mat-select[@formcontrolname='type']");
    private final By typeOptionByTemplate = By.xpath("//mat-option//span[normalize-space()='%s']");

    private final By applicableDocInputBy = By.xpath("//el-legify-async-autocomplete//input[@matinput]");
    private final By jurisdictionDropdownBy = By.xpath("//mat-select[@formcontrolname='jurisdiction']");
    private final By tagsInputBy = By.xpath("//input[@formcontrolname='tags']");
    private final By fileUploadInputBy = By.id("uploadFile");
    //private final By createButtonBy = By.xpath("//button[contains(@class,'create-button')]");
  //  private final By createBtnBy = By.xpath("//button[normalize-space()='Create']");
    private final By createBtnBy = By.xpath("//span[@class='button-with-spinner' and contains(.,'Create')]");





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

    public void isResourceCreationWindowDisplayed() {
        hooks.getWait().until(ExpectedConditions.visibilityOf(createNewResourceButton)).isDisplayed();
    }


    // -----------------------
    // WAIT FOR LOADER
    // -----------------------
    public void waitForLoaderToDisappear() {
        hooks.getWait().until(ExpectedConditions.invisibilityOfElementLocated(loaderBy));
    }


    // -----------------------
    // FORM INTERACTIONS
    // -----------------------

    public void enterTitle(String title) {
        waitForLoaderToDisappear();
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(titleInputBy));
        input.clear();
        input.sendKeys(title);
    }

    public void selectType(String type) {
        waitForLoaderToDisappear();
        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(typeDropdownBy));
        dropdown.click();
        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", type));
        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
        option.click();
    }

    public void selectApplicableDocumentType(String docType) {
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(applicableDocInputBy));
        input.sendKeys(docType);
        input.sendKeys(Keys.ENTER);
    }

    public void selectJurisdiction(String jurisdiction) {
        WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(jurisdictionDropdownBy));
        dropdown.click();
        By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", jurisdiction));
        WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
        option.click();
    }

    public void addTag(String tag) {
        WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(tagsInputBy));
        input.sendKeys(tag);
        input.sendKeys(Keys.ENTER);
    }

    // Upload a document using file input
    public void uploadDocument(String fileName) {
        waitForLoaderToDisappear();

        // Construct absolute path if your files are in a resources folder
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testFiles/" + fileName;

        WebElement uploadInput = hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.id("uploadFile"))
        );

        // Upload file
        uploadInput.sendKeys(filePath);

        // Optional: wait a bit for file to be processed
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void clickCreateButton() {

        By enabledButton = By.xpath("//button[contains(@class,'create-button') and not(@disabled)]");

        hooks.getWait().until(ExpectedConditions.elementToBeClickable(enabledButton));

        // Optional: wait a bit if API/Angular is slow
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int attempts = 0;
        while (attempts < 3) {
            try {
                waitForLoaderToDisappear();

                WebElement btn = hooks.getWait()
                        .until(ExpectedConditions.elementToBeClickable(createBtnBy));

                // Scroll into view
                ((JavascriptExecutor) hooks.getDriver())
                        .executeScript("arguments[0].scrollIntoView(true);", btn);

                // Extra safety: JS click (sometimes Angular overlays block normal click)
                ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", btn);

                // Wait until loader disappears after click
                waitForLoaderToDisappear();

                return; // Success
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
                if (attempts == 3) throw e;
            }
        }
    }


    public boolean isResourceCreatedSuccessfully(String title) {
        try {
            // Wait for table header to appear (table is loaded)
            hooks.getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//th[normalize-space()='Resource ID']")
                    )
            );

            By titleCell = By.xpath("//tbody//td//div[normalize-space()='" + title + "']");

            // Give some buffer time for API + table reload
            Thread.sleep(2000); // small wait, UI needs time to refresh after creation

            // Try to find the resource in a retry loop
            int retries = 3;
            for (int i = 0; i < retries; i++) {

                try {
                    hooks.getWait().until(
                            ExpectedConditions.visibilityOfElementLocated(titleCell)
                    );
                    return true; // found!
                } catch (TimeoutException e) {
                    // Wait and retry
                    Thread.sleep(1000);
                }
            }

            return false; // not found after retries

        } catch (Exception e) {
            return false;
        }
    }




}
