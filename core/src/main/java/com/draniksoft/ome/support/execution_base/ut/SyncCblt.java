package com.draniksoft.ome.support.execution_base.ut;

import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.support.load.interfaces.IGLRunnable;

public class SyncCblt implements SyncTask {

    IGLRunnable r;

    boolean active = true;

    public SyncCblt(IGLRunnable r) {
	  this.r = r;
    }

    @Override
    public int getFQ() {
	  return 1;
    }

    @Override
    public int getPhase() {
	  return 0;
    }

    @Override
    public void run() {
	  if (r.run() != IGLRunnable.RUNNING) active = false;
    }

    @Override
    public boolean active() {
	  return active;
    }

    public static SyncCblt WRAP(IGLRunnable r) {
	  return new SyncCblt(r);
    }
}
