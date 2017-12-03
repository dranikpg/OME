package com.draniksoft.ome.utils;


import com.artemis.World;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.draniksoft.ome.editor.base_gfx.drawable.EmptyDrawable;
import com.draniksoft.ome.editor.manager.drawable.SimpleDrawableMgr;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;

import java.io.File;

/**
 * File utils class
 */
public class FUtills {

    //public static Json json = new Json();

    private static final String METADATA_SEP = "|$|";

    public static JsonReader r = new JsonReader();

    public static final int STORE_L_INT = 1;
    public static final int STORE_L_LOC = 2;
    public static final int STORE_L_MAP = 3;

    public static int createE(World world) {
        return world.create();
    }

    public static class PrefIdx {

        public static final String localFolder = "loc_fdr";

        public static final String lastOpenings = "last_openings";

    }

    public static class LocalFdrNames {

        public static final String tempF = ".__tempF";


        public static final String iAssF = "i_assets/";
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

    /**
     * For MAP assets it DOES NOT RETURN THE FULL PATH
     */
    public static String uriToPath(String u) {

        int us = uriStoreLocation(u);
        if (us == STORE_L_MAP) {
            return AppDO.I.F().getTmpDir().getAbsolutePath() + "/" + u.substring(6);
        } else if (us == STORE_L_INT) {
            return LocalFdrNames.iAssF + u.substring(6);
        }

        return u.substring(6);

    }

    public static String uriToSimpleP(String u) {
        return u.substring(6);
    }

    public static int uriStoreLocation(String p) {

        if (p.startsWith("i"))
            return STORE_L_INT;
        else if (p.startsWith("l"))
            return STORE_L_LOC;
        else if (p.startsWith("m"))
            return STORE_L_MAP;

        return -1;

    }

    public static String pathToUri(String p, int l) {
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


    /**
     * Drawable part
     */

    public static class DrawablePrefix {
        public static final String P_EMPTY = "E:";
        public static final String P_SIMPLE_DW = "O:";
        public static final String P_NINEPATCH = "N:";
        public static final String P_ANIM = "A:";
    }

    private static NinePatch fetchNinePatch(TextureAtlas.AtlasRegion region) {
        NinePatch patch = null;
        if (region instanceof TextureAtlas.AtlasRegion) {
            int[] splits = region.splits;
            if (splits != null) {
                patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                int[] pads = region.pads;
                if (pads != null) patch.setPadding(pads[0], pads[1], pads[2], pads[3]);
            }
        }
        if (patch == null) patch = new NinePatch(region);

        return patch;
    }

    private static TextureAtlas.AtlasRegion fetchAtlasR(String id) {

        return MainBase.engine.getSystem(SimpleDrawableMgr.class).getRegion(id);

    }


    public static Drawable fetchDrawable(String data) {
        if (data.startsWith(DrawablePrefix.P_EMPTY)) {
            return new EmptyDrawable();
        } else if (data.startsWith(DrawablePrefix.P_SIMPLE_DW)) {
            return new TextureRegionDrawable(fetchAtlasR(data.substring(2)));
        }


        return null;
    }


    public static void putPrefsV(Preferences prefs, String id, Object v, Class t) {

        if (t == Boolean.class) {
            prefs.putBoolean(id, (Boolean) v);
        } else if (t == String.class) {
            prefs.putString(id, (String) v);
        } else if (t == Integer.class) {
            prefs.putInteger(id, (Integer) v);
        }

    }


    /*

    J FILE CHOOSER PART

     */

    public static int JF_OPTION = -5;
    public static File JF_FILE;


}
