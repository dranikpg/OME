package com.draniksoft.ome.editor.support.map_load;

import com.artemis.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.utils.struct.ResponseListener;


/**
 * Interface for a manager which either saves or loads a map
 */
public abstract class ProjectLoader {

    public static class Codes {

        public static final short READY = 1;
        public static final short CANCELLED = 2;
        public static final short ERROR = 3;

    }


    MapLoadBundle bundle;
    AssetManager assetManager;
    World world;

    boolean crashed;

    ResponseListener rootL;
    Array<ErrorInfo> errors;


    public abstract boolean load(ResponseListener l);

    public abstract boolean save(ResponseListener l);

    public abstract void tick();

    public void setBundle(MapLoadBundle bundle) {
        this.bundle = bundle;
    }

    public void setAssetManager(AssetManager manager) {
        this.assetManager = manager;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Array<ErrorInfo> getErrors() {
        if (errors == null) return new Array<ErrorInfo>();
        return errors;
    }

    public MapLoadBundle getBundle() {
        return bundle;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void reportError(boolean critical, short t, String msg) {

        errors.add(new ErrorInfo(t, critical, msg));

        if (critical) {
            rootL.onResponse(Codes.ERROR);
            crashed = true;
        }

    }
}
