package com.draniksoft.ome.utils;

import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.utils.lang.BiLangText;
import com.draniksoft.ome.utils.lang.I18NText;
import com.draniksoft.ome.utils.lang.PlainText;
import com.draniksoft.ome.utils.lang.Text;

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

    public static JsonValue createBoolV(boolean b) {
	  JsonValue v = new JsonValue(JsonValue.ValueType.booleanValue);
	  v.set(b);
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

    public static Text parseText(JsonValue v) {
	  return parseText(v, true);
    }

    public static Text parseText(JsonValue v, boolean emptyError) {
	  if (v == null) return new PlainText(emptyError ? "*ERROR*" : "");
	  if (v.type() == JsonValue.ValueType.stringValue) {
		String s = v.asString();
		if (s.startsWith(":@")) {
		    return new I18NText(s.substring(2));
		} else {
		    return new PlainText(s);
		}
	  } else if (v.hasChild("en") && v.hasChild("ru")) {
		return new BiLangText(
			  v.getString("en"),
			  v.getString("ru"));
	  }
	  return new PlainText(emptyError ? "*ERROR*" : "");
    }

}
