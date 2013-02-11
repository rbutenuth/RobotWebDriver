package de.codecentric.jrobot.webdriver;

import org.openqa.selenium.support.ui.Select;
import de.codecentric.jrobot.AbstractKeyword;

/**
 * Keywords for clicking and typing.
 */
public class InputKeywords {
    /**
     * Add input keywords.
     * @param lib Needed for callbacks.
     */
    public static void addKeywords(final SeleniumWebDriverLibrary lib) {

        lib.add(new AbstractKeyword("input text", new String[] {"strategy", "key", "text"},
                "Types the given text into text field identified by locator.") {
            @Override
            public Object run(Object strategy, Object key, Object text) {
                lib.findElement((String) strategy, (String) key).sendKeys((String) text);
                return null;
            }
        });

        lib.add(new AbstractKeyword("select from list", new String[] {"strategy", "key", "text"},
                "Select an item in a drop down list identified by locator.\nThe item is identified by the visible text.") {
            @Override
            public Object run(Object strategy, Object key, Object text) {
                Select droplist = new Select(lib.findElement((String) strategy, (String) key));
                droplist.selectByVisibleText((String) text);
                return null;
            }
        });

        lib.add(new AbstractKeyword("click", new String[] {"strategy", "key"},
            "Click this element. If this causes a new page to load, this method will attempt to block until the page "
                    + "has loaded. At this point, you should discard all references to this element and any further "
                    + "operations performed on this element will throw a StaleElementReferenceException unless you know "
                    + "the element and the page will still be present. If click() causes a new page to be loaded via an "
                    + "event or is done by sending a native event then the method will *not* wait for it to be loaded and "
                    + "the caller should verify that a new page has been loaded. There are some preconditions for an element "
                    + "to be clicked. The element must be visible and it must have a height and width greater then 0.") {
            @Override
            public Object run(Object strategy, Object key) {
                lib.findElement((String) strategy, (String) key).click();
                return null;
            }
        });

        lib.add(new AbstractKeyword("submit", new String[] {"strategy", "key"},
            "If the element is a form, or an element within a form, then this will be submitted to the remote server. "
                    + "If this causes the current page to change, "
                    + "then this method will block until the new page is loaded.") {
            @Override
            public Object run(Object strategy, Object key) {
                lib.findElement((String) strategy, (String) key).submit();
                return null;
            }
        });
    }
}
