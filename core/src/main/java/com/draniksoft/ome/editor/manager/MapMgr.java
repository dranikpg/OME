package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.components.gfx.TexRegC;
import com.draniksoft.ome.editor.components.pos.PosSizeC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.GUtils;

public class MapMgr extends BaseSystem implements LoadSaveManager {

    public static final String tag = "MapMgr";

    boolean aviab = false;
    int w, h;

    int qsize;
    int tsize;

    String waiting = null;

    @Wire
    AssetManager assM;

    /**
     * Only internal
     */
    Pixmap map;
    Texture t;

    /**
     *
     */

    ArchTransmuterMgr archM;


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

    public void loadMap(String file) {

        Gdx.app.debug(tag, "Starting map load");

        waiting = file;

        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Nearest;
        textureParameter.minFilter = Texture.TextureFilter.Nearest;


        assM.load(file, Texture.class, textureParameter);
    }


    private void parseMap() {

        Gdx.app.debug(tag, "Parsing map");

        t = assM.get(waiting);

        int s = 500;

        TextureRegion[][] rs = GUtils.splitTITR(t, s, s);

        for (int i = 0; i < rs.length; i++) {
            for (int j = 0; j < rs[i].length; j++) {

                int e = world.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.MAP_C);

                PosSizeC sc = world.getMapper(PosSizeC.class).get(e);
                sc.w = s;
                sc.h = s;
                sc.x = j * s;
                sc.y = i * s;

                TexRegC c = world.getMapper(TexRegC.class).get(e);
                c.d = rs[i][j];

            }
        }

    }

    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

        Gdx.app.debug(tag, "Writing pixmap");

        t.getTextureData().prepare();

        Pixmap m = t.getTextureData().consumePixmap();

        PixmapIO.writePNG(new FileHandle(AppDO.I.F().getTmpDir().getAbsolutePath() + "/data/m.png"), m);

    }

    @Override
    protected void processSystem() {

        if (waiting != null) {
            if (assM.isLoaded(waiting)) {
                parseMap();
                waiting = null;
            }
        }

    }

}
