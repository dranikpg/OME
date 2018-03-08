package com.draniksoft.ome.support.execution_base;

import com.draniksoft.ome.support.execution_base.sync.SyncTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ExecutionProvider {

    <T> Future<T> exec(Callable<T> c);

    void addShd(SyncTask t);

}
