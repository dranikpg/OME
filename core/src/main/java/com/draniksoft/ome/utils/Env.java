package com.draniksoft.ome.utils;

public class Env {

    public static boolean DEBUG = true;

    public static boolean GFX_DEBUG = false;

    public static boolean PRETTY_JSON = true;

    public static boolean B64D = false;

    static {
        if (!DEBUG) GFX_DEBUG = false;
    }
}
