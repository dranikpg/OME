package com.draniksoft.ome.editor.ui.menu.base_addons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;

public class BaseBarButtons extends BaseView {

    @LmlActor("root")
    VisTable t;

    @LmlActor("edit")
    VisImageButton edit;

    @Override
    public Actor get() {
	  return t;
    }

    @Override
    public void preinit() {
    }

    @Override
    public void postinit() {

    }
}
