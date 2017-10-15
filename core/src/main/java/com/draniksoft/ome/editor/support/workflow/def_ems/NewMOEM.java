package com.draniksoft.ome.editor.support.workflow.def_ems;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.support.actions.mapO.CreateNewMOA;
import com.draniksoft.ome.editor.support.input.NewMOIC;
import com.draniksoft.ome.editor.support.input.SelectIC;
import com.draniksoft.ome.editor.support.render.NewMORenderer;
import com.draniksoft.ome.editor.support.workflow.EditMode;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.utils.ESCUtils;

public class NewMOEM implements EditMode {


    World _w;

    NewMOIC newLIC;
    NewMORenderer newRdr;

    @Override
    public void attached(World _w) {
        this._w = _w;

        newLIC = new NewMOIC();
        newRdr = new NewMORenderer();

        newLIC.setOwnerMode(this);
        newLIC.setRdr(newRdr);

        _w.getSystem(InputSys.class).setDefIC(null);
        _w.getSystem(InputSys.class).setMainIC(newLIC);
        _w.getSystem(OverlayRenderSys.class).addRdr(newRdr);

        ESCUtils.clearSelected(_w);

    }

    @Override
    public void update() {

    }

    @Override
    public void detached() {

        _w.getSystem(InputSys.class).setMainIC(null);
        _w.getSystem(OverlayRenderSys.class).removeRdr(newRdr);

        _w.getSystem(InputSys.class).setDefIC(new SelectIC());

    }

    @Override
    public void stopped() {

        _w.getSystem(InputSys.class).setMainIC(null);
        _w.getSystem(OverlayRenderSys.class).removeRdr(newRdr);

    }

    @Override
    public void resumed() {

        _w.getSystem(InputSys.class).setMainIC(newLIC);
        _w.getSystem(OverlayRenderSys.class).addRdr(newRdr);

    }

    public void createLoc(Vector2 tmp) {
        CreateNewMOA a = new CreateNewMOA(tmp.x, tmp.y, 50, 50, "i_casB@mapTile@100");

        _w.getSystem(ActionSystem.class).exec(a);

        _w.getSystem(EditorSystem.class).detachEditMode();
    }


    // called when child is interrupted/detached
    public void inputStopped() {

        /*
        GOD DAMN IT I AM THE RULER AND WILL FORCE U MY INPUT CONTROLLER !!
         */

        //_w.getSystem(InputSys.class).setMainIC(newLIC);

        newLIC.ignoreDestruct = true;

        _w.getSystem(EditorSystem.class).detachEditMode();


    }
}
