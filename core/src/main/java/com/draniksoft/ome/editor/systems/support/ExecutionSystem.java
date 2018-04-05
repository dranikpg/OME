package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.assetcls.DividedAssetCollector;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.FM;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ExecutionSystem extends BaseSystem implements ExecutionProvider {

    private static String tag = "ExecutionSystem";

    @Wire
    AssetManager mgr;

    @Wire(name = "exs")
    ExecutorService s;

    DividedAssetCollector assetCollector;

    /*
 	Logic stuff
     */

    DelayedRemovalArray<SyncTask> tasks;

    @Override
    protected void initialize() {
	  tasks = new DelayedRemovalArray<SyncTask>(false, 16);
	  assetCollector = new DividedAssetCollector(mgr);
	  prepareBaseTasks();
    }

    @Override
    protected void processSystem() {
	  tasks.begin();
	  for (SyncTask t : tasks) {
		if ((FM.FRAME - t.getPhase()) % t.getFQ() == 0) {
		    if (t.active()) t.run();
		    else tasks.removeValue(t, true);
		}
	  }
	  tasks.end();
    }

    /*
 	Schedule
     */
    public void addShd(SyncTask t) {
	  Gdx.app.debug(tag, "Shd request " + t.getClass().getSimpleName());
	  tasks.add(t);
    }

    /*
    	Async exec
     */

    public <T> Future<T> exec(Callable<T> c) {
	  Gdx.app.debug(tag, "Async request " + c.getClass().getSimpleName());
	  return s.submit(c);
    }

    /*
    	Assets
     */

    @Override
    public AssetManager getAssets() {
	  return mgr;
    }

    @Override
    public void awaitAsset(String path, ResponseListener l) {
	  assetCollector.register(path, l);
    }



    /*
    	Util getters
     */

    public Iterator<SyncTask> getShdTasks() {
	  return tasks.iterator();
    }

    public int getShdTaskACount() {
	  return tasks.size;
    }


    /*
    		BASE TASKS
     */

    private void prepareBaseTasks() {

	  tasks.add(new AssMgrLoadShd(mgr, 5, 1));
	  tasks.add(new DividedAssetCollector(mgr, 5, 2));

    }

    private class AssMgrLoadShd extends SimpleSyncTask {
	  AssetManager mgr;

	  protected AssMgrLoadShd(AssetManager mgr) {
		this(mgr, 5, 0);
	  }

	  protected AssMgrLoadShd(AssetManager mgr, int fq, int ph) {
		super(fq, ph);
		this.mgr = mgr;
	  }

	  @Override
	  public void run() {
		if (mgr.getQueuedAssets() != 0) {
		    System.out.println("mgrpct " + mgr.getProgress());
		    mgr.update();
		}
	  }
    }

    @Override
    protected void dispose() {
	  // TODO bad fix for intelligent loader usage in dispose
	  s.shutdown();
    }
}

