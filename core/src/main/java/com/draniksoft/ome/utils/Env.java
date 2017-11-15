package com.draniksoft.ome.utils;

import java.lang.reflect.Field;

public class Env {

    public static boolean DEBUG = true;

    public static boolean GFX_DEBUG = false;

    public static boolean HARD_IO_DEBUG = true;

    public static boolean E_JSON_SRZ = true;

    public static boolean PRETTY_JSON = true;

    public static boolean B64D_JS = false;


    public static boolean GC_SCHEDULE_FQ = true;


    static {
	  updateVals();
    }

    public static void updateVals() {
	  if (!DEBUG) GFX_DEBUG = false;
	  if (!DEBUG) HARD_IO_DEBUG = false;
	  if (!DEBUG) E_JSON_SRZ = false;
	  if (!DEBUG) PRETTY_JSON = false;
	  if (!DEBUG) B64D_JS = true;
    }

    public static void changeVal(String name, boolean val) {

	  try {
		Field f = Env.class.getDeclaredField(name);

		f.setBoolean(null, val);
	  } catch (Exception e) {

	  }

	  updateVals();
    }

}
