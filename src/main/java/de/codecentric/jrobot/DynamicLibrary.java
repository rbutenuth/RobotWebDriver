package de.codecentric.jrobot;

import java.util.List;

/**
 * This interface should by provided by Robot itself, it defines the methods
 * which should be implemented by dynamic keyword libraries.
 */
public interface DynamicLibrary {
    /**
     * @return Immutable list of implmented keywords.
     */
    public List<String> getKeywordNames();

    /**
     * Execute a keyword.
     * @param name Name of the keyword, see {@link #getKeywordNames()}.
     * @param args Arguments, must match {@link #getKeywordArguments(String)}.
     * @return Result of the keyword.
     * @throws Exception For easy communication with Robot.
     */
    public Object runKeyword(String name, Object... args) throws Exception;

    /**
     * @param name Name of the keyword.
     * @return Result in Robot's Wiki syntax.
     */
    public String getKeywordDocumentation(String name);

    /**
     * @param name Name of the keyword.
     * @return Names of arguments, length of array determines number of arguments. A String of the form "name=value"
     * stands for an argument with a default value. A * as first character of the last argument means variable number
     * of arguments.
     */
    public String[] getKeywordArguments(String name);
}
