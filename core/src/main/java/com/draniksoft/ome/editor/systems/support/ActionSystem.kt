package com.draniksoft.ome.editor.systems.support

import com.artemis.BaseSystem
import com.badlogic.gdx.Gdx
import com.draniksoft.ome.editor.support.actions.Action
import com.draniksoft.ome.editor.support.event.ActionHistoryChangeE
import net.mostlyoriginal.api.event.common.EventSystem
import java.util.*

class ActionSystem : BaseSystem() {

    // The history
    lateinit var stack: LinkedList<Action>
        internal set
    var maxStackSize = 20

    internal var eventSys: EventSystem? = null

    override fun initialize() {
        stack = LinkedList<Action>()
    }

    override fun processSystem() {

    }

    fun exec(a: Action) {

        Gdx.app.debug(tag, "Executing " + a.javaClass.simpleName)

        a(world)


        stack.add(a)

        if (a.isCleaner) {
            for (ia in stack) {
                ia.destruct()
            }
            stack.clear()
            return
        }

        if (stack.size > maxStackSize) {
            Gdx.app.debug(tag, "Action stack overflow")
            stack[0].destruct()
            stack.removeAt(0)
        }

        eventSys!!.dispatch(ActionHistoryChangeE.ActionDoneEvent())

    }

    fun undo() {

        // index of last element
        var i = stack.size - 1

        while (i >= 0) {

            val a = stack[i]

            if (a.isUndoable) {

                Gdx.app.debug(tag, "Undoing " + a.javaClass.simpleName)

                a.undo(world)
                stack.removeLast()


                eventSys!!.dispatch(ActionHistoryChangeE.ActionUndoEvent())

                return
            }

            i--
        }


    }

    companion object {

        val tag = "ActionSystem"
    }
}
