package com.draniksoft.ome.editor.systems.support

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE
import net.mostlyoriginal.api.event.common.EventSystem

class WorkflowSys : BaseSystem() {


    // true :: showmode, false :: editmode
    var SHOW_M = false

    override fun initialize() {

        applyMode()

    }

    fun switchMode(v: Boolean) {

        if (v == SHOW_M) return

        SHOW_M = v

        applyMode()


    }

    private fun applyMode() {

        Gdx.app.debug(tag, "Applying for mode " + SHOW_M)

        world.getSystem(EventSystem::class.java).dispatch(ModeChangeE(SHOW_M))

    }

    override fun processSystem() {

    }

    companion object {

        private val tag = "WorkflowSys"
    }
}
