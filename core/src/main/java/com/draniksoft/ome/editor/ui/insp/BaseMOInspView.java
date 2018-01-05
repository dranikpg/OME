package com.draniksoft.ome.editor.ui.insp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.draniksoft.ome.editor.base_gfx.drawable.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.components.tps.MObjectC;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.utils.FUtills;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;

public class BaseMOInspView extends BaseView implements ActionContainer, InspView.InspectorManagable {

    public static String tag = "BaseMOInspView";

    @LmlActor("root")
    VisTable root;


    @LmlActor("imgC")
    Container imgC;

    int e;
    MObjectC c;

    @Override
    public void initFor(int e) {

	  Gdx.app.debug(tag, "Inting for " + e);

	  this.e = e;
	  if (e < 0) return;

	  c = _w.getMapper(MObjectC.class).get(e);

	  imgC.setActor(new VisImage(
		    GdxCompatibleDrawable.from(FUtills.fetchDrawable(c.dwbID))
	  ));
	  imgC.minSize(90f);

    }

    private void startMoveEM() {
	  _w.getSystem(EditorSystem.class).attachNewEditMode(EditModeDesc.IDS.moveMO);
    }

    @LmlAction("l.move_em")
    public void move_em() {
	  startMoveEM();
    }

    @Override
    public void opened() {
	  super.opened();

    }

    @Override
    public void prepareParser(LmlParser p) {
	  super.prepareParser(p);
	  p.getData().addActionContainer("l", this);
    }

    @Override
    public void clearParser(LmlParser p) {
	  super.clearParser(p);
	  p.getData().removeActionContainer("l");
    }

    @Override
    public void preinit() {
    }

    @Override
    public void postinit() {
	  imgC.maxSize(200f);
    }

    @Override
    public Actor getActor() {
	  return root;
    }


}
