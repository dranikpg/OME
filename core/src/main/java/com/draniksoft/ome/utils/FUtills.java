package com.draniksoft.ome.utils;


import com.artemis.World;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.KryoArtemisSerializer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.manager.drawable.SimpleAssMgr;
import com.draniksoft.ome.editor.res.drawable.simple.EmptyDrawable;
import com.draniksoft.ome.editor.res.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.struct.Points;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.File;

import static com.draniksoft.ome.utils.FUtills.DrawablePrefix.P_SIMPLE_DW;

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

    public static class ConfingN {

	  public static String focusOnMapObjSel = "focus_on_mapobj_sel";

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
	  public static final String P_SIMPLE_DW = "S:";
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

    public static TextureAtlas.AtlasRegion fetchAtlasR(String id) {

	  return MainBase.engine.getSystem(SimpleAssMgr.class).getRegion(id);

    }



    public static Drawable fetchDrawable(String data) {
        if (data.startsWith(DrawablePrefix.P_EMPTY)) {
            return new EmptyDrawable();
        } else if (data.startsWith(P_SIMPLE_DW)) {
            return SimpleDrawable.parse(data.substring(2));
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


    public static void registerBSrz(KryoArtemisSerializer serializer) {

        serializer.register(Array.class, new Serializer<Array>() {
            {
                setAcceptsNull(true);
            }

            private Class genericType;

            public void setGenerics(Kryo kryo, Class[] generics) {
                if (kryo.isFinal(generics[0])) genericType = generics[0];
            }

            public void write(Kryo kryo, Output output, Array array) {
                int length = array.size;
                output.writeInt(length, true);
                if (length == 0) {
                    genericType = null;
                    return;
                }
                if (genericType != null) {
                    Serializer serializer = kryo.getSerializer(genericType);
                    genericType = null;
                    for (Object element : array)
                        kryo.writeObjectOrNull(output, element, serializer);
                } else {
                    for (Object element : array)
                        kryo.writeClassAndObject(output, element);
                }
            }

            public Array read(Kryo kryo, Input input, Class<Array> type) {
                Array array = new Array();
                kryo.reference(array);
                int length = input.readInt(true);
                array.ensureCapacity(length);
                if (genericType != null) {
                    Class elementClass = genericType;
                    Serializer serializer = kryo.getSerializer(genericType);
                    genericType = null;
                    for (int i = 0; i < length; i++)
                        array.add(kryo.readObjectOrNull(input, elementClass, serializer));
                } else {
                    for (int i = 0; i < length; i++)
                        array.add(kryo.readClassAndObject(input));
                }
                return array;
            }
        });

        serializer.register(Vector2.class, new Serializer() {
            @Override
            public void write(Kryo kryo, Output output, Object object) {
                Vector2 v = (Vector2) object;
                output.writeFloat(v.x);
                output.writeFloat(v.y);
            }

            @Override
            public Object read(Kryo kryo, Input input, Class type) {
                Vector2 inst = new Vector2();
                inst.x = input.readFloat();
                inst.y = input.readFloat();
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


}
