package de.codecentric.jrobot.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import de.codecentric.jrobot.AbstractKeyword;

/**
 * Execute JavsScript in Browser
 */
public class ScriptingKeywords extends AbstractKeyword {
    private SeleniumWebDriverLibrary lib;

    /**
     * @param lib Library, for callbacks.
     */
    public ScriptingKeywords(SeleniumWebDriverLibrary lib) {
        super("executeJavascript", new String[] {"*script"},
            "Executes JavaScript in the context of the currently selected frame or window. The script fragment provided will"
                    + "be executed as the body of an anonymous function."
                    + "Within the script, use document to refer to the current document. Note that local variables will not be"
                    + "available once the script has finished executing, though global variables will persist.\n" + "Return:\n"
                    + "What you give to \"return\" in the script.");
        this.lib = lib;
    }

    /**
     * @see de.codecentric.jrobot.AbstractKeyword#run(java.lang.Object[])
     */
    @Override
    public Object run(Object[] arguments) {
        StringBuilder sb = new StringBuilder();
        for (Object part : arguments) {
            sb.append(part);
        }
        Object result = ((JavascriptExecutor) lib.element()).executeScript(sb.toString());
        return result == null ? "" : result.toString();
    }
}
