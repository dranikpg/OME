package com.draniksoft.ome.editor.systems.support

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import com.draniksoft.ome.editor.support.input.InputController
import com.draniksoft.ome.editor.support.input.SelectIC

class InputSys : BaseSystem(), InputProcessor {

    @Wire
    internal var phys: World? = null

    @Wire(name = "game_vp")
    internal var gameVP: Viewport? = null

    @Wire
    internal var mx: InputMultiplexer? = null

    // aka the current tool
    internal var mainIC: InputController? = null

    // aka the behavior
    internal var defIC: InputController? = null

    override fun initialize() {

        mx!!.addProcessor(this)

        setDefIC(SelectIC())

    }

    override fun processSystem() {

        if (mainIC != null) {
            mainIC!!.update()
        }

        if (defIC != null) {
            defIC!!.update()
        }

    }

    fun clearMainIC() {
        setMainIC(null)
    }

    fun setMainIC(mainIC: InputController?) {

        if (this.mainIC != null) {
            this.mainIC!!.destruct()
        }

        this.mainIC = mainIC

        mainIC?.init(world)
    }

    fun setDefIC(defIC: InputController?) {

        if (this.defIC != null) {
            this.defIC!!.destruct()
        }

        this.defIC = defIC

        defIC?.init(world)
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        if ((mainIC == null || !mainIC!!.keyUp(keycode)) && defIC != null) {
            defIC!!.keyUp(keycode)
        }

        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        if ((mainIC == null || !mainIC!!.touchDown(screenX, screenY, pointer, button)) && defIC != null) {
            defIC!!.touchDown(screenX, screenY, pointer, button)
        }

        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {

        if ((mainIC == null || mainIC!!.touchUp(screenX, screenY, pointer, button)) && defIC != null) {
            defIC!!.touchUp(screenX, screenY, pointer, button)
        }

        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {

        if ((mainIC == null || mainIC!!.touchDragged(screenX, screenY, pointer)) && defIC != null) {
            defIC!!.touchDragged(screenX, screenY, pointer)
        }

        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {

        if ((mainIC == null || mainIC!!.mouseMoved(screenX, screenY)) && defIC != null) {
            defIC!!.mouseMoved(screenX, screenY)
        }

        return false
    }

    override fun scrolled(amount: Int): Boolean {

        if (mainIC != null) {
            mainIC!!.scrolled(amount)
        }

        return false
    }

    fun getMainIC(): InputController? {
        return mainIC
    }

    fun getDefIC(): InputController? {
        return defIC
    }

    companion object {

        internal val tag = "InputSys"
    }
}
