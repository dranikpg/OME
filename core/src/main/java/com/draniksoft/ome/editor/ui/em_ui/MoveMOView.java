package com.draniksoft.ome.editor.ui.em_ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;

public class MoveMOView extends BaseWinView {

    @LmlActor("root")
    Table root;

    @Override
    public Actor getActor() {
	  return root;
    }

    @Override
    public void prepareParser(LmlParser p) {
	  super.prepareParser(p);
    }

    @Override
    public void clearParser(LmlParser p) {
	  super.clearParser(p);
    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {

    }
}
