package com.draniksoft.ome.editor.systems.support.flows

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE
import com.draniksoft.ome.utils.ESCUtils
import net.mostlyoriginal.api.event.common.Subscribe

class WorkflowSys : BaseSystem() {


    // true :: showmode, false :: editmode
    var SHOW_M = false

    override fun initialize() {


    }

    var ev: ModeChangeE.ShowRequestEvent? = null

    override fun processSystem() {

        if (ev != null) {
            if (ev!!.c <= 0) {
                switchToShow()
            }
        }

    }


    @Subscribe(priority = ESCUtils.EVENT_LAST)
    fun gotRequest(e: ModeChangeE.ShowRequestEvent) {
        Gdx.app.debug(tag, "Got request back ")
        if (e.c > 0) {
            ev = e
        } else {
            switchToShow()
        }
    }

    private fun switchToShow() {
        SHOW_M = true
        ev = null
        world.getSystem(OmeEventSystem::class.java).dispatch(ModeChangeE.ShowEnterEvent())
    }


    fun requestShow() {
        if (ev != null) return
        Gdx.app.debug(tag, "Requesting show")
        ev = ModeChangeE.ShowRequestEvent()
        world.getSystem(OmeEventSystem::class.java).dispatch(ev)
    }

    fun requestEdit() {
        SHOW_M = false
        world.getSystem(OmeEventSystem::class.java).dispatch(ModeChangeE.ShowQuitEvent())
    }


    companion object {

        private val tag = "WorkflowSys"
    }
}
