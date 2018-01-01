package com.draniksoft.ome.editor.ui.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.manager.ColorManager;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.color.ColorEvent;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.draniksoft.ome.ui_addons.ColoredCirlceWget;
import com.draniksoft.ome.utils.struct.EColor;
import com.draniksoft.ome.utils.struct.MtPair;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.kotcrab.vis.ui.layout.VerticalFlowGroup;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import net.mostlyoriginal.api.event.common.Subscribe;
import org.jetbrains.annotations.Nullable;

public class ColorListT1View extends BaseWinView {

    private static final String tag = "ColorListT1View";

    @LmlActor("root")
    VisTable root;


    @LmlActor("bc")
    Container bc;

    @LmlActor("spane")
    VisScrollPane pane;

    VerticalFlowGroup group;

    ColorEditView editV;


    float space = 10;
    float labelPad = 10;
    float cellw = 200;

    LWTable curSel;

    @Override
    public void preinit() {
	  group = new VerticalFlowGroup();
    }

    @Override
    public void clearParser(LmlParser p) {
	  space = p.parseFloat(p.getData().getArgument("_1"));
	  labelPad = p.parseFloat(p.getData().getArgument("_2"));
	  cellw = p.parseFloat(p.getData().getArgument("_3"));
    }

    @Override
    public void postinit() {

	  pane.setActor(group);
	  group.setSpacing(space);

	  pane.setScrollingDisabled(false, true);

	  _w.getSystem(OmeEventSystem.class).registerEvents(this);


    }

    boolean neeedU = true;

    @Subscribe
    public void colorEV(ColorEvent ev) {
	  if (active) {
		if (ev instanceof ColorEvent.ColorAddedEvent) {
		    group.addActor(new LWTable(_w.getSystem(ColorManager.class).getP(ev.id)));
		    recalc();
		} else {
		    for (Actor a : group.getChildren()) {
			  if (a instanceof LWTable && ((LWTable) a).getId() == ev.id) {
				((LWTable) a).rebuild();
			  }
		    }
		}

	  } else {
		neeedU = true;
	  }
    }


    public void rebuildLV() {

	  Gdx.app.debug(tag, "Rebuilding");

	  group.clear();
	  ObjectMap.Entries<Integer, MtPair<EColor, String>> i = _w.getSystem(ColorManager.class).getDataI();
	  ObjectMap.Entry<Integer, MtPair<EColor, String>> e;
	  while (i.hasNext()) {
		e = i.next();
		LWTable t = new LWTable(e.value);
		group.addActor(t);
	  }
    }


    private class LWTable extends VisTable {

	  int id;

	  ColoredCirlceWget wget;
	  VisLabel name;

	  boolean sel = false;

	  LWTable(final MtPair<EColor, String> data) {
		this.id = data.K().id;

		addListener(new ClickListener() {
		    @Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			  setSelected(id, LWTable.this);
			  return super.touchDown(event, x, y, pointer, button);

		    }
		});

		collosalReb();

	  }

	  public void collosalReb() {

		clearChildren();

		name = new VisLabel("", sel ? "big_primary_dark" : "big");
		name.setEllipsis(true);

		wget = new ColoredCirlceWget(_w.getSystem(ColorManager.class).newMgnd(id));

		add(wget).padRight(labelPad);
		add(name).left().width(cellw);

		rebuild();
	  }

	  public int getId() {
		return id;
	  }

	  public void rebuild() {
		if (!_w.getSystem(ColorManager.class).has(id)) {
		    setSelected(-1);
		    group.removeActor(this, true);
		} else {
		    name.setText(_w.getSystem(ColorManager.class).getName(id));
		}
	  }

	  public void deselect() {
		sel = false;
		if (_w.getSystem(ColorManager.class).has(id)) collosalReb();
	  }


	  public void select() {
		sel = true;
		collosalReb();
	  }
    }

    private void setSelected(int id) {
	  setSelected(id, null);
    }

    private void setSelected(int id, @Nullable LWTable t) {
	  if (curSel != null) {
		curSel.deselect();
	  }
	  if (t != null) {
		curSel = t;
		t.select();
	  }
	  editV.initfor(id);
    }


    @Override
    public void opened() {
	  super.opened();
	  if (neeedU) {
		rebuildLV();
		neeedU = false;
	  }
    }

    @Override
    public void closed() {
	  setSelected(-1);
	  super.closed();
    }

    @Override
    public Actor getActor() {
	  return root;
    }

    @Override
    public void obtainIncld(String name, BaseView vw) {
	  editV = (ColorEditView) vw;
	  editV.initfor(-1);

	  bc.setActor(vw.getActor());
	  bc.fill();
    }

}
