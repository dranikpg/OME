package com.draniksoft.ome.utils;

public class Env {

    public static boolean DEBUG = true;

    public static boolean GFX_DEBUG = false;

    public static boolean HARD_IO_DEBUG = true;

    public static boolean ETTY_JSON_SRZ = true;

    public static boolean PRETTY_JSON = true;

    public static boolean B64D = false;

    static {
        if (!DEBUG) GFX_DEBUG = false;
        if (!DEBUG) HARD_IO_DEBUG = false;
        if (!DEBUG) ETTY_JSON_SRZ = false;
    }

    public static boolean GC_SCHEDULE_FQ = true;
}
