package com.draniksoft.ome.support.execution_base.ut;

import com.draniksoft.ome.support.load.interfaces.IRunnable;

import java.util.concurrent.Callable;

public class CblT implements Callable {

    IRunnable r;

    public CblT(IRunnable r) {
	  this.r = r;
    }

    @Override
    public Object call() throws Exception {
	  r.run(null);
	  return null;
    }

    public static CblT WRAP(IRunnable r) {
	  return new CblT(r);
    }

}
