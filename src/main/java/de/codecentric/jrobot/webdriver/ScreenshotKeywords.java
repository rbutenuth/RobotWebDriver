package de.codecentric.jrobot.webdriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import de.codecentric.jrobot.AbstractKeyword;

/**
 * Keywords for screenshots.
 */
public class ScreenshotKeywords {
    private File screenshotDirectory;

    private int screenshotSequence;

    /**
     * Add screenshot keywords.
     *
     * @param lib Needed for callbacks.
     */
    public ScreenshotKeywords(final SeleniumWebDriverLibrary lib) {
        screenshotDirectory = new File("").getAbsoluteFile();
        screenshotSequence = 1;

        lib.add(new AbstractKeyword("setScreenshotDirectory", new String[] { "path" },
                "Set a new directory for screenshots. \n\nReturn: The absolute path of the new directory for screenshots.") {
            @Override
            public Object run(Object path) {
                screenshotDirectory = new File(screenshotDirectory, (String) path);
                screenshotDirectory.mkdirs();
                return screenshotDirectory.getAbsolutePath();
            }
        });

        lib.add(new AbstractKeyword("takeScreenshot", new String[0], "Return: The absolute filename of the screenshot.") {
            @Override
            public Object run() throws IOException {
                File f = new File(screenshotDirectory, "selenium-screenshot-" + screenshotSequence++ + ".png");
                byte[] image = ((TakesScreenshot) lib.element()).getScreenshotAs(OutputType.BYTES);
                OutputStream os = new FileOutputStream(f);
                try {
                    os.write(image);
                } finally {
                    os.close();
                }
                // *HTML* at the beginning of the log message disables HTML quoting of Robot, this way
                // we can inject HTML into the log file.
                System.out.println("*HTML* <a href=\"" + f.getName() + "\">" //
                        + "<img src=\"" + f.getName() + "\" width=\"600px\"></a>");
                return f.getAbsolutePath();
            }
        });
    }
}
