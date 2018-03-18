package com.draniksoft.ome.editor.load;

import com.draniksoft.ome.editor.manager.*;

public interface LoadSaveManager {

    Class[] MANAGER = new Class[]{
		ProjectMgr.class,
		MapMgr.class,
		TimeMgr.class,
		ExtensionManager.class,
		SerializationManager.class,
    };

    void save(ProjectSaver s);

    void load(ProjectLoader ld);


}
