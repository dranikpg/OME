package com.draniksoft.ome.support.ui.viewsys.impl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;

public class TestV extends BaseWinView {

    @LmlActor("root")
    Table t;

    @LmlActor("incl")
    Container c;

    @Override
    public Actor get() {
	  return t;
    }

    @Override
    public void obtainIncld(String name, BaseView vw) {
	  c.setActor(vw.get());
	  c.fill();
    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

    }
}
