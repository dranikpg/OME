package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.utils.FUtills;
import net.mostlyoriginal.api.event.common.EventSystem;

public class ChangeDwbA implements Action {

    private static final String tag = "ChangeDwbA";

    public int _e;

    public String dwid;
    String old;

    @Override
    public void _do(World w) {

        changeD(w, dwid);
    }

    private void changeD(World w, String d) {

	  Drawable dwb = FUtills.fetchDrawable(d);

	  if (dwb == null) {
		Gdx.app.error(tag, "DWB IS NULL");
	  }

        MObjectC c = w.getMapper(MObjectC.class).get(_e);
	  old = c.dwbData;
	  c.dwbData = d;

        DrawableC dc = w.getMapper(DrawableC.class).get(_e);
	  dc.d = dwb;

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
