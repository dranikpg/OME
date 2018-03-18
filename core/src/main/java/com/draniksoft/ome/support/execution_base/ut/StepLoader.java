package com.draniksoft.ome.support.execution_base.ut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.assetcls.AssetGroupCollectionHelper;
import com.draniksoft.ome.support.execution_base.assetcls.DividedAssetCollector;
import com.draniksoft.ome.support.execution_base.assetcls.MessedAssetCollector;
import com.draniksoft.ome.support.execution_base.assetcls.StupidAssetCollector;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class StepLoader implements ExecutionProvider {

    private static final String tag = "StepLoader";

    ExecutionProvider p;
    ResponseListener l;

    AssetManager mgr;

    DelayedRemovalArray<Future> futures;
    DelayedRemovalArray<SyncTask> tasks;

    StepLoaderTask stepTask;

    AssetGroupCollectionHelper assetHelper;

    volatile boolean paused = true;

    int completiton = 0;
    int disableCC = -1;

    public StepLoader(ExecutionProvider p) {
	  this(p, null);
    }

    public StepLoader(ExecutionProvider p, ResponseListener l) {
	  this(p, p.getAssets(), AssetGroupCollectionHelper.CollectionStrategy.DIVIED, l);
    }

    public StepLoader(ExecutionProvider p, AssetManager mgr, short assetStratey, ResponseListener l) {
	  this.p = p;
	  this.l = l;
	  this.mgr = mgr;
	  stepTask = new StepLoaderTask();

	  futures = new DelayedRemovalArray<Future>();
	  tasks = new DelayedRemovalArray<SyncTask>();

	  switch (assetStratey) {
		case AssetGroupCollectionHelper.CollectionStrategy.DIVIED:
		    assetHelper = new DividedAssetCollector(mgr);
		    break;
		case AssetGroupCollectionHelper.CollectionStrategy.MESSED:
		    assetHelper = new MessedAssetCollector(mgr);
		    break;
		case AssetGroupCollectionHelper.CollectionStrategy.STUPID:
		    assetHelper = new StupidAssetCollector(mgr);
		    break;
		case AssetGroupCollectionHelper.CollectionStrategy.PARENT:
		    assetHelper = null;
		    break;
	  }

	  Gdx.app.debug(tag, "Asset collection " + assetStratey);

	  p.addShd(stepTask);
	  if (assetHelper != null) p.addShd(assetHelper.asTask());

    }

    public void setDisableCC(int disableCC) {
	  this.disableCC = disableCC;
    }

    public void start() {

    }

    public void reset() {
	  // arrays are clean anyway
	  paused = false;
    }

    public void dispose() {
	  Gdx.app.debug(tag, "Disposing");
	  stepTask.terminate();
	  if (assetHelper != null) assetHelper.terminate();
    }

    @Override
    public <T> Future<T> exec(Callable<T> c) {
	  Future f = p.exec(c);
	  futures.add(f);
	  return f;
    }

    @Override
    public void addShd(SyncTask t) {
	  tasks.add(t);
	  p.addShd(t);
    }

    @Override
    public AssetManager getAssets() {
	  return p.getAssets();
    }

    /*
    	Does not use the super to system to keep track itself
     */
    @Override
    public void awaitAsset(String path, ResponseListener l) {
	  if (assetHelper == null) {
		p.awaitAsset(path, l);
		return;
	  }

	  assetHelper.register(path, l);
	  if (l != null && !assetHelper.supportsSingleResponse()) {
		l.onResponse(ResponseCode.UNSUPPORTED);
	  }
    }


    public void setListener(ResponseListener l) {
	  this.l = l;
    }

    private class StepLoaderTask extends SimpleSyncTask {
	  public StepLoaderTask() {
		super(10, 1);
	  }

	  @Override
	  public void run() {
		if (paused) return;

		System.out.print(">");

		futures.begin();
		for (Future f : futures) {
		    if (!f.isDone()) {
			  futures.end();
			  return;
		    } else futures.removeValue(f, true);
		}
		futures.end();

		tasks.begin();
		for (SyncTask t : tasks) {
		    if (t.active()) {
			  tasks.end();
			  return;
		    } else tasks.removeValue(t, true);
		}
		tasks.end();

		if (!assetHelper.isReady()) return;

		paused = true;

		Gdx.app.debug(tag, "Step");

		if (l != null) l.onResponse(ResponseCode.SUCCESSFUL);

		completiton++;

		if (completiton == disableCC) {
		    dispose();
		}

	  }
    }

}
