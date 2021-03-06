package de.codecentric.jrobot.webdriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class InputKeywordsTest {
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
    public void testInputAppend() throws Exception {
        driver.runKeyword("input text", "id", "TextfieldId", "43");
        String text = (String)driver.runKeyword("get value", "id", "TextfieldId");
        assertEquals("4243", text);
    }

    @Test
    public void testInputReplace() throws Exception {
        driver.runKeyword("replace text", "id", "TextfieldId", "43");
        String text = (String)driver.runKeyword("get value", "id", "TextfieldId");
        assertEquals("43", text);
    }

    @Test
    public void testInputReplaceSubmit() throws Exception {
        driver.runKeyword("replace text", "id", "TextfieldId", "Hello!");
        driver.runKeyword("click", "id", "submit");
        String text = (String)driver.runKeyword("get value", "id", "hiddenTextFieldId");
        assertEquals("Hello!", text);
    }

    @Test
    public void testRadioButton() throws Exception {
        assertEquals("true", driver.runKeyword("get attribute", "id", "wdr1", "checked"));
        assertNull(driver.runKeyword("get attribute", "id", "wdr2", "checked"));
        assertNull(driver.runKeyword("get attribute", "id", "wdr3", "checked"));
        driver.runKeyword("click", "id", "wdr2");
        assertNull(driver.runKeyword("get attribute", "id", "wdr1", "checked"));
        assertEquals("true", driver.runKeyword("get attribute", "id", "wdr2", "checked"));
        assertNull(driver.runKeyword("get attribute", "id", "wdr3", "checked"));
        driver.runKeyword("click", "id", "submit");
        String text = (String)driver.runKeyword("get value", "id", "checkedStationId");
        assertEquals("WDR 2", text);
    }
}
