package com.draniksoft.ome.editor.systems.file_mgmnt

import com.artemis.BaseSystem
import com.artemis.annotations.Wire
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.draniksoft.ome.editor.load.MapLoadBundle
import com.draniksoft.ome.editor.load.ProjectLoader
import com.draniksoft.ome.editor.load.ProjectSaver
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE
import com.draniksoft.ome.editor.systems.support.EditorSystem
import com.draniksoft.ome.mgmnt_base.base.AppDO
import net.mostlyoriginal.api.event.common.EventSystem

class ProjectLoadSystem : BaseSystem() {

    @Wire
    var bundle: MapLoadBundle? = null

    @Wire
    internal var assM: AssetManager? = null


    internal var state = 0
    internal lateinit var saver: ProjectSaver
    internal lateinit var loader: ProjectLoader


    override fun initialize() {

        saver = ProjectSaver()
        loader = ProjectLoader()


        //load();


    }

    override fun processSystem() {

        if (state == STATE_SAVING) saver.update()
        if (state == STATE_LOADING) loader.update()

    }

    private fun prepareLoad() {

        var e = SelectionChangeE()
        e.old = world.getSystem(EditorSystem::class.java).sel
        e.n = -1

        world.getSystem(EventSystem::class.java).dispatch(e)

    }

    private fun load(bundle: MapLoadBundle) {

        Gdx.app.debug(tag, "Loading")

        //logLoad(bundle.inDir)

        prepareLoad()

        state = STATE_LOADING

        loader.setW(world)
        loader.start(bundle) {
            Gdx.app.debug(tag, "Load ready")

            state = STATE_IDLE

            loader.dispose()
        }


    }


    private fun save(bundle: MapLoadBundle) {

        Gdx.app.debug(tag, "Saving")

        //logLoad(bundle.outDir)

        state = STATE_SAVING

        saver.setWorld(world)
        saver.start(bundle) {
            Gdx.app.debug(tag, "Save ready")

            state = STATE_IDLE

            saver.dispose()
        }

    }


    private fun logLoad(p: String) {

        AppDO.I.LH().reportOpening(p)

    }

    fun load() {
        if (bundle != null) load(bundle!!)
    }

    fun save() {
        if (bundle != null) save(bundle!!)
    }

    companion object {

        var tag = "ProjectLoadSystem"

        val STATE_IDLE = 1
        val STATE_LOADING = 2
        val STATE_SAVING = 3
    }

}
