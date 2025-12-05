package com.legify.selenium.steps;

import static org.openqa.selenium.support.PageFactory.initElements;

import com.legify.selenium.pages.BasePage;
import com.legify.selenium.runners.Hook;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;


import java.util.List;

@Slf4j
public class BaseSteps {

    /**
     * Logger
     */

    private final Hook hooks;
    private final List<BasePage> pages;

    public BaseSteps(Hook hooks, List<BasePage> pages) {
        this.hooks = hooks;
        this.pages = pages;
    }

    @Before(order = 1)
    public void logBeforeScenario(final Scenario scenario) {
        log.info("{}[{}] - [{}]", StringUtils.rightPad("Starting scenario:", 20), getFeatureName(scenario), scenario.getName());
    }

    @Before(order = 2)
    public void initializeDriver() {
        final WebDriver driver = hooks.getDriver();

        // Initialize all page elements
        pages.forEach(p -> initElements(driver, p));
        // --
    }

    @After(order = 1)
    public void closeDriver(final Scenario scenario) {
        hooks.tearDown(scenario);
        hooks.closeDriver();
    }

    @After(order = Integer.MAX_VALUE)
    public void logAfterScenario(final Scenario scenario) {
        log.info("{}[{}] - [{}] [{}]", StringUtils.rightPad("Finished scenario:", 20),
                getFeatureName(scenario), scenario.getName(), scenario.getStatus());
    }

    private String getFeatureName(Scenario scenario) {
        String featureName = scenario.getId();

        featureName = StringUtils.substringBeforeLast(featureName, ".feature");
        featureName = StringUtils.substringAfterLast(featureName, "/");

        return featureName;
    }
}
