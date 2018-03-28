package com.draniksoft.ome.editor.ui.edit.dwb_typevw;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.res.drawable.constr.DrawableLeafContructor;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.impl.constructor.ResConstructor;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;
import com.draniksoft.ome.editor.ui.proj.AssetListView;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class DwbLeafView extends BaseView implements DwbEditI {

    private static final String tag = "DwbLeafView";

    DrawableLeafContructor c;

    @LmlActor("root")
    VisTable root;

    AssetListView lw;

    @Override
    public Actor get() {
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
	  root.add(vw.get()).expand().fill();

	  this.lw = (AssetListView) vw;

	  lw.setListener(new ResponseListener() {
		@Override
		public void onResponse(short code) {

		    AssetListView.AssetImg i = lw.getSelection().getLastSelected();

		    if (i == null) return;
		    if (c == null) return;

		    //c.setFor(i.getR());

		}
	  });


    }

    @Override
    public void setFor(ResConstructor<Drawable> c, EditDwbView.Handler h) {
	  this.c = (DrawableLeafContructor) c;
    }

    @Override
    public void typeUpdated() {

    }

    @Override
    public String getID() {
	  return ID;
    }
}
