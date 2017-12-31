package com.draniksoft.ome.editor.systems.file_mgmnt;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.assets.AssetManager;

public class AssetLScheduleSys extends BaseSystem {

    @Wire
    AssetManager mgr;


    @Override
    protected void processSystem() {

	  if (mgr.getQueuedAssets() != 0)
		mgr.update();

    }
}
