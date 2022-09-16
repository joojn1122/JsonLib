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
    public JsonObject setElement(String key, Object obj) throws JsonUnsupportedException {
        super.put_(key, JsonLib.getJsonElement(obj));

        return this;
    }

    @Override
    public JsonObject getAsJsonObject() {
        return this;
    }

    @Override
    public String __toString__(int indent, int caller)
    {
        if(this.size() == 0) return "{}";

        int indent_ = caller * indent;

        StringBuilder builder = new StringBuilder("{").append(indent_ > 0 ? "\n" : "");

        int i = 0;
        for(EntrySet<String, JsonElement> entry : this.getEntrySet())
        {
            builder
                    .append(JsonLib.getStringMultiplier(" ", indent_))
                    .append('"')
                    .append(JsonLib.escapeString(entry.getKey()))
                    .append("\": ")
                    .append(entry.getValue().__toString__(indent, caller + 1));

            if(i != this.size() - 1)
            {
                builder
                        .append(", ")
                        .append(indent_ > 0 ? "\n" : "");
            }

            i++;
        }

        if(indent_ > 0)
        {
            builder.append("\n").append(caller != 1 ? JsonLib.getStringMultiplier(" ", indent_ - indent) : "");
        }

        return builder + "}";
    }

    @Override
    public String toString()
    {
        return toString(0);
    }
}
