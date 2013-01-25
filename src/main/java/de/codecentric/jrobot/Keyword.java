package de.codecentric.jrobot;

/**
 * See <a href="http://robotframework.googlecode.com/hg/doc/userguide/RobotFrameworkUserGuide.html?r=2.7.6#dynamic-library-api">
 * RobotFrameworkUserGuide.html</a> for more information.
 */
public interface Keyword {

    /**
     * @return Name of the Keyword.
     */
    public String getKeywordName();

    /**
     * Run this keyword.
     * @param args Arguments (must match {@link #getKeywordArguments()}).
     * @return Result of the keyword.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object runKeyword(Object[] args) throws Exception;

    /**
     * @return Keyword documentation in Robot's Wiki syntax.
     */
    public String getKeywordDocumentation();

    /**
     * @return Names of arguments, length of array determines number of arguments. A String of the form "name=value"
     * stands for an argument with a default value. A * as first character of the last argument means variable number
     * of arguments.
     */
    public String[] getKeywordArguments();
}
