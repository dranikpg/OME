package com.draniksoft.ome.editor.ui.edit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.layout.VerticalFlowGroup;
import com.kotcrab.vis.ui.widget.BusyBar;
import com.kotcrab.vis.ui.widget.VisTable;

public class ChangeDwbView extends BaseWinView {

    @LmlActor("root")
    VisTable root;

    @LmlActor("bb")
    BusyBar bb;

    @LmlActor("gp")
    VerticalFlowGroup gp;

    int e;

    private void initFor(int _e, boolean imdReb) {
	  e = _e;
	  if (imdReb) {
		rebuild();
	  }
    }

    private void rebuild() {

	  rebuildGroup();

    }

    private void rebuildGroup() {


    }


    public void initFor(int _e) {
	  initFor(_e, true);
    }

    public void setFor(int _e) {
	  initFor(_e, false);
    }

    public void cancel() {

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
