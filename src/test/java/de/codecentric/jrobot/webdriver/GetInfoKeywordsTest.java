package de.codecentric.jrobot.webdriver;

import static org.junit.Assert.assertEquals;
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
public class GetInfoKeywordsTest {
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
        driver.runKeyword("openBrowser", url, "chrome");
    }

    @After
    public void tearDown() throws Exception {
        driver.runKeyword("closeBrowser", (Object[])null);
    }

    @Test
    public void testGetTitle() throws Exception {
        String expected = "My Small Testpage";
        String actual = (String) driver.runKeyword("getTitle", (Object[])null);
        assertEquals("Title", expected, actual);
    }

    @Test
    public void testGetPageSource() throws Exception {
        String source = (String)driver.runKeyword("getPageSource", (Object[])null);
        assertTrue(source.startsWith("<!DOCTYPE html PUBLIC"));
        assertTrue(source.trim().endsWith("</html>"));
    }

    @Test
    public void testGetText() throws Exception {
        String expected = "My Small Testpage";
        String actual = (String) driver.runKeyword("getText", "xpath", "//html/head/title");
        assertEquals("Title", expected, actual);
    }

    @Test
    public void testGetAttribute() throws Exception {
        String attr = (String)driver.runKeyword("getAttribute", "id", "TextfieldId", "value");
        assertEquals("Value of input", "42", attr);
    }

    @Test
    public void testGetValue() throws Exception {
        String attr = (String)driver.runKeyword("getValue", "id", "TextfieldId");
        assertEquals("Value of input", "42", attr);
    }
}
