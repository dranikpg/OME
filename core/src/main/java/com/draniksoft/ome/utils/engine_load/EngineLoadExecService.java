package com.draniksoft.ome.utils.engine_load;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.assetcls.AssetGroupCollectionHelper;
import com.draniksoft.ome.support.execution_base.assetcls.DividedAssetCollector;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.FM;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class EngineLoadExecService implements ExecutionProvider {

    public ExecutorService service;

    DelayedRemovalArray<SyncTask> taskAr;
    AssetGroupCollectionHelper assh;

    public EngineLoadExecService(ExecutorService service) {
	  this.service = service;
	  taskAr = new DelayedRemovalArray<SyncTask>();
	  assh = new DividedAssetCollector(getAssets());
	  addShd(assh.asTask());
    }

    @Override
    public <T> Future<T> exec(Callable<T> c) {
	  return service.submit(c);
    }

    @Override
    public void addShd(SyncTask t) {
	  taskAr.add(t);
    }

    public void update() {
	  taskAr.begin();
	  for (SyncTask t : taskAr) {
		if ((FM.FRAME - t.getPhase()) % t.getFQ() == 0) {
		    if (t.active()) t.run();
		    else taskAr.removeValue(t, true);
		}
	  }
	  taskAr.end();
	  if (getAssets() != null) getAssets().update();
    }

    @Override
    public AssetManager getAssets() {
	  // bad idea ??
	  return EngineLoader.assm;
    }

    @Override
    public void awaitAsset(String path, ResponseListener l) {
	  assh.register(path, l);
    }

    public void dispose() {
	  assh.terminate();
    }
}
