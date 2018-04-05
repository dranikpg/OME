package com.draniksoft.ome.utils;


import com.artemis.World;
import com.artemis.io.JsonArtemisSerializer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.manager.uslac.TextureRManager;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.drawable.simple.EmptyDrawable;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.pipemsg.MsgBaseCodes;
import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.utils.struct.Points;

import java.io.File;

/**
 * File utils class
 */
public class FUtills {

    public static final short[] NULL_ARRAY = new short[]{};


    public static JsonReader r = new JsonReader();

    public static synchronized JsonReader r() {
        return r;
    }

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

        public static final String tempF = ".temp";

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
        return uriToPath(uriStoreLocation(u), u.substring(6));
    }

    public static String uriToPath(int us, String sub) {
        if (us == STORE_L_MAP) {
            return AppDO.I.F().getTmpDir().path() + "/" + sub;
        } else if (us == STORE_L_INT) {
            return sub;
        } else if (us == STORE_L_LOC) {
            return AppDO.I.F().getHomeDir().path() + "/" + sub;
        }
        return sub;
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

    public static FileHandle uriToFile(String u) {
        int us = uriStoreLocation(u);
        return uriToFile(us, u.substring(6));
    }

    public static FileHandle uriToFile(int us, String u) {
        if (us == STORE_L_INT) {
            return Gdx.files.internal(u);
        } else if (us == STORE_L_MAP) {
            return Gdx.files.absolute(uriToPath(us, u));
        } else if (us == STORE_L_LOC) {
            return Gdx.files.absolute(uriToPath(us, u));
        }
        return null;
    }


    /**
     * Drawable part
     */

    public static class DrawablePrefix {
        public static final String P_EMPTY = "E:";
	  public static final String P_SIMPLE_DW = "S:";
    }


    public static TextureRAccesor getRAC(String uri) {
        return MainBase.engine.getSystem(TextureRManager.class).get(uri);
    }

    public static void updateUsage(TextureRAccesor ac, int dlt) {
        MainBase.engine.getSystem(TextureRManager.class).updateUsage(ac, dlt);
    }



    public static Drawable fetchDrawable(String data) {

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


    public static void registerJSrz(final JsonArtemisSerializer serializer) {

        serializer.register(Points.class, new Json.Serializer() {

            @Override
            public void write(Json json, Object object, Class knownType) {
                Points p = (Points) object;

                json.writeArrayStart();

                for (Vector2 pt : p) {
                    json.writeValue(pt.x);
                    json.writeValue(pt.y);
                }

                json.writeArrayEnd();

            }

            @Override
            public Object read(Json json, JsonValue jsonData, Class type) {
                Points inst = new Points();
                float[] ar = jsonData.asFloatArray();

                for (int i = 0; i < ar.length; i += 2) {
                    inst.add(new Vector2(ar[i], ar[i + 1]));
                }

                return inst;
            }
        });

    }


    public static FreeTypeFontGenerator.FreeTypeFontParameter parseTTFParameter(JsonValue jsonValue) {
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.size = 40;
        return p;
    }

    public static JsonValue getTTFParameterJ(FreeTypeFontGenerator.FreeTypeFontParameter p) {
        return new JsonValue(JsonValue.ValueType.object);
    }


    public static int JF_OPTION = -5;
    public static File JF_FILE;

    /*
        Drawable utils
     */


    public static Drawable buildDrawable(ResConstructor<Drawable> c) {

        Gdx.app.debug("--", "Building drawable");

        Drawable d;

        if (c == null) d = new EmptyDrawable();
	  else d = c.build(null).self();

        d.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);

        return d;
    }

    public static void copyAtlasRegion(TextureAtlas.AtlasRegion src, TextureAtlas.AtlasRegion dst) {
	  dst.index = src.index;
	  dst.name = src.name;
	  dst.offsetX = src.offsetX;
	  dst.offsetY = src.offsetY;
	  dst.packedWidth = src.packedWidth;
	  dst.packedHeight = src.packedHeight;
	  dst.originalWidth = src.originalWidth;
	  dst.originalHeight = src.originalHeight;
	  dst.rotate = src.rotate;
	  dst.splits = src.splits;
	  dst.setRegion(src);
    }
}
