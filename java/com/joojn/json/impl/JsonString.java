package com.joojn.json.impl;

import com.joojn.json.JsonElement;

public class JsonString implements JsonElement {

    private final String str;

    public JsonString(String str)
    {
        this.str = str;
    }

    @Override
    public String getAsString() {
        return this.str;
    }

    @Override
    public String toString()
    {
        return '"' + this.getAsString().replace("\"", "\\\"") + '"';
    }

}
