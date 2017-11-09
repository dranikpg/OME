package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.draniksoft.ome.editor.load.LoadSaveManager;

public class MapMgr extends Manager implements LoadSaveManager {

    public static final String tag = "MapMgr";

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
