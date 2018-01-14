package com.draniksoft.ome.editor.ui.insp;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class PathView extends BaseView implements InspView.InspectorManagable {

    @LmlActor("root")
    VisTable root;

    @Override
    public void initFor(int e) {

    }

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
