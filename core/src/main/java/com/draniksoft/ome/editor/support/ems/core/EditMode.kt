package com.draniksoft.ome.editor.support.ems.core

import com.artemis.World

interface EditMode {

    fun attached(_w: World)

    fun update()

    fun detached()

    fun ID(): Int

}
