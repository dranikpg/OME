package com.draniksoft.ome.editor.ui.edit.dwb_typevw;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res_mgmnt.constructor.ResConstructor;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class DwbGroupView extends BaseView implements DwbEditI {

    private static String tag = "DwbGroupView";

    @LmlActor("root")
    VisTable root;


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
    public void setFor(ResConstructor<Drawable> c, EditDwbView.Handler h) {

    }

    @Override
    public void typeUpdated() {

    }

    @Override
    public String getID() {
	  return ID;
    }
}
