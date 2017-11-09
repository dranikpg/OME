package com.draniksoft.ome.editor.support.ems.timed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.support.actions.timed.move.ChangeTimedMovDataA;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.ems.core.SimpleEditMode;
import com.draniksoft.ome.editor.support.input.EditTimedMovIC;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.render.EditTimedMovsR;
import com.draniksoft.ome.editor.support.render.core.OverlayPlaces;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.EditorSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.utils.struct.Pair;

public class EditTimedMovsEM extends SimpleEditMode {

    private static final String tag = "EditTimedMovsEM";

    EditTimedMovsR r;
    EditTimedMovIC ic;

    InputController oldDIC;

    public boolean newM = false;
    public boolean dragM = false;
    public float px;
    public float py;

    public int selI;

    Array<MoveDesc> backup;
    Array<MoveDesc> ar;

    @Override
    protected void on_attached() {

        r = new EditTimedMovsR();
        ic = new EditTimedMovIC();


        r.setEm(this);
        ic.setEm(this);


        backup = _w.getMapper(TimedMoveC.class).get(_w.getSystem(EditorSystem.class).sel).a;
        ar = new Array<MoveDesc>(backup);

        ic.setArr(ar);
        r.setArr(ar);



        _w.getSystem(OverlayRenderSys.class).removeRdrByPlace(new int[]{}, new int[]{OverlayPlaces.ENTITY_MAIN_BODY, OverlayPlaces.PATH});
        _w.getSystem(OverlayRenderSys.class).addRdr(r);

        _w.getSystem(InputSys.class).setMainIC(ic);
        oldDIC = _w.getSystem(InputSys.class).getDefIC();
        _w.getSystem(InputSys.class).setDefIC(null);


    }

    private void handleESC() {

        if (dragM || newM) {
            dragM = false;
            newM = false;
        } else {

            detachSelf();
        }
    }


    public void handleKey(int k) {

        if (k == Input.Keys.N) {

            setSelI(-1);
            startNM();

        } else if (k == Input.Keys.ESCAPE) {

            handleESC();

        } else if (k == Input.Keys.SPACE) {

            save();

            detachSelf();

        }

    }

    private void save() {


        ChangeTimedMovDataA _a = new ChangeTimedMovDataA();
        _a.ar = ar;
        _a._e = _w.getSystem(EditorSystem.class).sel;

        _w.getSystem(ActionSystem.class).exec(_a);

    }

    public void setSelI(int i) {


        Gdx.app.debug(tag, "New Sel " + i);
        selI = i;
    }

    public void startNM() {

        newM = true;

    }

    public void strartDrag() {

        dragM = true;

    }

    public void addDescOnLP() {

        MoveDesc d = new MoveDesc();
        d.end_x = (int) px;
        d.end_y = (int) py;

        if (ar.size > 0) {
            d.time_s = ar.get(ar.size - 1).time_e;
            d.time_e = ar.get(ar.size - 1).time_e;
        }

        ar.add(d);

        newM = false;
    }


    @Override
    public void update() {

    }

    private void detachSelf() {

        _w.getSystem(EditorSystem.class).detachEditMode();

    }


    @Override
    public void detached() {

        returnUIState();

        _w.getSystem(OverlayRenderSys.class).removeRdr(r);
        _w.getSystem(OverlayRenderSys.class).restoreDef();

        _w.getSystem(InputSys.class).clearMainIC();
        _w.getSystem(InputSys.class).setDefIC(oldDIC);

    }

    public Pair<Float, Float> getEPos() {

        return _w.getSystem(PhysicsSys.class).getPos(_w.getSystem(EditorSystem.class).sel);

    }
}
