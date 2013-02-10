package de.codecentric.jrobot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract helper class to make it easier to implement a keyword library.
 * The method names and signatures are defined by the Robot framework.
 */
public abstract class AbstractDynamicLibrary implements DynamicLibrary {
    /**
     * Determines how long instances of library classes live. Has
     * to be stored in <code>public static final String ROBOT_LIBRARY_SCOPE</scope>.
     */
    public enum Scope {
        /** Create a new instance for each test case */
        TEST_CASE,
        /** Create a new instance for each test suite */
        TEST_SUITE,
        /** Singleton, one instance for all test cases / suites. */
        GLOBAL
    }
    private Map<String, Keyword> keywordMap;

    /**
     * Create a library with no keywords.
     */
    public AbstractDynamicLibrary() {
        keywordMap = new TreeMap<String, Keyword>();
    }

    /**
     * Add a new keyword to the library. Should be called from constructor only,
     * otherwise Robot may get confused.
     * @param keyword The new keyword.
     */
    public void add(Keyword keyword) {
        keywordMap.put(keyword.getKeywordName(), keyword);
    }

    /**
     * Add new keywords to the library. Should be called from constructor only,
     * otherwise Robot may get confused.
     * @param keywords The new keywords.
     */
    protected void addAll(Collection<Keyword> keywords) {
        for (Keyword k : keywords) {
            add(k);
        }
    }

    /**
     * @see de.codecentric.jrobot.DynamicLibrary#getKeywordNames()
     */
    @Override
    public List<String> getKeywordNames() {
        return new ArrayList<String>(keywordMap.keySet());
    }

    /**
     * @see de.codecentric.jrobot.DynamicLibrary#runKeyword(java.lang.String, java.lang.Object[])
     */
    @Override
    public Object runKeyword(String name, Object... args) throws Exception {
        return keyword(name).runKeyword(args);
    }

    /**
     * @see de.codecentric.jrobot.DynamicLibrary#getKeywordDocumentation(java.lang.String)
     */
    @Override
    public String getKeywordDocumentation(String name) {
        if ("__intro__".equals(name)) {
            return getLibraryDocumentation();
        } else {
            return keyword(name).getKeywordDocumentation();
        }
    }

    /**
     * @see de.codecentric.jrobot.DynamicLibrary#getKeywordArguments(java.lang.String)
     */
    @Override
    public String[] getKeywordArguments(String name) {
        return keyword(name).getKeywordArguments();
    }

    /**
     * @return Introduction to the library in Robot's Wiki syntax.
     */
    public abstract String getLibraryDocumentation();

    /**
     * Write the documentation of this keyword library in XML format.
     * The filename is the fully qualified class name with suffix ".xml".
     * @param directory Directory we write to.
     * @throws IOException The usual I/O failures may happen
     */
    public void generateXmlDocumentation(File directory) throws IOException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        File file = new File(directory, getClass().getName() + ".xml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        try {
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<keywordspec generated=\"");
            bw.write(df.format(new Date()));
            bw.write("\" format=\"ROBOT\" type=\"library\" name=\"");
            bw.write(getClass().getName());
            bw.write("\">");
            bw.newLine();
            bw.write("<version></version>");
            bw.newLine();
            bw.write("<scope>");
            bw.write(getScope());
            bw.write("</scope>");
            bw.newLine();
            bw.write("<namedargs>no</namedargs>");
            bw.newLine();
            bw.write("<doc>");
            bw.write(quoteXml(getKeywordDocumentation("__intro__")));
            bw.write("</doc>");
            bw.newLine();
            for (Keyword kw : keywordMap.values()) {
                bw.write("<kw name=\"" + kw.getKeywordName() + "\">");
                bw.newLine();
                bw.write("<arguments>");
                bw.newLine();
                for (String s : kw.getKeywordArguments()) {
                    bw.write("<arg>");
                    bw.write(quoteXml(s));
                    bw.write("</arg>");
                    bw.newLine();
                }
                bw.write("</arguments>");
                bw.newLine();
                bw.write("<doc>");
                bw.write(quoteXml(kw.getKeywordDocumentation()));
                bw.write("</doc>");
                bw.newLine();
                bw.write("</kw>");
                bw.newLine();
            }
            bw.write("</keywordspec>");
            bw.newLine();
        } finally {
            bw.close();
        }
    }

    private String quoteXml(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<') {
                sb.append("&lt;");
            } else if (ch == '>') {
                sb.append("&gt;");
            } else if (ch == '&') {
                sb.append("&amp;");
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private String getScope() {
        try {
            Class<? extends AbstractDynamicLibrary> clazz = getClass();
            Field field = clazz.getField("ROBOT_LIBRARY_SCOPE");
            return ((String) field.get(null)).toLowerCase();
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Keyword keyword(String name) {
        Keyword kw = keywordMap.get(name);
        if (kw == null) {
            throw new IllegalArgumentException("Unknown keyword: " + name);
        }
        return kw;
    }
}
