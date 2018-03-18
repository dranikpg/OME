package com.draniksoft.ome.support.execution_base.assetcls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.Pair;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class DividedAssetCollector extends SimpleSyncTask implements AssetGroupCollectionHelper {

    private static final String tag = "AssetCollectorTask";

    DelayedRemovalArray<Pair<String, ResponseListener>> ar;
    AssetManager mgr;

    public static boolean LOG = true;

    public DividedAssetCollector(AssetManager mgr) {
	  this(mgr, 5, 0);
    }

    public DividedAssetCollector(AssetManager mgr, int fq, int ph) {
	  super(fq, ph);
	  ar = new DelayedRemovalArray<Pair<String, ResponseListener>>();
	  this.mgr = mgr;
    }

    public void register(String path, ResponseListener l) {
	  ar.add(Pair.P(path, l));
    }

    @Override
    public boolean supportsSingleResponse() {
	  return true;
    }

    @Override
    public boolean isReady() {
	  return ar.size == 0;
    }

    @Override
    public SyncTask asTask() {
	  return this;
    }

    @Override
    public void run() {
	  if (ar.size != 0) {
		ar.begin();
		for (Pair<String, ResponseListener> pp : ar) {
		    if (mgr.isLoaded(pp.K())) {
			  Gdx.app.debug(tag, " Loaded " + pp.K());
			  if (pp.V() != null) pp.V().onResponse(ResponseCode.SUCCESSFUL);
			  ar.removeValue(pp, true);
		    }
		}
		ar.end();
	  }
    }
}