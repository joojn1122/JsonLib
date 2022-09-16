package com.joojn.json.impl;

import com.joojn.json.JsonElement;
import com.joojn.json.JsonLib;
import com.joojn.json.util.ProtectedArray;


public class JsonArray extends ProtectedArray<JsonElement> implements JsonElement {

    @Override
    public JsonArray getAsJsonArray() {
        return this;
    }

    @Override
    public JsonElement getElement(int index) {
        return super.get_(index);
    }

    @Override
    public JsonArray addElement(Object el) {
        super.add_(JsonLib.getJsonElement(el));

        return this;
    }

    public void clear()
    {
        this.clear_();
    }

    @Override
    public String __toString__(int indent, int caller)
    {
        if(this.size() == 0) return "[]";

        int indent_ = caller * indent;

        StringBuilder builder = new StringBuilder("[").append(indent_ > 0 ? "\n" : "");

        for(int i = 0; i < this.size() ; i++)
        {
            JsonElement el = this.get_(i);

            builder
                    .append(JsonLib.getStringMultiplier(" ", indent_))
                    .append(el.__toString__(indent, caller + 1))
                    .append(i != this.size() -1 ? ", " : "")
                    .append((indent_ > 0) ? "\n" : "");
        }

        return builder
                .append(caller != 1 ? JsonLib.getStringMultiplier(" ", indent_ - indent) : "")
                + "]";
    }

    @Override
    public String toString()
    {
        return toString(0);
    }
}
