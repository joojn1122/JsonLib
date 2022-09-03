package com.joojn.json.impl;

import com.joojn.json.JsonElement;

public class JsonNumber implements JsonElement {

    private final Object number;

    public JsonNumber(Number number)
    {
        this.number = number;
    }

    public JsonNumber(String number)
    {
        this.number = number;
    }

    @Override
    public int getAsInt() {
        return (number instanceof String) ? Integer.parseInt((String) number) : ((Number) number).intValue();
    }

    @Override
    public float getAsFloat() {
        return (number instanceof String) ? Float.parseFloat((String) number) : ((Number) number).floatValue();
    }

    @Override
    public long getAsLong() {
        return (number instanceof String) ? Long.parseLong((String) number) : ((Number) number).longValue();
    }

    @Override
    public double getAsDouble() {
        return (number instanceof String) ? Double.parseDouble((String) number) : ((Number) number).doubleValue();
    }

    @Override
    public String toString()
    {
        return this.number.toString();
    }
}
