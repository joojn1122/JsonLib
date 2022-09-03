package com.joojn.json.impl;

import com.joojn.json.util.EntrySet;
import com.joojn.json.JsonElement;
import com.joojn.json.JsonLib;
import com.joojn.json.util.ProtectedMap;
import com.joojn.json.exceptions.JsonUnsupportedException;

public class JsonObject extends ProtectedMap<String, JsonElement> implements JsonElement {

    @Override
    public JsonElement get(String key) {
        return super.get_(key);
    }

    public void clear()
    {
        super.clear_();
    }

    public boolean remove(String key)
    {
        return super.remove_(key);
    }

    @Override
    public void set(String key, Object obj) throws JsonUnsupportedException {
        super.put_(key, JsonLib.getJsonElement(obj));
    }

    @Override
    public JsonObject getAsJsonObject() {
        return this;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("{");

        int i = 0;

        for(EntrySet<String, JsonElement> entry : this.getEntrySet())
        {
            builder.append('"')
                    .append(entry.getKey())
                    .append("\": ")
                    .append(entry.getValue().toString())
                    .append(i != this.size() - 1 ? ", " : "");

            i++;
        }

        return builder + "}";
    }
}
