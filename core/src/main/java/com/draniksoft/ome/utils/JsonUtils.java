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

    public static <T> T getVal(Class<T> c, JsonValue v) {
        if (c == Boolean.class) {
            return (T) new Boolean(v.asBoolean());
        } else if (c == Integer.class) {
            return (T) new Integer(v.asInt());
        } else if (c == String.class) {
            return (T) v.asString();
        }

        return null;

    }

    public static Object parseTpye(String kS, Class confT) {
        if (confT == Boolean.class) {
            return Boolean.parseBoolean(kS);
	  } else if (confT == String.class) {
		return kS;
	  } else if (confT == Integer.class) {
		return Integer.valueOf(kS);
	  }

        return null;
    }

}
