package com.draniksoft.ome.editor.ui.edit.dwb_typevw;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.DwbConstructor;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.GroupConstructor;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.t.DwbGroupTypes;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;

public class DwbGroupView extends BaseView implements DwbEditI {

    private static String tag = "DwbGroupView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("typebox")
    VisSelectBox<DwbGroupTypes> typebox;

    GroupConstructor c;

    @Override
    public Actor getActor() {
	  return root;
    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

	  typebox.setItems(DwbGroupTypes.values());

	  typebox.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {

		    if (c == null) return;

		    c.setType(typebox.getSelected());

		}
	  });
    }

    @Override
    public void setFor(DwbConstructor c, EditDwbView.ActionHandler h) {
	  this.c = (GroupConstructor) c;

	  typebox.setSelected(((GroupConstructor) c).getType());

    }

    @Override
    public String getID() {
	  return ID;
    }
}
