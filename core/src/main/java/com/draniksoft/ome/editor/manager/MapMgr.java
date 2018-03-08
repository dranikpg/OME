package com.draniksoft.ome.editor.manager;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
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
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.GUtils;

public class MapMgr extends BaseSystem implements LoadSaveManager {

    public static final String tag = "MapMgr";

    volatile String assW = null;

    @Wire
    AssetManager assM;

    /**
     * Only internal
     */
    Texture t;



    public void loadMap(String file) {

        Gdx.app.debug(tag, "Starting map load");

	  assW = file;

        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Nearest;
        textureParameter.minFilter = Texture.TextureFilter.Nearest;


        assM.load(file, Texture.class, textureParameter);

    }


    public void parseMap() {

        Gdx.app.debug(tag, "Parsing map");

	  t = assM.get(assW);

        int s = 500;

        TextureRegion[][] rs = GUtils.splitTITR(t, s, s);

        for (int i = 0; i < rs.length; i++) {
            for (int j = 0; j < rs[i].length; j++) {

		    int e = world.create();

		    PosSizeC sc = world.getMapper(PosSizeC.class).create(e);
		    sc.w = s;
                sc.h = s;
                sc.x = j * s;
                sc.y = i * s;

		    TexRegC c = world.getMapper(TexRegC.class).create(e);
		    c.d = rs[i][j];

		    world.getMapper(MapC.class).create(e);

            }
        }

	  Gdx.app.debug(tag, "Parsed map");

	  assW = null;

    }

    @Override
    public void save(ProjectSaver s) {

        Gdx.app.debug(tag, "Writing pixmap");

	  if (t == null) return;

        t.getTextureData().prepare();

        Pixmap m = t.getTextureData().consumePixmap();

        PixmapIO.writePNG(new FileHandle(AppDO.I.F().getTmpDir().getAbsolutePath() + "/data/m.png"), m);

    }

    @Override
    public void load(ProjectLoader ld) {

	  IntBag aff = world.getAspectSubscriptionManager().get(Aspect.all(MapC.class)).getEntities();

	  /*for (int e : aff.getData()) {
		world.deleteColor(e);
	  }*/

	  loadMap(AppDO.I.F().getTmpDir().getAbsolutePath() + "/data/m.png");


    }



    @Override
    protected void processSystem() {

	  if (assW != null) {
		if (assM.isLoaded(assW)) {
		    parseMap();
            }
        }
    }

}
