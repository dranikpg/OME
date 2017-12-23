package com.draniksoft.ome.editor.ui.menu.base_addons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.utils.DateUtils;
import com.kotcrab.vis.ui.widget.VisLabel;

public class BaseShowButtons extends BaseView {

    VisLabel l;

    @Override
    public Actor getActor() {
	  return l;
    }

    @Override
    public void preinit() {
	  l = new VisLabel("", "dgs") {
		@Override
		public void act(float delta) {
		    super.act(delta);
		    setText(DateUtils.format(_w.getSystem(TimeMgr.class).getTime()));
		}
	  };
    }

    @Override
    public void postinit() {

    }
}
