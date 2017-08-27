package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.components.PosSizeC;
import com.draniksoft.ome.editor.components.TexRegC;
import com.draniksoft.ome.editor.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.utils.GUtils;
import com.draniksoft.ome.utils.struct.Pair;

public class MapManager extends Manager implements LoadSaveManager {

    public static final String tag = "MapManager";

    boolean aviab = false;
    int w, h;

    int qsize;
    int tsize;

    /**
     * Only internal
     */
    Pixmap map;
    String mpath;
    String mname;

    Texture t;

    /**
     *
     */

    ArchTransmuterMgr archM;

    @Override
    public String getNode() {
        return "map";
    }

    public void loadL(JsonValue val, ProjectLoader l) {


        mname = val.get("name").asString();
        mpath = l.getBundle().fPath + "/data/" + mname;


        tsize = val.get("tsize").asInt();

        if (tsize > GUtils.maxTSize) {

            Gdx.app.debug(tag, "Max T size exceeded");

            map = new Pixmap(Gdx.files.external(mpath));

        } else {

            TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
            textureParameter.magFilter = Texture.TextureFilter.Linear;
            textureParameter.minFilter = Texture.TextureFilter.Linear;

            l.getAssetManager().load(mpath, Texture.class, textureParameter);

        }


    }

    @Override
    public boolean loadG(ProjectLoader l) {

        t = l.getAssetManager().get(mpath, Texture.class);

        int ts = 500;

        TextureRegion[][] rgs = GUtils.splitTITR(t, ts, ts);


        for (int i = 0; i < rgs.length; i++) {

            for (int j = 0; j < rgs.length; j++) {

                int e = archM.build(ArchTransmuterMgr.Codes.MAP_C);

                TexRegC c = world.getMapper(TexRegC.class).get(e);
                c.d = rgs[i][j];

                PosSizeC p = world.getMapper(PosSizeC.class).get(e);

                p.w = rgs[i][j].getRegionWidth();
                p.h = rgs[i][j].getRegionHeight();
                p.x = j * ts;
                p.y = i * ts;

            }

        }

        return true;
    }

    @Override
    public Pair<String, JsonValue> save() {

        JsonValue v = new JsonValue(JsonValue.ValueType.object);

        JsonValue maxT = new JsonValue(JsonValue.ValueType.longValue);
        maxT.setName("tsize");
        maxT.set((long) tsize, String.valueOf(maxT));

        JsonValue mapN = new JsonValue(JsonValue.ValueType.stringValue);
        mapN.set(mname);
        mapN.setName("name");

        v.addChild(maxT);
        v.addChild(mapN);

        return Pair.createPair("map", v);
    }


    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getQsize() {
        return qsize;
    }

    public int getTsize() {
        return tsize;
    }

    public boolean isAviab() {
        return aviab;
    }


}
