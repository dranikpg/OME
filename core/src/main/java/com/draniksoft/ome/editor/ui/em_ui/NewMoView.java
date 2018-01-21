package com.draniksoft.ome.editor.ui.em_ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class NewMoView extends BaseWinView {

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
}
