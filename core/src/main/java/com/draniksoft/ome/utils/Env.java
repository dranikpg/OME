package com.draniksoft.ome.utils;

public class Env {

    public static boolean DEBUG = true;

    public static boolean GFX_DEBUG = false;

    static {
        if (!DEBUG) GFX_DEBUG = false;
    }
}
