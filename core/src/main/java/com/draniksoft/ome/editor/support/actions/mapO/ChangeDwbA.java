package com.draniksoft.ome.editor.support.actions.mapO;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.base_gfx.drawable.Drawable;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.EntityDataChangeE;
import com.draniksoft.ome.utils.FUtills;
import org.jetbrains.annotations.NotNull;

public class ChangeDwbA implements Action {

    private static final String tag = "ChangeDwbA";

    public int _e;

    public String dwid;
    String old;

    @Override
    public void invoke(@NotNull World w) {

        changeD(w, dwid);
    }

    private void changeD(World w, String d) {

	  Drawable dwb = FUtills.fetchDrawable(d);

	  if (dwb == null) {
		Gdx.app.error(tag, "DWB IS NULL");
	  }

        MObjectC c = w.getMapper(MObjectC.class).get(_e);
        old = c.dwbID;
        c.dwbID = d;

        DrawableC dc = w.getMapper(DrawableC.class).get(_e);
	  dc.d = dwb;

	  w.getSystem(OmeEventSystem.class).dispatch(new EntityDataChangeE.DwbChangeE(_e));

    }

    @Override
    public void undo(World w) {

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
