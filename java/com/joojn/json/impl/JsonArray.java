package com.joojn.json.impl;

import com.joojn.json.JsonElement;
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
    public void addElement(JsonElement el) {
        super.add_(el == null ? new JsonNull() : el);
    }

    public void clear()
    {
        this.clear_();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("[");

        for(int i = 0; i < this.size() ; i++)
        {
            JsonElement el = this.get_(i);

            builder.append(el.toString()).append(i != this.size() - 1 ? ", " : "");
        }

        return builder + "]";
    }
}
