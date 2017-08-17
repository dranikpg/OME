package com.draniksoft.ome.editor.map_load;

import com.artemis.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.utils.ResponseListener;

public class MapLoaderImpl implements MapLoader{

    public static final String tag = MapLoaderImpl.class.getSimpleName();



    MapLoadBundle bundle;
    AssetManager manager;
    World world;

    ResponseListener rootL;
    private Array<ErrorInfo> errors;

    Thread myT;
    Runnable myR = new Runnable() {
        /**
         * Function should return true if the runnable should stop
         *
         */
        @Override
        public void run() {


            errors = new Array<ErrorInfo>();


        }
    };


    private boolean handleError(short type,boolean critical, String file, String additionalInfo) {

        ErrorInfo erInf = new ErrorInfo();
        erInf.type = type;
        erInf.critical = critical;
        erInf.file = file;
        erInf.additionalInfo = additionalInfo;

        return critical;
    }



    /**
     * Inner tags
     */

    boolean waitngForAssetMgr;
    boolean gfxProcessing;


    public boolean load(ResponseListener rootL){

        if(bundle.indexP == null){
            rootL.onResponse(Codes.READY);
            return false;
        }

        this.rootL = rootL;

        myT = new Thread();
        myT.setName("MapLoadThread");
        myT.start();

        return true;
    }


    public void tick(){

        if(waitngForAssetMgr){
            if(manager.update()){
                waitngForAssetMgr = false;
                gfxProcessing = true;
            }
        }

    }


    public void setBundle(MapLoadBundle bundle) {
        this.bundle = bundle;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Array<ErrorInfo> getErrors() {
        if(errors == null) return new Array<ErrorInfo>();
        return errors;
    }
}
