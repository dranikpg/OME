package com.draniksoft.ome.editor.support.workflow;

import com.artemis.World;
import com.badlogic.gdx.math.Vector2;
import com.draniksoft.ome.editor.support.actions.loc.CreateLocationA;
import com.draniksoft.ome.editor.support.input.NewLocIC;
import com.draniksoft.ome.editor.support.render.NewLocRenderer;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.WorkflowSystem;

public class NewLocEditMode implements EditMode {


    World _w;

    NewLocIC newLIC;
    NewLocRenderer newRdr;

    @Override
    public void attached(World _w) {
        this._w = _w;

        newLIC = new NewLocIC();
        newRdr = new NewLocRenderer();

        newLIC.setOwnerMode(this);
        newLIC.setRdr(newRdr);

        _w.getSystem(InputSys.class).setMainIC(newLIC);
        _w.getSystem(OverlayRenderSys.class).addRdr(newRdr);

    }

    @Override
    public void run() {

    }

    @Override
    public void detached() {

        _w.getSystem(InputSys.class).setMainIC(null);
        _w.getSystem(OverlayRenderSys.class).removeRdr(newRdr);

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

        _w.getSystem(ActionSystem.class).exec(new CreateLocationA((int) tmp.x, (int) tmp.y, 50, 50));

        _w.getSystem(WorkflowSystem.class).detachEditMode();
    }


    // called when child is interrupted/detached
    public void inputStopped() {

        /*
        GOD DAMN IT I AM THE RULER AND WILL FORCE U MY INPUT CONTROLLER !!
         */

        //_w.getSystem(InputSys.class).setMainIC(newLIC);

        newLIC.ignoreDestruct = true;

        _w.getSystem(WorkflowSystem.class).detachEditMode();


    }
}
