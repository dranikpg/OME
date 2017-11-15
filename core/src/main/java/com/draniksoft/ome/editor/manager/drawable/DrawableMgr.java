package com.draniksoft.ome.editor.manager.drawable;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.utils.dao.AssetDDao;

import java.util.Iterator;

public abstract class DrawableMgr extends BaseSystem implements LoadSaveManager {

    public abstract Iterator<AssetDDao> getLoadedDaoI();

    public abstract Iterator<AssetDDao> getAviabDaoI();

    public abstract Iterator<ObjectMap.Entry<String, String>> getRedirectsI();


    public abstract boolean hasAtlas(String id, boolean useRds);

    public abstract AssetDDao getLoadedDao(String id);

    public abstract AssetDDao getAviabDao(String id);


    public abstract String getRedirect(String id);

    public abstract void putRedirect(String src, String dest);


    public abstract void loadDao(AssetDDao dao);

    public abstract void unloadAsset(String id);


    /**
     * @param idx -1 returns the first match
     * @return
     */
    public abstract TextureAtlas.AtlasRegion getRegion(String atlas, String name, int idx);

    public abstract TextureAtlas.AtlasRegion getRegion(String id);

    public abstract TextureAtlas getAtlas(String id);


    public abstract void resetSystemRedirects();


}
