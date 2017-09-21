package com.draniksoft.ome.utils;

import com.badlogic.gdx.utils.JsonValue;

public class JsonUtils {

    public static JsonValue createIntV(int i) {

        JsonValue v = new JsonValue(JsonValue.ValueType.longValue);
        v.set(i, null);

        return v;

    }

    public static JsonValue createIntV(String n, int i) {

        JsonValue v = createIntV(i);
        v.setName(n);
        return v;

    }

    public static JsonValue createStringV(String s) {

        JsonValue v = new JsonValue(JsonValue.ValueType.stringValue);
        v.set(s);
        return v;

    }

    public static JsonValue createStringV(String n, String val) {

        JsonValue v = createStringV(val);
        v.setName(n);
        return v;

    }

}
