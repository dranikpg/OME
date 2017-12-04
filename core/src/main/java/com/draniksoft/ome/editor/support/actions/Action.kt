package com.draniksoft.ome.editor.support.actions

import com.artemis.World

/**
 * Actions are object that trigger complex work, they shouldn't be used for inner workings, instead only for things an user can trigger
 */

interface Action {


    operator fun invoke(w: World)

    fun undo(w: World)

    //
    val isUndoable: Boolean

    // A cleaner cleans the story, hence it'time_s supposed to be an action like loading a map
    val isCleaner: Boolean

    val simpleConcl: String

    // Called when pool is freed and all action data should be deleted
    fun destruct()
}
