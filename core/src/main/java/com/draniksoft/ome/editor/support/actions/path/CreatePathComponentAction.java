package com.draniksoft.ome.editor.support.actions.path;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.struct.path.runtime.Path;
import com.draniksoft.ome.editor.struct.path.srz.PathSzr;
import com.draniksoft.ome.editor.support.actions.Action;
import org.jetbrains.annotations.NotNull;

public class CreatePathComponentAction implements Action {

    private static final String tag = "CreatePathComponentAction";

    public int e;

    @Override
    public void invoke(@NotNull World w) {
	  Gdx.app.debug(tag, "Creating");
	  PathDescC c = w.getMapper(PathDescC.class).create(e);
	  PathRunTimeC rtc = w.getMapper(PathRunTimeC.class).create(e);
	  c.ar = new Array<PathSzr>();
	  rtc.ar = new Array<Path>();
    }

    @Override
    public void undo(@NotNull World w) {
	  Gdx.app.debug(tag, "Removing");
	  w.getMapper(PathDescC.class).remove(e);
	  w.getMapper(PathRunTimeC.class).remove(e);
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
	  return "Conclusion";
    }

    @Override
    public void destruct() {

    }
}
