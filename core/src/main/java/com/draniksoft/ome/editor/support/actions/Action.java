package com.draniksoft.ome.editor.support.actions;

import com.artemis.World;

/**
 * Actions are object that trigger complex work, they shouldn't be used for inner working, instead only for things an user can trigger
 */

public interface Action {


    void _do(World w);

    void _undo(World w);

    //
    boolean isUndoable();

    // A cleaner cleans the story, hence it's supposed to be an action like loading a map
    boolean isCleaner();
}
