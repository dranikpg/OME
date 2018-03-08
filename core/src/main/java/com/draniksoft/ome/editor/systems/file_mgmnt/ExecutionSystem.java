package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.support.execution_base.sync.SimpleSyncTask;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.GUtils;

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

    /*
 	Logic stuff
     */

    DelayedRemovalArray<SyncTask> tasks;

    @Override
    protected void initialize() {
	  tasks = new DelayedRemovalArray<SyncTask>(false, 16);
	  prepareBaseTasks();
    }

    @Override
    protected void processSystem() {
	  tasks.begin();
	  for (SyncTask t : tasks) {
		if ((GUtils.FRAME - t.getPhase()) % t.getFQ() == 0) {
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
	  tasks.add(t);
    }

    public void removeShedTask(SyncTask t) {
	  tasks.removeValue(t, true);
    }

    /*
    	Async exec
     */

    public <T> Future<T> exec(Callable<T> c) {
	  return s.submit(c);
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
	  tasks.add(new AssMgrLoadShd(mgr));
    }

    private class AssMgrLoadShd extends SimpleSyncTask {
	  AssetManager mgr;

	  protected AssMgrLoadShd(AssetManager mgr) {
		super(5);
		this.mgr = mgr;
	  }

	  @Override
	  public void run() {
		if (mgr.getQueuedAssets() != 0) mgr.update();
	  }
    }

}

