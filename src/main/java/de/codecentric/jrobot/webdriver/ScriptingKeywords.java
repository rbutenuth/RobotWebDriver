package de.codecentric.jrobot.webdriver;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.openqa.selenium.JavascriptExecutor;
import de.codecentric.jrobot.AbstractKeyword;

/**
 * Execute JavsScript in Browser
 */
public class ScriptingKeywords {
    private static ScriptEngineManager sem = new ScriptEngineManager();
    private static ScriptEngine engine = sem.getEngineByExtension("js");

    public static void addKeywords(final SeleniumWebDriverLibrary lib) {
        lib.add(new AbstractKeyword("execute JavaScript in browser", new String[] { "*script" },
                "Executes JavaScript in the context of the currently selected frame or window. "
                        + "The script fragment provided will be executed as the body of an anonymous function."
                        + "Within the script, use document to refer to the current document. "
                        + "Note that local variables will not be available once the script has finished executing, "
                        + "though global variables will persist.\n\nArguments:\n"
                        + " - script: One or several Strings, they will be concatenated before execution.\n\n"
                        + "Returns: What you give to \"return\" in the script.") {
            @Override
            public Object run(Object[] arguments) {
                StringBuilder sb = new StringBuilder();
                for (Object part : arguments) {
                    sb.append(part);
                }
                Object result = ((JavascriptExecutor) lib.element()).executeScript(sb.toString());
                return result == null ? "" : result.toString();
            }
        });
        lib.add(new AbstractKeyword("execute JavaScript locally", new String[] { "*script" },
                "Executes JavaScript in the test driver (with the builtin script engine of the JVM). "
                        + "The script will be evaluated as an expression, the result of the expression is returned. "
                        + "All scripts will be evaluated in the same ScriptEngine, global variables will persist. "
                        + "Don't use a return statement at the end of the script." + "\n\nArguments:\n"
                        + " - script: One or several Strings, they will be concatenated before execution.\n\n"
                        + "Returns: What you give to \"return\" in the script.") {
            @Override
            public Object run(Object[] arguments) throws ScriptException {
                StringBuilder sb = new StringBuilder();
                for (Object part : arguments) {
                    sb.append(part);
                }
                Object result = engine.eval(sb.toString());
                return result == null ? "" : result.toString();
            }
        });
    }
}
