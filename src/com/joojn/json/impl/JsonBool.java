package com.joojn.json.impl;

import com.joojn.json.JsonElement;

public class JsonBool implements JsonElement {

    private final boolean bool;

    public JsonBool(boolean bool)
    {
        this.bool = bool;
    }

    public JsonBool(String bool)
    {
        this.bool = bool.equals("true");
    }

    @Override
    public boolean getAsBoolean() {
        return this.bool;
    }

    @Override
    public String toString() {
        return getAsBoolean() ? "true" : "false";
    }
}
