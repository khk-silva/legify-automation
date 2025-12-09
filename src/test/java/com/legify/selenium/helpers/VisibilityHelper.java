package com.legify.selenium.helpers;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import com.legify.selenium.runners.Hook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisibilityHelper {

    @Autowired
    private Hook hooks;

    private final By loaderBy = By.cssSelector(".loading-overlay");


    public void waitForVisibilityOf(WebElement element) {
        hooks.getWait().until(visibilityOf(element));
    }


    public void waitForPresenceOf(By by) {
        hooks.getWait().until(visibilityOfElementLocated(by));
    }

    // ---------------- Utility methods ----------------
    public void safeSleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    public void jsScrollToCenter(WebElement element) {
        ((JavascriptExecutor) hooks.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) hooks.getDriver()).executeScript("arguments[0].click();", element);
    }

    public void retryElementAction(Runnable action) {
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
}
