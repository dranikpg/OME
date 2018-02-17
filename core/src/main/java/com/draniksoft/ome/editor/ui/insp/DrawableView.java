package com.draniksoft.ome.editor.ui.insp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.res.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.widget.VisTable;

public class DrawableView extends BaseWinView implements InspView.InspectorManagable {

    private static final String tag = "DrawableView";

    @LmlActor("root")
    VisTable root;

    @LmlActor("btn")
    Button btn;

    int e;

    @Override
    public Actor get() {
	  return root;
    }

    @Override
    public void preinit() {

    }

    @Override
    public void postinit() {
	  btn.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    if (e > -1) {
			  Gdx.app.debug(tag, "CLICK");
		    }
		}
	  });
    }

    @Override
    public void closed() {
	  super.closed();
	  e = -1;
    }

    @Override
    public void initFor(int e) {
	  this.e = e;
	  DrawableC c = _w.getMapper(DrawableC.class).get(e);
	  btn.setBackground(GdxCompatibleDrawable.from(c.d));
    }
}
