# Legify.AI - Automation Testing Cucumber

This repository contains the base setup of functional regression testin legify.ai system
# Requirements

* JDK 17
* Maven 3.9.1

# Test Execution

1. Download or clone the repository
2. Open a terminal
3. From the project root directory run:  `mvn clean test`

# Configuration

By default, tests will be executed in Chrome (headless mode). 

Preferences can be changed in "application.properties" file

This values can also be defined from the command line when launching the tests without the need of
modify the properties file.

`mvn clean test -Dselenium.browser.headless=false`

# Results

To check the report, open the '/results/cucumber-reports.html' file once the execution has finished.
