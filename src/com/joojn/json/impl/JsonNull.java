package com.joojn.json.impl;

import com.joojn.json.JsonElement;

public class JsonNull implements JsonElement {

    public static final JsonNull INSTANCE = new JsonNull();

    @Override
    public String toString()
    {
        return "null";
    }


}
