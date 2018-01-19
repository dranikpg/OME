package com.draniksoft.ome.editor.ui.edit.dwb_typevw;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.DwbConstructor;
import com.draniksoft.ome.editor.base_gfx.drawable_contructor.LeafConstructor;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;
import com.draniksoft.ome.editor.ui.proj.AssetListView;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class DwbLeafView extends BaseView implements DwbEditI {

    private static final String tag = "DwbLeafView";

    LeafConstructor c;

    @LmlActor("root")
    VisTable root;

    AssetListView lw;

    @Override
    public Actor getActor() {
	  return root;
    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

    }

    @Override
    protected void handleInclude(String nm, BaseView vw) {
	  super.handleInclude(nm, vw);

	  root.clearChildren();
	  root.add(vw.getActor()).expand().fill();

	  this.lw = (AssetListView) vw;

	  lw.setListener(new ResponseListener() {
		@Override
		public void onResponse(short code) {

		    AssetListView.AssetImg i = lw.getSelection().getLastSelected();

		    if (i == null) return;
		    if (c == null) return;

		    c.setFor(i.getR());

		}
	  });


    }


    @Override
    public void setFor(DwbConstructor c, EditDwbView.ActionHandler h) {
	  this.c = (LeafConstructor) c;

    }

    @Override
    public String getID() {
	  return ID;
    }
}
