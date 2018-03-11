package com.draniksoft.ome.support.execution_base.assetcls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class MessedAssetCollector extends SimpleSyncTask implements AssetGroupCollectionHelper {

    DelayedRemovalArray<String> ar;
    AssetManager mgr;

    public MessedAssetCollector(AssetManager mgr, int fq, int ph) {
	  super(fq);
	  ar = new DelayedRemovalArray<String>(false, 16);
    }

    public MessedAssetCollector(AssetManager mgr) {
	  this(mgr, 5, 0);
    }

    public void add(String p) {
	  ar.add(p);
    }

    public void add(String... st) {
	  ar.addAll(st);
    }

    @Override
    public void run() {
        /* UNREADY !! */
	  Gdx.app.exit();
    }

    @Override
    public void register(String path, ResponseListener l) {
	  ar.add(path);
    }

    @Override
    public boolean supportsSingleResponse() {
	  return false;
    }

    @Override
    public boolean isReady() {
	  return ar.size == 0;
    }

    @Override
    public SyncTask asTask() {
	  return null;
    }
}