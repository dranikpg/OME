package com.draniksoft.ome.editor.map_load;

import com.artemis.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.utils.ResponseListener;


/**
 *
 * Interface for a manager which either saves or loads a map
 *
 */
public interface MapLoader {

    public static class Codes{

        public static final short READY = 1;
        public static final short ERROR = 3;

    }


    public boolean load(ResponseListener l);

    public void tick();

    public void setBundle(MapLoadBundle bundle);

    public void setWorld(World world);

    public void setManager(AssetManager manager);

    public Array<ErrorInfo> getErrors();

}
