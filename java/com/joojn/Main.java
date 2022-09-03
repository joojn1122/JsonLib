package com.joojn;

import com.joojn.json.JsonLib;
import com.joojn.json.impl.JsonArray;
import com.joojn.json.impl.JsonObject;

public class Main {

    public static void main(String[] args) {

        JsonObject object = new JsonObject();
        JsonArray arr = new JsonArray();

        JsonObject nested = new JsonObject();

        object.set("Boolean", false);
        object.set("String", "\"Hello World\"");
        object.set("Map", nested);
        object.set("Array", arr);
        object.set("Null", null);
        object.set("Number", 1.23);
        object.set("Number2", 1);

        System.out.println(
                object.toString()
        );

        String json = "{" +
                "\"text\" : \"\\\"Hello World\\\"\"," +
                "\"boolean\" : true," +
                "\"nested_dict\" : {" +
                "\"nested_list\" : [" +
                "{" +
                "\"number\" : 1, " +
                "\"number_\" : 1.23, " +
                "\"null\" : null" +
                "}, " +
                "\"123\", " +
                "\"1234\"" +
                "]" +
                "}}";

        JsonObject o = JsonLib.parseString(json).getAsJsonObject();

        System.out.println(o);
        System.out.println(o
                .get("nested_dict")
                .get("nested_list")
                .getElement(0)
                .get("number_")
                .getAsFloat()
        );


    }
}
