package com.draniksoft.ome.support.ui.viewsys.impl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.annotation.LmlActor;

public class SecTest extends BaseView {

    @LmlActor("root")
    Table root;

    @Override
    public Actor getActor() {
	  return root;
    }

    @Override
    public void addedAsInclude(BaseView p) {
	  super.addedAsInclude(p);
    }


    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

	  try {
		Thread.sleep(20000);
	  } catch (InterruptedException e) {
		e.printStackTrace();
	  }

    }
}
