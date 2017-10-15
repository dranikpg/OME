package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import net.mostlyoriginal.api.event.common.EventSystem;

public class ChangeDwbA implements Action {

    public int _e;

    public String dwid;
    String old;

    @Override
    public void _do(World w) {

        changeD(w, dwid);
    }

    private void changeD(World w, String d) {

        TextureRegion tr = w.getSystem(DrawableMgr.class).getRegion(d);
        if (d == null) return;

        MObjectC c = w.getMapper(MObjectC.class).get(_e);
        old = c.dwbID;
        c.dwbID = d;

        DrawableC dc = w.getMapper(DrawableC.class).get(_e);
        dc.d = new TextureRegionDrawable(tr);

        w.getSystem(EventSystem.class).dispatch(new EntityDataChangeE.DwbChangeE(_e));

    }

    @Override
    public void _undo(World w) {

        changeD(w, old);

    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean isCleaner() {
        return false;
    }

    @Override
    public String getSimpleConcl() {
        return "Changed drawable";
    }

    @Override
    public void destruct() {

        old = null;

    }
}
