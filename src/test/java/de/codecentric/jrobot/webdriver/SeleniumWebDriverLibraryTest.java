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
        driver.runKeyword("open browser", url, "chrome");
        driver.runKeyword("close browser", (Object[])null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSwitchToNonExistingBrowser() throws Exception {
        driver.runKeyword("switch to browser", "not known");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testCloseNonExistingBrowser() throws Exception {
        driver.runKeyword("close browser", "not known");
    }

    @Test(expected=IllegalStateException.class)
    public void testNoOpenBrowser() throws Exception {
        driver.runKeyword("get title", (Object[])null);
    }

    @Test
    public void testOpenCloseFirefox() throws Exception {
        String url = testPage.toURI().toURL().toString();
        String key = (String) driver.runKeyword("open browser", url, "firefox");
        driver.runKeyword("close browser", key);
    }

    @Test
    public void testOpenCloseInternetExplorer() throws Exception {
        String url = testPage.toURI().toURL().toString();
        driver.runKeyword("open browser", url, "internetExplorer");
        driver.runKeyword("close all browsers");
    }

    @Test
    public void testSwitchBrowser() throws Exception {
        String url = testPage.toURI().toURL().toString();
        String ff = (String) driver.runKeyword("open browser", url, "firefox");
        String chrome = (String) driver.runKeyword("open browser", url, "chrome");
        driver.runKeyword("switch to browser", ff);
        driver.runKeyword("close browser", ""); // should close ff
        driver.runKeyword("close browser", chrome);
    }
}
