package de.codecentric.jrobot;

/**
 * Helper class to make implementation of keywords easier.
 */
public abstract class AbstractKeyword implements Keyword {
    private final String name;
    private final String[] keywordArguments;
    private final String keywordDocumentation;
    private final boolean varargs;

    /**
     * Create keyword.
     *
     * @param name Name, must be not empty.
     * @param arguments Arguments, see {@link #getKeywordArguments()} for details.
     * @param documentation Documentation in Wiki syntax.
     */
    public AbstractKeyword(String name, String[] arguments, String documentation) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("name is empty");
        }
        this.name = name;
        keywordDocumentation = documentation == null ? "" : documentation;
        keywordArguments = arguments.clone();
        varargs = arguments.length > 0 && arguments[arguments.length - 1].startsWith("*");
    }

    /**
     * @see de.codecentric.jrobot.Keyword#getKeywordName()
     */
    @Override
    public String getKeywordName() {
        return name;
    }

    /**
     * @see de.codecentric.jrobot.Keyword#runKeyword(java.lang.Object[])
     */
    @Override
    public Object runKeyword(Object[] argsParam) throws Exception {
        int argsLength;
        Object[] args;
        if (argsParam == null) {
            if (keywordArguments.length == 1) {
                // Make null to one null parameter
                argsLength = 1;
                args = new Object[] { null };
            } else {
                argsLength = 0;
                args = null;
            }
        } else {
            argsLength = argsParam.length;
            args = argsParam.clone();
        }
        // TODO: Insert default values for empty parameters
        if (varargs) {
            return run(argsParam);
        } else {
            if (keywordArguments.length != argsLength) {
                throw new IllegalArgumentException("Keyword " + name + " expects " + keywordArguments.length
                        + " arguments but is called with " + argsLength + " arguments.");
            }
            switch (argsLength) {
            case 0:
                return run();
            case 1:
                return run(args[0]);
            case 2:
                return run(args[0], args[1]);
            case 3:
                return run(args[0], args[1], args[2]);
            case 4:
                return run(args[0], args[1], args[2], args[3]);
            case 5:
                return run(args[0], args[1], args[2], args[3], args[4]);
            default:
                return run(args);
            }
        }
    }

    /**
     * @see de.codecentric.jrobot.Keyword#getKeywordDocumentation()
     */
    @Override
    public String getKeywordDocumentation() {
        return keywordDocumentation;
    }

    /**
     * @see de.codecentric.jrobot.Keyword#getKeywordArguments()
     */
    @Override
    public String[] getKeywordArguments() {
        return keywordArguments.clone();
    }

    /**
     * @return Is this a keyword with variable number of arguments?
     */
    public boolean isVarargs() {
        return varargs;
    }

    /**
     * Should be overridden in implementation classes.
     *
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run() throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be overridden in implementation classes.
     *
     * @param a1 First argument.
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run(Object a1) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be overridden in implementation classes.
     *
     * @param a1 First argument.
     * @param a2 Second argument.
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run(Object a1, Object a2) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be overridden in implementation classes.
     *
     * @param a1 First argument.
     * @param a2 Second argument.
     * @param a3 Third argument.
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run(Object a1, Object a2, Object a3) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be overridden in implementation classes.
     *
     * @param a1 First argument.
     * @param a2 Second argument.
     * @param a3 Third argument.
     * @param a4 4. argument.
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run(Object a1, Object a2, Object a3, Object a4) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be overridden in implementation classes.
     *
     * @param a1 First argument.
     * @param a2 Second argument.
     * @param a3 Third argument.
     * @param a4 4. argument.
     * @param a5 5. argument.
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run(Object a1, Object a2, Object a3, Object a4, Object a5) throws Exception {
        throw new UnsupportedOperationException();
    }

    /**
     * Should be overridden in implementation classes. This method is called when the keyword has more than five
     * arguments or when it allows a variable number of arguments.
     *
     * @param arguments The arguments.
     * @return Never
     * @throws UnsupportedOperationException Always.
     * @throws Exception An easy way to communicate with Robot.
     */
    public Object run(Object[] arguments) throws Exception {
        throw new UnsupportedOperationException();
    }
}
