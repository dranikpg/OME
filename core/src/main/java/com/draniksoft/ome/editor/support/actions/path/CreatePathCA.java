package com.draniksoft.ome.editor.support.actions.path;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.res.path.b.PathRTDesc;
import com.draniksoft.ome.editor.res.path.b.PathSDesc;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import org.jetbrains.annotations.NotNull;

public class CreatePathCA implements Action {

    public int _e;

    @Override
    public void invoke(@NotNull World w) {

	  PathDescC c = w.getMapper(PathDescC.class).create(_e);
	  PathRunTimeC c2 = w.getMapper(PathRunTimeC.class).create(_e);

	  c.ar = new Array<PathSDesc>();
	  c2.p = new Array<PathRTDesc>();

	  w.getSystem(OmeEventSystem.class).dispatch(new CompositionChangeE(_e));
    }

    @Override
    public void undo(@NotNull World w) {

	  w.getMapper(PathDescC.class).remove(_e);
    }

    @Override
    public boolean isUndoable() {
	  return true;
    }

    @Override
    public boolean isCleaner() {
	  return false;
    }

    @NotNull
    @Override
    public String getSimpleConcl() {
	  return "Added path";
    }

    @Override
    public void destruct() {

    }
}
