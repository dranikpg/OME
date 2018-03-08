package com.draniksoft.ome.support.execution_base.assetcls;

import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

public class StupidAssetCls extends SimpleSyncTask {
    AssetManager mgr;
    ResponseListener l;

    boolean paused = false;

    public StupidAssetCls(ResponseListener l, AssetManager mgr, int fq) {
	  super(fq);
	  this.l = l;
	  this.mgr = mgr;
    }

    public StupidAssetCls(ResponseListener l, AssetManager mgr) {
	  this(l, mgr, 5);
    }

    @Override
    public void run() {
	  if (!paused && mgr.getQueuedAssets() == 0) {
		paused = true;
		if (l != null) l.onResponse(ResponseCode.SUCCESSFUL);
	  }
    }

    public void reset() {
	  paused = false;
    }

    public boolean isPaused() {
	  return paused;
    }
}
