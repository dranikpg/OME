package com.draniksoft.ome.utils;


import com.badlogic.gdx.Preferences;

import java.io.File;

/**
 * File utils class
 */
public class FUtills {

    public static class PrefIdx {

        public static final String localFolder = "loc_fdr";

        public static final String lastOpenings = "last_openings";

    }

    public static class LocalFdrNames {

        public static final String tempF = ".__tempF";

    }


    public static <T> T getVal(Preferences prefs, Class<T> c, String id) {

        if (c == Boolean.class) {
            return (T) Boolean.valueOf(prefs.getBoolean(id));
        } else if (c == Integer.class) {
            return (T) Integer.valueOf(prefs.getInteger(id));
        } else if (c == String.class) {
            return (T) prefs.getString(id);
        }

        return null;

    }

    public static String parseP(String p) {

        return p.substring(6, p.length());

    }

    public static String uriToPath(String p) {

        return p.substring(6, p.length());

    }

    public static int getStorLoc(String p) {

        if (p.startsWith("int://"))
            return 1;
        else if (p.startsWith("loc://"))
            return 2;
        else if (p.startsWith("map://"))
            return 3;

        return -1;

    }

    public static String toUIRPath(String p, int l) {

        String cS;

        if (l == 1) {
            cS = "int://";
        } else if (l == 2) {
            cS = "loc://";
        } else {
            cS = "map://";
        }

        return cS.concat(p);

    }

    /*

    J FILE CHOOSER PART

     */

    public static int JF_OPTION = -5;
    public static File JF_FILE;


}
