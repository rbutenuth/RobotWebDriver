package de.codecentric.jrobot.webdriver;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Factory class for Selenium2 {@link WebDriver}s.
 */
public class WebDriverFactory {
    private final static String SUFFIXES[] = new String[] { "", ".exe" };

    /**
     * @param browserName Name of the browser to start, "chrome", "internetExplorer", or "firefix".
     * @return New instance
     * @throws IllegalArgumentException If browser is not known.
     */
    public WebDriver createDriver(String browserName) {
        WebDriver driver;
        if ("chrome".equals(browserName)) {
            // Only the Chromedriver allows to point to the executable by a property.
            setPropertyToExecutable("webdriver.chrome.driver", "chromedriver");
            driver = new ChromeDriver();
        } else if ("firefox".equals(browserName)) {
            driver = new FirefoxDriver();
        } else if ("internetExplorer".equals(browserName)) {
            driver = new InternetExplorerDriver();
        } else if ("phantomjs".equals(browserName)) {
            driver = new PhantomJSDriver(DesiredCapabilities.phantomjs());
        } else {
            throw new IllegalArgumentException("Unknown browser: " + browserName);
        }

        return driver;
    }

    /**
     * Search an executable in the PATH and in the tree starting in the current directory,
     * store the found absolute path in the given property.
     * Skip this if the property is already set.
     * @param propertyName Name of the property which points to the executable.
     * @param executableName Name of the executable.
     */
    private void setPropertyToExecutable(String propertyName, String executableName) {
        String value = System.getProperty(propertyName);
        if (value == null) {
            File executable = searchBinary(executableName);
            if (executable != null) {
                System.setProperty(propertyName, executable.getAbsolutePath());
            }
        }
    }

    private File searchBinary(String name) {
        // Search in PATH
        String path = System.getenv("PATH");
        String sep = System.getProperty("path.separator");
        int start = 0;
        int end = path.indexOf(sep);
        while (start < path.length()) {
            File dir = new File(path.substring(start, end));
            for (int i = 0; i < SUFFIXES.length; i++) {
                File executable = new File(dir, name + SUFFIXES[i]);
                if (executable.canExecute()) {
                    return executable;
                }
            }
            start = end + sep.length();
            end = path.indexOf(sep, start);
            if (end == -1) {
                end = path.length();
            }
        }
        // Search starting from local directory
        return searchBinary(new File(System.getProperty("user.dir")), name);
    }

    private File searchBinary(File dir, String name) {
        // Check if we have it in dir
        for (int i = 0; i < SUFFIXES.length; i++) {
            File executable = new File(dir, name + SUFFIXES[i]);
            if (executable.canExecute()) {
                return executable;
            }
        }
        // Recursion into subdirectories
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                File executable = searchBinary(file, name);
                if (executable != null) {
                    return executable;
                }
            }
        }
        return null;
    }
}
