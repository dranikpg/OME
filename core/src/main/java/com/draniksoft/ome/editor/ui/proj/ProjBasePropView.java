package com.draniksoft.ome.editor.ui.proj;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class ProjBasePropView extends BaseWinView {

    @LmlActor("root")
    VisTable root;

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
}
