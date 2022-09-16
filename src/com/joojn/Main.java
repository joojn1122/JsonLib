package com.joojn;

import com.joojn.json.JsonLib;
import com.joojn.json.impl.JsonArray;
import com.joojn.json.impl.JsonObject;

public class Main {

    public static void main(String[] args) {

        JsonObject object = new JsonObject();
        JsonArray arr = new JsonArray();
        arr.addElement(1).addElement(2);

        JsonObject nested = new JsonObject();
        nested.setElement("test", new JsonObject());

        object.setElement("Boolean", false);
        object.setElement("String", "\"Hello World\"");
        object.setElement("Map", nested);
        object.setElement("Array", arr);
        object.setElement("Null", null);
        object.setElement("Number", 1.23);
        object.setElement("Number2", 1);

        System.out.println(
                "JsonObject to string with indent of 4: " + object.toString(4)
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

        System.out.println("Parsed JSON string without indent: " + o);
        System.out.println("Getting float number: " +
                o
                .get("nested_dict")
                .get("nested_list")
                .getElement(0)
                .get("number_")
                .getAsFloat()
        );


    }
}
