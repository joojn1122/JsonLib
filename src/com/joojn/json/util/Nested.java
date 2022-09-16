package com.joojn.json.util;

import com.joojn.json.JsonElement;

public class Nested {

    public interface ElementRunnable{
        JsonElement call(String text);
    }

    public boolean writing = false;
    public boolean inQuotes;

    public char openBracket;
    public char closeBracket;

    public String text;

    public int brackets;

    public ElementRunnable func;

}
