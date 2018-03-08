package com.draniksoft.ome.support.execution_base.ut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
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

    volatile boolean paused = true;

    public StepLoader(ExecutionProvider p, AssetManager mgr, ResponseListener l) {
	  this.p = p;
	  this.l = l;
	  this.mgr = mgr;
	  stepTask = new StepLoaderTask();

	  futures = new DelayedRemovalArray<Future>();
	  tasks = new DelayedRemovalArray<SyncTask>();

	  p.addShd(stepTask);
    }

    public void start() {

    }

    public void reset() {
	  paused = false;
    }

    public void dispose() {
	  stepTask.terminate();
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

		if (mgr.getQueuedAssets() != 0) return;

		paused = true;
		l.onResponse(ResponseCode.SUCCESSFUL);

		Gdx.app.debug(tag, "Step");
	  }
    }

}
