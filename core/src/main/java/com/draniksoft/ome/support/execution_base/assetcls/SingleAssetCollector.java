package com.draniksoft.ome.support.execution_base.assetcls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;

public class SingleAssetCollector extends SimpleSyncTask {

    DelayedRemovalArray<String> ar;
    AssetManager mgr;

    public SingleAssetCollector(AssetManager mgr, int fq) {
	  super(fq);
	  ar = new DelayedRemovalArray<String>();
    }

    public SingleAssetCollector(AssetManager mgr) {
	  this(mgr, 5);
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
}