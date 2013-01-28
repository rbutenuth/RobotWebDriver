package de.codecentric.jrobot.webdriver;

import de.codecentric.jrobot.AbstractKeyword;

/**
 * Keywords for navigation from page to page and within the page (frames etc.).
 */
public class NavigationKeywords {

    /**
     * Add navigation keywords.
     * @param lib Needed for callbacks.
     */
    public static void addKeywords(final SeleniumWebDriverLibrary lib) {

        lib.add(new AbstractKeyword("navigateTo", new String[] {"url"},
            "Load a new web page in the current browser window. This is done using an HTTP GET operation, and the"
                    + " method will block until the load is complete. This will follow redirects issued either"
                    + " by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect"
                    + " \"rest\" for any duration of time, it is best to wait until this timeout is over,"
                    + "since should the underlying page change whilst your test is executing the results of "
                    + "future calls against this interface will be against the freshly loaded page.") {
            @Override
            public Object run(Object url) {
                lib.driver().navigate().to((String) url);
                lib.setCurrentElement(null);
                return null;
            }
        });

        lib.add(new AbstractKeyword("navigateBack", new String[0], "Move back a single \"item\" in the browser's history.") {
            @Override
            public Object run() {
                lib.driver().navigate().back();
                lib.setCurrentElement(null);
                return null;
            }

        });

        lib.add(new AbstractKeyword("navigateForward", new String[0],
                "Move a single \"item\" forward in the browser's history. Does nothing if we are on the latest page viewed.") {
            @Override
            public Object run() {
                lib.driver().navigate().forward();
                lib.setCurrentElement(null);
                return null;
            }

        });

        lib.add(new AbstractKeyword("selectCurrentElement", new String[] {"strategy", "key"},
                "Select a different element of the current page as toplevel element for further actions.") {
            @Override
            public Object run(Object strategy, Object key) {
                lib.setCurrentElement(lib.findElement((String) strategy, (String) key));
                return null;
            }
        });

        lib.add(new AbstractKeyword("switchToFrame", new String[] {"strategy", "key"}, "Select a frame.") {
            @Override
            public Object run(Object strategy, Object key) {
                lib.driver().switchTo().frame(lib.findElement((String)strategy, (String)key));
                // The driver is in the new frame, so currentElement can be null.
                lib.setCurrentElement(null);
                return null;
            }
        });

        lib.add(new AbstractKeyword("switchToDefaultContent", new String[0],
                "Selects either the first frame on the page, or the main document when a page contains iframes.") {
            @Override
            public Object run() {
                lib.driver().switchTo().defaultContent();
                lib.setCurrentElement(null);
                return null;
            }

        });

        lib.add(new AbstractKeyword("switchToAlert", new String[0],
                "Switches to the currently active modal dialog for this particular driver instance.") {
            @Override
            public Object run() {
                lib.driver().switchTo().alert();
                lib.setCurrentElement(null);
                return null;
            }

        });

        lib.add(new AbstractKeyword("switchToWindow", new String[] {"nameOrHandle"},
                "Switch the focus of future commands for this driver to the window with the given name/handle.") {
            @Override
            public Object run(Object nameOrHandle) {
                lib.driver().switchTo().window((String) nameOrHandle);
                lib.setCurrentElement(null);
                return null;
            }
        });

        lib.add(new AbstractKeyword("getWindowHandle", new String[0],
            "Return an opaque handle to this window that uniquely identifies it within this driver instance. "
                    + "This can be used to switch to this window at a later date.") {
            @Override
            public Object run() {
                return lib.driver().getWindowHandle();
            }
        });
    }
}
