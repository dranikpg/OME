package com.draniksoft.ome.support.execution_base.assetcls;

import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.utils.struct.ResponseListener;

public interface AssetGroupCollectionHelper {

    class CollectionStrategy {
	  public static final short DIVIED = 1;
	  public static final short MESSED = 2;
	  public static final short STUPID = 3;

	  public static final short PARENT = 4;

    }

    void register(String path, ResponseListener l);

    boolean supportsSingleResponse();

    boolean isReady();

    void terminate();

    SyncTask asTask();

}
