package com.draniksoft.ome.editor.ui.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.manager.ProjValsManager;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.projectVals.DrawableEvent;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.draniksoft.ome.utils.struct.MtPair;
import com.draniksoft.ome.utils.ui.wgets.DrawableActor;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import com.github.czyzby.lml.parser.LmlParser;
import com.kotcrab.vis.ui.util.adapter.ArrayAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextField;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.Iterator;

public class DrawableListT1View extends BaseWinView {

    private static final String tag = "DrawableListT1View";

    @LmlActor("root")
    VisTable root;

    @LmlActor("lwt")
    VisTable listViewT;

    @LmlActor("search")
    VisTextField searchField;

    ListView<Integer> lw;
    LWAdapter a;

    ProjValsManager m;

    boolean newS = true;


    float textWidth = 200;
    float dwbSize = 60;
    float pad = 10;


    @Subscribe
    public void dwbEvent(DrawableEvent e) {

	  if (e instanceof DrawableEvent.DrawableAddedE) {

		a.add(e.id);

		invalidateParent();

	  } else if (e instanceof DrawableEvent.DrawableRemovedE) {

		a.removeValue(e.id, false);

	  }

	  a.itemsChanged();

    }

    public void refresh() {

	  Iterator<IntMap.Entry<MtPair<Drawable, String>>> it = m.getDrawableItAll();

	  while (it.hasNext()) {
		a.add(it.next().key);
	  }

	  a.itemsChanged();

    }


    @Override
    public void opened() {
	  super.opened();
	  if (newS) {
		newS = false;
		refresh();
	  }
    }

    @Override
    public void postinit() {


	  searchField.setTextFieldListener(new VisTextField.TextFieldListener() {
		@Override
		public void keyTyped(VisTextField textField, char c) {
		    a.itemsChanged();
		}
	  });

	  searchField.addListener(new InputListener() {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
		    if (keycode == Input.Keys.ESCAPE) {
			  searchField.getStage().setKeyboardFocus(null);
			  return true;
		    }
		    return super.keyDown(event, keycode);

		}
	  });

	  listViewT.add(lw.getMainTable()).expand().fill();

    }


    @Override
    public void preinit() {

	  m = _w.getSystem(ProjValsManager.class);
	  _w.getSystem(OmeEventSystem.class).registerEvents(this);


	  a = new LWAdapter(new Array<Integer>());

	  lw = new ListView<Integer>(a);
	  lw.setUpdatePolicy(ListView.UpdatePolicy.ON_DRAW);

	  lw.getScrollPane().addListener(new InputListener() {
		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		    super.exit(event, x, y, pointer, toActor);
		    if (lw.getScrollPane().getStage() != null) lw.getScrollPane().getStage().setScrollFocus(null);
		}

		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		    super.enter(event, x, y, pointer, fromActor);
		    lw.getScrollPane().getStage().setScrollFocus(lw.getScrollPane());
		}
	  });


    }

    private class DrawableView extends VisTable {

	  VisLabel name;
	  Container<DrawableActor> imgC;

	  int id;

	  public void create(int id) {
		this.id = id;

		name = new VisLabel();
		name.setEllipsis(true);

		imgC = new Container<DrawableActor>();

		Drawable d = m.getDrawable(id);
		DrawableActor a = new DrawableActor();
		a.d = d;

		if (d == null) {
		    Gdx.app.error(tag, "NULL DRAWABLE");
		}

		imgC.setActor(a);
		imgC.minSize(dwbSize);

		add(imgC).padRight(pad);
		add(name).width(textWidth);

	  }

	  public void update() {

		name.setText(m.getDrawableName(id));

	  }

	  public boolean matches(String filter) {
		return m.getDrawableName(id).contains(filter);
	  }

    }

    private class LWAdapter extends ArrayAdapter<Integer, DrawableView> {

	  public LWAdapter(Array<Integer> array) {
		super(array);
		//gp = new HorizontalFlowGroup();
	  }


	  @Override
	  public void fillTable(VisTable itemsTable) {

		String filter = "";

		//itemsTable.add(gp).expand().fill();
		//gp.clearChildren();


		if (searchField != null && searchField.getText() != null)
		    filter = searchField.getText().trim();


		for (final Integer item : iterable()) {

		    final DrawableView view = getView(item);

		    if (filter.equals("") || view.matches(filter)) {
			  prepareViewBeforeAddingToTable(item, view);
			  //gp.addActor(view);
			  itemsTable.add(view).growX();
			  itemsTable.row();
		    }

		}

	  }

	  @Override
	  protected void updateView(DrawableView view, Integer item) {

		view.update();

	  }

	  @Override
	  protected DrawableView createView(Integer item) {

		DrawableView v = new DrawableView();

		v.create(item);

		return v;

	  }
    }


    @LmlAction("addDwb")
    public void addDwb() {

    }

    @Override
    public void prepareParser(LmlParser p) {
	  super.prepareParser(p);
    }

    @Override
    public void clearParser(LmlParser p) {
	  super.clearParser(p);
	  textWidth = p.parseFloat(p.getData().getArgument("_1"));
	  dwbSize = p.parseFloat(p.getData().getArgument("_2"));
	  pad = p.parseFloat(p.getData().getArgument("_3"));
    }

    @Override
    public Actor getActor() {
	  return root;
    }
}
