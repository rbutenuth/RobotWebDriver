package de.codecentric.jrobot.webdriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.codecentric.jrobot.AbstractDynamicLibrary;
import de.codecentric.jrobot.AbstractKeyword;

/**
 * Experimental Robot library to access the Selenium {@link WebDriver}.
 */
public class SeleniumWebDriverLibrary extends AbstractDynamicLibrary {
    /** GLOBAL */
    public static final String ROBOT_LIBRARY_SCOPE = Scope.GLOBAL.name();

    private Map<String, WebDriver> driverMap;

    private int keySequence;

    private WebDriver currentDriver;

    private String currentDriverKey;

    private WebElement currentElement;

    /**
     * Create library, no browser is opened so far.
     */
    public SeleniumWebDriverLibrary() {
        driverMap = new HashMap<String, WebDriver>();
        keySequence = 1;
        Runtime.getRuntime().addShutdownHook(new WebDriverExitHandler(this));

        add(new ScriptingKeywords(this));
        add(new OpenBrowser());
        add(new SwitchToBrowser());
        add(new CloseBrowser());
        add(new CloseAllBrowsers());
        NavigationKeywords.addKeywords(this);
        new ScreenshotKeywords(this);
        GetInfoKeywords.addKeywords(this);
        InputKeywords.addKeywords(this);
        VerifyKeywords.addKeywords(this);
    }

    /**
     * @see de.codecentric.jrobot.AbstractDynamicLibrary#getLibraryDocumentation()
     */
    @Override
    public String getLibraryDocumentation() {
        return "Experimental Robot library to access the Selenium via WebDriver.";
    }

    private class OpenBrowser extends AbstractKeyword {
        OpenBrowser() {
            super("openBrowser", new String[] {"url", "browser"},
                "Open a new browser, make it the current one, navigate to <code>url</code>." + "Parameters:\n"
                        + "- _url_: Where to start.\n"
                        + "- _browser_: Name of the browser, see {@link WebDriverFactory} for supported browsers.\n" + "Return:\n"
                        + "  Key of the browser.");
        }

        @Override
        public String run(Object url, Object browser) {
            WebDriver d = new WebDriverFactory().createDriver((String) browser);
            currentDriver = d;
            d.get((String) url);
            currentDriverKey = d.getClass().getSimpleName() + "-" + keySequence++;
            driverMap.put(currentDriverKey, d);
            currentElement = null;
            return currentDriverKey;
        }
    }

    private class SwitchToBrowser extends AbstractKeyword {
        SwitchToBrowser() {
            super("switchToBrowser", new String[] {"browser"}, "Parameters: - _browser_: Make this the current browser.");
        }

        @Override
        public Object run(Object browser) {
            WebDriver d = driverMap.get(browser);
            if (d == null) {
                throw new IllegalArgumentException("Unknown browser: \"" + browser + "\"");
            }
            currentDriver = d;
            currentDriverKey = (String) browser;
            currentElement = null;
            return browser;
        }
    }

    private class CloseBrowser extends AbstractKeyword {
        CloseBrowser() {
            super("closeBrowser", new String[] { "browser=\"\"" },
                "Close the given browser. Parameters: - _browser_: Key of the browser. "
                        + " If no browser is given, close the current one.");
        }

        @Override
        public Object run(Object arg) {
            String browser = (String)arg;
            if (browser == null || browser.length() == 0) {
                browser = currentDriverKey;
            }
            closeBrowser(browser);
            return null;
        }
    }

    private class CloseAllBrowsers extends AbstractKeyword {
        CloseAllBrowsers() {
            super("closeAllBrowsers", new String[0], "Close all browsers");
        }
        @Override
        public Object run() {
            closeAllBrowsers();
            return null;
        }
    }

    /**
     * Close all browsers.
     */
    public void closeAllBrowsers() {
        // A copy is necessary, otherwise we get ConcurrentModificationException
        for (String k : new ArrayList<String>(driverMap.keySet())) {
            closeBrowser(k);
        }
    }

    private void closeBrowser(String key) {
        WebDriver d = driverMap.get(key);
        if (d == null) {
            throw new IllegalArgumentException("Unknown browser: \"" + key + "\"");
        }
        d.quit();
        driverMap.remove(key);
        if (d == currentDriver) {
            currentDriver = null;
            currentDriverKey = null;
            currentElement = null;
        }
        currentElement = null;
    }

    /**
     * @return The current driver.
     * @throws IllegalStateException When there is no current driver.
     */
    public WebDriver driver() {
        if (currentDriver == null) {
            throw new IllegalStateException("No open browser");
        }
        return currentDriver;
    }

    /**
     * @return The current element, defaults to the current driver.
     * @throws IllegalStateException When there is no current driver and no current element.
     */
    public SearchContext element() {
        if (currentElement != null) {
            return currentElement;
        }
        if (currentDriver != null) {
            return currentDriver;
        }
        throw new IllegalStateException("No current element/driver");
    }

    /**
     * Find element, starting at driver or current element. May wait, finds the element or throws exception.
     * @param strategy Name of a strategy, see {@link By}.
     * @param key The key to use with the strategy.
     * @return Found element, never <code>null</code>
     */
    public WebElement findElement(String strategy, String key) {
        return element().findElement(by(strategy, key));
    }

    /**
     * Find elements, starting at driver or current element.
     * @param strategy Name of a strategy, see {@link By}.
     * @param key The key to use with the strategy.
     * @return Found elements.
     */
    public List<WebElement> findElements(String strategy, String key) {
        return element().findElements(by(strategy, key));
    }

    /**
     * @param strategy Name of a strategy, see {@link By}.
     * @param key The key to use with the strategy.
     * @return Strategy.
     */
    public By by(String strategy, String key) {
        By by;
        if ("class".equals(strategy)) {
            by = By.className(key);
        } else if ("css".equals(strategy)) {
            by = By.cssSelector(key);
        } else if ("id".equals(strategy)) {
            by = By.id(key);
        } else if ("name".equals(strategy)) {
            by = By.name(key);
        } else if ("linkText".equals(strategy)) {
            by = By.linkText(key);
        } else if ("partialLinkText".equals(strategy)) {
            by = By.partialLinkText(key);
        } else if ("tag".equals(strategy)) {
            by = By.tagName(key);
        } else if ("xpath".equals(strategy)) {
            by = By.xpath(key);
        } else {
            throw new IllegalArgumentException("Unknown locator strategy: \"" + strategy + "\"");
        }
        return by;
    }

    /**
     * @return Current driver, may be null.
     */
    public WebDriver getCurrentDriver() {
        return currentDriver;
    }

    /**
     * @param currentDriver Current driver, may be null.
     */
    public void setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
    }

    /**
     * @return String key for current driver, may be null.
     */
    public String getCurrentDriverKey() {
        return currentDriverKey;
    }

    /**
     * @param currentDriverKey String key for current driver, may be null.
     */
    public void setCurrentDriverKey(String currentDriverKey) {
        this.currentDriverKey = currentDriverKey;
    }

    /**
     * @return The current active element, may be null.
     */
    public WebElement getCurrentElement() {
        return currentElement;
    }

    /**
     * @param currentElement The current active element, may be null.
     */
    public void setCurrentElement(WebElement currentElement) {
        this.currentElement = currentElement;
    }
}
