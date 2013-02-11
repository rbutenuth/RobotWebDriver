package de.codecentric.jrobot.webdriver;

import de.codecentric.jrobot.AbstractKeyword;

/**
 * Keywords for reading things from a page.
 */
public class VerifyKeywords {
    /**
     * Add get keywords.
     * @param lib Needed for callbacks.
     */
    public static void addKeywords(final SeleniumWebDriverLibrary lib) {

        lib.add(new AbstractKeyword("textfield value should be", new String[] {"strategy", "key", "expected"},
                "Verifies the value in text field identified by strategy / key is exactly expected.") {
            @Override
            public Object run(Object strategy, Object key, Object expected) throws Exception {
                String value = lib.findElement((String) strategy, (String) key).getAttribute("value");
                if (!expected.equals(value)) {
                    throw new Exception("Text field \"" + strategy + "\", \"" + key + "\" should be \"" + expected
                        + "\" but is \"" + value + "\"");
                }
                return null;
            }
        });
    }
}
