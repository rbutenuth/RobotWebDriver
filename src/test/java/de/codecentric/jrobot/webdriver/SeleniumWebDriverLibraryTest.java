package de.codecentric.jrobot.webdriver;

import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test basic things like open/close browser with different browsers.
 * Needs the driver executables for Internet Explorer and Chrome in the PATH.
 */
public class SeleniumWebDriverLibraryTest {
    private static SeleniumWebDriverLibrary driver;
    private static File testPage;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new SeleniumWebDriverLibrary();
        testPage = new File("src/test/resources/Testpage.html");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.closeAllBrowsers();
        driver = null;
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetLibraryDocumentation() {
        assertTrue("doc there", driver.getLibraryDocumentation().length() > 0);
    }

    @Test
    public void testOpenCloseChrome() throws Exception {
        String url = testPage.toURI().toURL().toString();
        String key = (String) driver.runKeyword("openBrowser", url, "chrome");
        driver.runKeyword("closeBrowser", key);
    }

    @Test
    public void testOpenCloseFirefox() throws Exception {
        String url = testPage.toURI().toURL().toString();
        String key = (String) driver.runKeyword("openBrowser", url, "firefox");
        driver.runKeyword("closeBrowser", key);
    }

    @Test
    public void testOpenCloseInternetExplorer() throws Exception {
        String url = testPage.toURI().toURL().toString();
        String key = (String) driver.runKeyword("openBrowser", url, "internetExplorer");
        driver.runKeyword("closeBrowser", key);
    }

    // TODO: switchToBrowser
}