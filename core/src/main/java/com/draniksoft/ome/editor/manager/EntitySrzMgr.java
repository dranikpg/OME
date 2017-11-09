package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.draniksoft.ome.editor.load.LoadSaveManager;

public class EntitySrzMgr extends Manager implements LoadSaveManager {

    private static final String tag = "EntitySrzManager";

    public static class ComponentNames {

        public static final String MapObject = "MO";
        public static final String TimedC = "TMC";
        public static final String TimedMoveC = "TMVC";

    }

}
