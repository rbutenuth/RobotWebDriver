package de.codecentric.jrobot.webdriver;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.python.util.jython;

/**
 *
 */
public class GenerateKeywordDocumentationTest {

    @Test
    public void generateDocumentation() throws IOException {
        SeleniumWebDriverLibrary lib = new SeleniumWebDriverLibrary();
        String name = lib.getClass().getName();
        File dir = new File("doc");
        File xml = new File(dir, name + ".xml");
        File html = new File(dir, name + ".html");

        if (xml.isFile()) {
           assertTrue("can't delete file " + xml.getAbsolutePath(), xml.delete());
        }
        if (html.isFile()) {
            assertTrue("can't delete file " + html.getAbsolutePath(), html.delete());
         }
        // Step 1: Generate the XML-File with own generator
        lib.generateXmlDocumentation(dir);
        assertTrue(xml.isFile());

        // Step 2: Convert XML to Html
        jython.run(new String[] { "-m", "robot.libdoc", xml.getAbsolutePath() , html.getAbsolutePath() });
        assertTrue(html.isFile());
    }

}
