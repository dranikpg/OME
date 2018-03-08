package com.draniksoft.ome.support.execution_base.assetcls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.Pair;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class DiviedAssetCollector extends SimpleSyncTask {

    private static final String tag = "AssetCollectorTask";

    DelayedRemovalArray<Pair<String, ResponseListener>> ar;
    AssetManager mgr;

    public static boolean LOG = true;

    public DiviedAssetCollector(AssetManager mgr) {
	  this(mgr, 5);
    }

    public DiviedAssetCollector(AssetManager mgr, int fq) {
	  super(fq);
	  ar = new DelayedRemovalArray<Pair<String, ResponseListener>>();
    }

    public void register(String path, ResponseListener l) {
	  ar.add(Pair.P(path, l));
    }

    @Override
    public void run() {
	  if (mgr.getQueuedAssets() != 0) {
		ar.begin();
		for (Pair<String, ResponseListener> pp : ar) {
		    if (mgr.isLoaded(pp.K())) {
			  pp.V().onResponse(ResponseCode.SUCCESSFUL);
			  ar.removeValue(pp, true);
			  if (LOG) Gdx.app.debug(tag, "Loaded " + pp.K());
		    }
		}
		ar.end();
	  }
    }
}
