package com.draniksoft.ome.utils;


/**
 * File utils class
 */
public class FUtills {

    public static class PrefIdx {

        public static final String localFolder = "loc_fdr";

    }


    public static class ConstFileN {

        public static final String assetAll = "f";

    }

    public void globalSave() {

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

}
