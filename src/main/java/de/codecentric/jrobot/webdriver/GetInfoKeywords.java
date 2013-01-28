package de.codecentric.jrobot.webdriver;

import de.codecentric.jrobot.AbstractKeyword;

/**
 * Keywords for reading things from a page.
 */
public class GetInfoKeywords {
    /**
     * Add get keywords.
     * @param lib Needed for callbacks.
     */
    public static void addKeywords(final SeleniumWebDriverLibrary lib) {

        lib.add(new AbstractKeyword("getTitle", new String[0],
            "Return: The title of the current page, with leading and trailing whitespace stripped, "
                    + "or null if one is not already set") {
            @Override
            public Object run() {
                return lib.driver().getTitle();
            }
        });

        lib.add(new AbstractKeyword(
            "getPageSource",
            new String[0],
            "Get the source of the last loaded page. If the page has been modified after loading "
                    + "(for example, by Javascript) there is no guarantee that the returned text is that "
                    + "of the modified page. Please consult the documentation of the particular driver "
                    + "being used to determine whether the returned text reflects the current state of the "
                    + "page or the text last sent by the web server. The page source returned is a "
                    + "representation of the underlying DOM: do not expect it to be formatted or escaped "
                    + "in the same way as the response sent from the web server. Think of it as an "
                    + "artist's impression.") {
            @Override
            public Object run() {
                return lib.driver().getPageSource();
            }
        });

        lib.add(new AbstractKeyword("getText", new String[] { "strategy", "key"},
                "Returns the text of element identified by locator.") {
            @Override
            public Object run(Object strategy, Object key) {
                return lib.findElement((String)strategy, (String)key).getText();
            }
        });

        lib.add(new AbstractKeyword("getAttribute",  new String[] { "strategy", "key", "attribute"},
            "Get the value of a the given attribute of the element. Will return the current value, even if this has "
                    + "been modified after the page has been loaded. More exactly, this method will return the value "
                    + "of the given attribute, unless that attribute is not present, in which case the value of the "
                    + "property with the same name is returned. If neither value is set, null is returned. The "
                    + "\"style\" attribute is converted as best can be to a text representation with a trailing "
                    + "semi-colon. The following are deemed to be \"boolean\" attributes, and will return either "
                    + "\"true\" or null: async, autofocus, autoplay, checked, compact, complete, controls, declare,"
                    + " defaultchecked, defaultselected, defer, disabled, draggable, ended, formnovalidate, hidden, "
                    + "indeterminate, iscontenteditable, ismap, itemscope, loop, multiple, muted, nohref, noresize, "
                    + "noshade, novalidate, nowrap, open, paused, pubdate, readonly, required, reversed, scoped, "
                    + "seamless, seeking, selected, spellcheck, truespeed, willvalidate Finally, the following "
                    + "commonly mis-capitalized attribute/property names are evaluated as expected: - \"class\" - "
                    + "\"readonly\" Parameters: - _strategy_: Location strategy, e.g. \"id\" - _key_: Key part of "
                    + "location strategy - _attribute_: Name of the attribute Returns: The attribute's current "
                    + "value or null if the value is not set.") {
            @Override
            public Object run(Object strategy, Object key, Object attribute) {
                return lib.findElement((String)strategy, (String)key).getAttribute((String)attribute);
            }
        });
    }
}
