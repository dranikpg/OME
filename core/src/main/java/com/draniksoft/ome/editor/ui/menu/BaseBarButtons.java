package com.draniksoft.ome.editor.ui.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;

public class BaseBarButtons extends BaseView {

    @LmlActor("root")
    Table t;

    @Override
    public Actor getActor() {
	  return t;
    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

    }
}
