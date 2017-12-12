package com.draniksoft.ome.editor.support.ems;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.editor.support.input.MoveMOIC;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import net.mostlyoriginal.api.event.common.EventSystem;

public class MoveMOEM implements EditMode {

    public final String tag = "MoveMOEM";

    MoveMOIC ic;

    World _w;
    int e = -1;

    boolean easyQ = false;

    @Override
    public void attached(World _w) {
        this._w = _w;

        ic = new MoveMOIC();


        e = _w.getSystem(EditorSystem.class).sel;

        if (e < 0) {
            Gdx.app.debug(tag, "Selection is null -> detaching");
            easyQ = true;
            _w.getSystem(EditorSystem.class).detachEditMode();
            return;
        }


        ic.setE(e);
        ic.setEm(this);


        _w.getSystem(OverlayRenderSys.class).removeRdrByPlace(new int[]{}, new int[]{OverlayPlaces.ENTITY_MAIN_BODY});
        _w.getSystem(InputSys.class).setMainIC(ic);

        _w.getSystem(EventSystem.class).registerEvents(this);

        if (e < 0) {
            _w.getSystem(EditorSystem.class).detachEditMode();
        }


    }

    @Override
    public void update() {


    }

    public void keyPressed(int t) {


        if (t == 0) selfDestroy();

        if (t == 1) {
            save();
        }

    }

    private void save() {

        Gdx.app.debug(tag, "Committing changes");



        _w.getSystem(EditorSystem.class).detachEditMode();

    }

    private void selfDestroy() {

        ic.setReactOnKill(false);
        _w.getSystem(EditorSystem.class).detachEditMode();

    }

    @Override
    public void detached() {

        if (easyQ) return;

        _w.getSystem(InputSys.class).clearMainIC();

    }

    /*
    public void selChanged(SelectionChangeE time_e){
        selfDestroy();
    }
    */
    public void childKilled() {

        ic.setReactOnKill(true);
        _w.getSystem(EditorSystem.class).detachEditMode();

    }


}
