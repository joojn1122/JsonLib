package com.joojn.json;

import com.joojn.json.exceptions.JsonUnsupportedException;
import com.joojn.json.impl.JsonArray;
import com.joojn.json.impl.JsonObject;

public interface JsonElement  {

    default JsonElement getElement(int index) {
        throw new JsonUnsupportedException();
    }
    default void addElement(JsonElement element) {
        throw new JsonUnsupportedException();
    }

    default JsonElement get(String key) {
        throw new JsonUnsupportedException();
    }
    default void set(String key, Object object) {
        throw new JsonUnsupportedException();
    }

    default String getAsString() {
        throw new JsonUnsupportedException();
    }

    default boolean getAsBoolean() {
        throw new JsonUnsupportedException();
    }

    default JsonArray getAsJsonArray() {
        throw new JsonUnsupportedException();
    }

    default JsonObject getAsJsonObject() {
        throw new JsonUnsupportedException();
    }

    default int getAsInt() {
        throw new JsonUnsupportedException();
    }
    default float getAsFloat() {
        throw new JsonUnsupportedException();
    }
    default long getAsLong() {
        throw new JsonUnsupportedException();
    }
    default double getAsDouble() {
        throw new JsonUnsupportedException();
    }

}
