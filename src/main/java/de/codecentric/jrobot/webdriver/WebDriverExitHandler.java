package de.codecentric.jrobot.webdriver;

/**
 * Exit handler to close all browsers.
 */
public class WebDriverExitHandler extends Thread {
    private SeleniumWebDriverLibrary seleniumWebDriverLibrary;

    /**
     *
     * @param seleniumWebDriverLibrary
     */
    public WebDriverExitHandler(SeleniumWebDriverLibrary seleniumWebDriverLibrary) {
        this.seleniumWebDriverLibrary = seleniumWebDriverLibrary;
    }

    /**
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        seleniumWebDriverLibrary.closeAllBrowsers();
    }
}
