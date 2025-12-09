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
public class ResourcePage implements BasePage {

    @Autowired
    private Hook hooks;

    @Autowired
    private VisibilityHelper visibilityHelper;

    @Autowired
    public ResourcePage(Hook hooks) {
        this.hooks = hooks;
        PageFactory.initElements(hooks.getDriver(), this);
    }


    @FindBy(xpath = "//h4[normalize-space()='Build Your Own AI']")
    private WebElement resourceMenuText;

    @FindBy(xpath = "//h4[normalize-space()='Build Your Own AI']")
    private WebElement resourceMenuIcon;


    @FindBy(xpath = "//button[contains(.,'Create New Resource')]")
    private WebElement createNewResourceButton;

    private final By loaderBy = By.cssSelector(".loading-overlay");
    private final By titleInputBy = By.xpath("//input[@formcontrolname='title']");
    private final By typeDropdownBy = By.xpath("//mat-select[@formcontrolname='type']");
    private final By applicableDocInputBy = By.xpath("//el-legify-async-autocomplete//input[@matinput]");
    private final By jurisdictionDropdownBy = By.xpath("//mat-select[@formcontrolname='jurisdiction']");
    private final By tagsInputBy = By.xpath("//input[@formcontrolname='tags']");
    private final By fileUploadInputBy = By.id("uploadFile");
    private final By createBtnBy = By.xpath("//span[@class='button-with-spinner' and contains(.,'Create')]");

    // -----------------------
    // NAVIGATION
    // -----------------------
    public void navigateToResourceModule() {
        visibilityHelper.safeSleep(15000);
        visibilityHelper.retryElementAction(() -> {
            visibilityHelper.jsScrollToCenter(resourceMenuIcon);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(resourceMenuIcon));
            visibilityHelper.jsClick(resourceMenuIcon);
        });
    }

    public boolean showResourcePage() {
        visibilityHelper.retryElementAction(() -> {
            WebElement element = hooks.getWait().until(ExpectedConditions.visibilityOf(resourceMenuText));
            assertTrue(element.isDisplayed(), "Resource page is not visible");
        });
        return true;
    }

    // -----------------------
    // CREATE NEW RESOURCE
    // -----------------------
    public void clickCreateNewResourceButton() {
        visibilityHelper.safeSleep(15000); // preserve original behavior
        visibilityHelper.retryElementAction(() -> {
            visibilityHelper.jsScrollToCenter(createNewResourceButton);
            hooks.getWait().until(ExpectedConditions.elementToBeClickable(createNewResourceButton));
            visibilityHelper.jsClick(createNewResourceButton);
        });
    }

    public void isResourceCreationWindowDisplayed() {
        visibilityHelper.waitForVisibilityOf(createNewResourceButton);
    }


    // -----------------------
    // FORM INTERACTIONS
    // -----------------------
    public void enterTitle(String title) {
        visibilityHelper.waitForLoaderToDisappear();
        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(titleInputBy));
            visibilityHelper.jsScrollToCenter(input);
            input.clear();
            input.sendKeys(title);
        });
    }

    public void selectType(String type) {
        visibilityHelper.waitForLoaderToDisappear();
        visibilityHelper.retryElementAction(() -> {
            WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(typeDropdownBy));
            visibilityHelper.jsScrollToCenter(dropdown);
            dropdown.click();

            By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", type));
            WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
            visibilityHelper.jsClick(option);
        });
    }

    public void selectApplicableDocumentType(String docType) {
        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(applicableDocInputBy));
            visibilityHelper.jsScrollToCenter(input);
            input.sendKeys(docType);
            input.sendKeys(Keys.ENTER);
        });
    }

    public void selectJurisdiction(String jurisdiction) {
        visibilityHelper.retryElementAction(() -> {
            WebElement dropdown = hooks.getWait().until(ExpectedConditions.elementToBeClickable(jurisdictionDropdownBy));
            visibilityHelper.jsScrollToCenter(dropdown);
            dropdown.click();

            By optionBy = By.xpath(String.format("//mat-option//span[normalize-space()='%s']", jurisdiction));
            WebElement option = hooks.getWait().until(ExpectedConditions.elementToBeClickable(optionBy));
            visibilityHelper.jsClick(option);
        });
    }

    public void addTag(String tag) {
        visibilityHelper.retryElementAction(() -> {
            WebElement input = hooks.getWait().until(ExpectedConditions.elementToBeClickable(tagsInputBy));
            visibilityHelper.jsScrollToCenter(input);
            input.sendKeys(tag);
            input.sendKeys(Keys.ENTER);
        });
    }

    public void uploadDocument(String fileName) {
        visibilityHelper.waitForLoaderToDisappear();
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testFiles/" + fileName;
        visibilityHelper.retryElementAction(() -> {
            WebElement uploadInput = hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(fileUploadInputBy));
            uploadInput.sendKeys(filePath);
        });
        visibilityHelper.safeSleep(2000);
    }

    public void clickCreateButton() {
        visibilityHelper.waitForLoaderToDisappear();
        visibilityHelper.safeSleep(15000);

        visibilityHelper.retryElementAction(() -> {
            WebElement btn = hooks.getWait().until(ExpectedConditions.elementToBeClickable(createBtnBy));
            visibilityHelper.jsScrollToCenter(btn);
            visibilityHelper.jsClick(btn);
            visibilityHelper.waitForLoaderToDisappear();
        });
    }

    public boolean isResourceCreatedSuccessfully(String title) {
        try {
            hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[normalize-space()='Resource ID']")));
            By titleCell = By.xpath("//tbody//td//div[normalize-space()='" + title + "']");
            visibilityHelper.safeSleep(2000);

            int retries = 3;
            for (int i = 0; i < retries; i++) {
                try {
                    hooks.getWait().until(ExpectedConditions.visibilityOfElementLocated(titleCell));
                    return true;
                } catch (TimeoutException e) {
                    visibilityHelper.safeSleep(1000);
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
