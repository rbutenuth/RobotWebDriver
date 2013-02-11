package de.codecentric.jrobot.webdriver;

import static org.junit.Assert.assertEquals;

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
public class ScriptingKeywordsTest {
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
        String url = testPage.toURI().toURL().toString();
        driver.runKeyword("open browser", url, "chrome");
    }

    @After
    public void tearDown() throws Exception {
        driver.runKeyword("close browser", (Object[])null);
    }

    @Test
    public void testExecuteJavaScriptInBrowser() throws Exception {
        String result = (String)driver.runKeyword("execute JavaScript in browser", "return 6 * 7");
        // In WebDriver / Chrome returns a Long, so there is no fractional part.
        assertEquals("42", result);
    }

    @Test
    public void testExecuteJavaScriptLocally() throws Exception {
        String result = (String)driver.runKeyword("execute JavaScript locally", "6 * 7");
        // Rhino in the JVM returns a Double, toString() add a ".0"...
        assertEquals("42.0", result);
    }

}
