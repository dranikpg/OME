package com.draniksoft.ome.support.execution_base;

import com.badlogic.gdx.assets.AssetManager;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ExecutionProvider {

    <T> Future<T> exec(Callable<T> c);

    void addShd(SyncTask t);

    //void removeShd(SyncTask t);

    AssetManager getAssets();

    // listener is optional
    void awaitAsset(String path, ResponseListener l);
}
