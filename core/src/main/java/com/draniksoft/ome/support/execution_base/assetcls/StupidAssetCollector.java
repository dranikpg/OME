package com.draniksoft.ome.support.execution_base.assetcls;

import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class StupidAssetCollector extends SimpleSyncTask implements AssetGroupCollectionHelper {

    AssetManager mgr;

    public StupidAssetCollector(AssetManager mgr, int fq, int ph) {
	  super(fq, ph);
	  this.mgr = mgr;
    }

    public StupidAssetCollector(AssetManager mgr) {
	  this(mgr, 5, 0);
    }

    @Override
    public void run() {

    }

    @Override
    public void register(String path, ResponseListener l) {

    }

    @Override
    public boolean supportsSingleResponse() {
	  return false;
    }

    @Override
    public boolean isReady() {
	  return mgr.getQueuedAssets() == 0;
    }

    @Override
    public SyncTask asTask() {
	  return this;
    }
}

