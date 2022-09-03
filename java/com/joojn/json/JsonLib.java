package com.joojn.json;

import com.joojn.json.exceptions.JsonParseException;
import com.joojn.json.exceptions.JsonUnsupportedException;
import com.joojn.json.impl.*;
import com.joojn.json.util.Nested;

import java.util.Arrays;
import java.util.List;

public class JsonLib {

    // TODO:
//    public static String jsonToString(JsonElement element, int intends)
//    {
//
//        return "";
//    }

    private static JsonArray parseArray(String str)
    {
        JsonArray array = new JsonArray();
        Nested nested = new Nested();

        StringBuilder value = new StringBuilder();
        Character lastChar = null;

        boolean inQuotes = false;

        for(char c : str.toCharArray())
        {
            if(inQuotes)
            {
                if(c == '"' && lastChar != '\\')
                {
                    inQuotes = false;

                    value.append('"');
                }
                else if(c != '\\')
                {
                    value.append(c);
                }
            }
            else if(nested.writing)
            {
                if(c == '"' && lastChar != '\\')
                {
                    nested.inQuotes = !nested.inQuotes;
                }
                else if(c == nested.openBracket && !nested.inQuotes)
                {
                    nested.brackets++;
                }
                else if(c == nested.closeBracket && !nested.inQuotes)
                {
                    nested.brackets--;

                    if(nested.brackets == 0)
                    {
                        nested.text += nested.closeBracket;

                        array.addElement(nested.func.call(nested.text));

                        value.setLength(0);
                        nested.writing = false;
                    }
                }

                nested.text += c;
            }
            else if(c == '{' || c == '[')
            {
                nested.writing = true;
                nested.openBracket = c;
                nested.text = "";
                nested.inQuotes = false;
                nested.brackets = 1;

                if(c == '{')
                {
                    nested.closeBracket = '}';
                    nested.func = JsonLib::parseObject;
                }
                else
                {
                    nested.closeBracket = ']';
                    nested.func = JsonLib::parseArray;
                }
            }
            else if(c == ']')
            {
                if(value.length() != 0)
                {
                    array.addElement(getJsonElementFromString(value.toString()));
                }

                return array;
            }
            else if(c == '"')
            {
                inQuotes = true;

                value.append('"');
            }
            else if(c == ',')
            {
                if(value.length() == 0)
                {
                    continue;
                }

                array.addElement(getJsonElementFromString(value.toString()));
                value.setLength(0);
            }
            else if(!specialChars.contains(c))
            {
                value.append(c);
            }

            lastChar = c;
        }

        throw new JsonParseException();
    }

    private static JsonObject parseObject(String str)
    {
        JsonObject dict = new JsonObject();

        boolean writingKey = true;
        boolean inQuotes = false;

        String key = "";
        String value = "";

        Character last_char = null;

        Nested nested = new Nested();

        for(char c : str.toCharArray())
        {
            if(inQuotes)
            {
                if(c == '"' && last_char != '\\') // escape string
                {
                    inQuotes = false;

                    if(writingKey)
                    {
                        key = value;
                        value = "";
                    }
                    else
                    {
                        value = '"' + value + '"';
                    }

                }
                else if(c != '\\')
                {
                    value += c;
                }
            }
            else if(nested.writing)
            {
                if(c == '"' && last_char != '\\')
                {
                    nested.inQuotes = !nested.inQuotes;
                }
                else if(c == nested.openBracket && !nested.inQuotes)
                {
                    nested.brackets++;
                }
                else if(c == nested.closeBracket && !nested.inQuotes)
                {
                    nested.brackets--;

                    if(nested.brackets == 0)
                    {
                        nested.text += nested.closeBracket;

                        dict.set(key, nested.func.call(nested.text));

                        value = "";
                        key = "";

                        nested.writing = false;
                    }
                }

                nested.text += c;
            }
            else if(c == '{' || c == '[')
            {
                nested.writing = true;
                nested.openBracket = c;
                nested.text = "";
                nested.brackets = 1;
                nested.inQuotes = false;

                if(c == '{')
                {
                    nested.closeBracket = '}';
                    nested.func = JsonLib::parseObject;
                }
                else
                {
                    nested.closeBracket = ']';
                    nested.func = JsonLib::parseArray;
                }
            }
            else if(c == '}')
            {
                if(key.length() != 0)
                {
                    dict.set(key, getJsonElementFromString(value));
                }

                return dict;
            }
            else if(c == '"')
            {
                inQuotes = true;
                value = "";
            }
            else if(c == ':')
            {
                writingKey = false;
            }
            else if(c == ',')
            {
                writingKey = true;

                if (value.length() == 0)
                {
                    continue;
                }

                dict.set(key, getJsonElementFromString(value));
            }
            else if(!specialChars.contains(c))
            {
                if(writingKey)
                {
                    throw new JsonParseException();
                }

                value += c;
            }

            last_char = c;
        }

        throw new JsonParseException();
    }

    public static JsonElement parseString(String str) throws JsonParseException {

        int i = 0;

        for(char c : str.toCharArray())
        {
            if(c == '{')
            {
                return parseObject(str.substring(i + 1));
            }

            if(c == '[')
            {
                return parseArray(str.substring(i + 1));
            }

            i++;
        }

        throw new JsonParseException("Invalid input!");
    }

    private static final List<Character> numberChars = Arrays.asList(
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.', '-'
    );

    private static final List<Character> specialChars = Arrays.asList(
        '\t', '\n', '\r', ' '
    );

    private static boolean isNumber(String input)
    {
        for(char c : input.toCharArray())
        {
            if(!numberChars.contains(c)) return false;
        }

        return true;
    }

    public static JsonElement getJsonElement(Object obj)
    {
        if(obj instanceof String)
        {
            obj = new JsonString((String) obj);
        }
        else if(obj instanceof Boolean)
        {
            obj = new JsonBool((Boolean) obj);
        }
        else if(obj instanceof Number)
        {
            obj = new JsonNumber((Number) obj);
        }
        else if(obj == null)
        {
            obj = new JsonNull();
        }

        if(!(obj instanceof JsonElement))
        {
            throw new JsonUnsupportedException("This object is not supported!");
        }

        return (JsonElement) obj;
    }

    protected static JsonElement getJsonElementFromString(String str)
    {
        if(str.equals("null"))
        {
            return new JsonNull();
        }

        if(str.equals("false") || str.equals("true"))
        {
            return new JsonBool(str);
        }

        if(isNumber(str))
        {
            return new JsonNumber(str);
        }

        if(str.startsWith("\"") && str.endsWith("\""))
        {
            return new JsonString(str.substring(1, str.length() - 1));
        }

        throw new UnsupportedOperationException("Invalid value! '" + str + "'");
    }
}
