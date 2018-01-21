package com.draniksoft.ome.editor.ui.proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.manager.drawable.SimpleAssMgr;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.file_b.ResourceLoadedEvent;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.utils.dao.AssetDDao;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.github.czyzby.lml.annotation.LmlActor;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.widget.CollapsibleWidget;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisTable;

import java.util.Iterator;

public class AssetListView extends BaseView {

    private static String tag = "AssetListView";

    @LmlActor("root")
    VisTable root;

    ObjectMap<String, AssGroup> m;

    Selection<AssetImg> s;

    ResponseListener l;


    @Override
    public Actor get() {
	  return root;
    }

    @Override
    public void preinit() {

	  s = new Selection<AssetImg>() {
		@Override
		protected void changed() {
		    if (l != null) l.onResponse((short) 0);
		    updateSel();
		}
	  };

	  s.setMultiple(false);


	  m = new ObjectMap<String, AssGroup>();

    }

    @Override
    public void postinit() {
	  _w.getSystem(OmeEventSystem.class).registerEvents(this);

	  Iterator<AssetDDao> it = _w.getSystem(SimpleAssMgr.class).getLoadedDaoI();
	  while (it.hasNext()) {
		buildGroup(it.next().id);
	  }

    }

    public void loadEvent(ResourceLoadedEvent e) {
	  if (e.t != ResourceLoadedEvent.TYPE_ASSEt) return;

	  buildGroup(e.id);
    }

    private void updateSel() {
	  for (AssGroup g : m.values()) {
		for (AssetImg img : g.imgA) {
		    if (s.contains(img)) {
			  img.setChecked(true);
		    } else {
			  img.setChecked(false);
		    }
		}
	  }
    }

    public void clearSelection() {
	  s.clear();
    }

    public Selection<AssetImg> getSelection() {
	  return s;
    }

    @Override
    public void opened() {
	  super.opened();
	  clearSelection();
    }

    @Override
    public void closed() {
	  super.closed();
	  l = null;
    }

    private void buildGroup(String id) {

	  Gdx.app.debug(tag, "Building " + id);

	  AssGroup a = new AssGroup();

	  VisTable fullt = new VisTable();
	  fullt.setName(id);

	  VisTable titleT = new VisTable();
	  titleT.add(id);

	  VisTable contentT = new VisTable();
	  HorizontalFlowGroup ct = new HorizontalFlowGroup();


	  Array<AssetImg> ar = new Array<AssetImg>();

	  for (TextureAtlas.AtlasRegion r : _w.getSystem(SimpleAssMgr.class).getAtlas(id).getRegions()) {
		AssetImg img = new AssetImg();
		img.setFor(r);

		contentT.add(img).minSize(50f);
		ar.add(img);
	  }

	  fullt.add(titleT).padBottom(5);
	  fullt.row();
	  fullt.add(contentT).expandX().fillX();


	  a.fullt = fullt;
	  a.title = titleT;
	  a.imgA = ar;
	  a.ct = ct;
	  a.contentT = contentT;

	  m.put(id, a);

	  //

	  root.add(fullt).expandX().fillX();
	  root.row();
	  root.add(new Separator("small")).expandX().fillX().padTop(10).padBottom(10);
	  root.row();


    }

    public class AssGroup {

	  VisTable fullt;

	  VisTable title;

	  VisTable contentT;

	  HorizontalFlowGroup ct;

	  CollapsibleWidget clsW;

	  Array<AssetImg> imgA;

    }

    public class AssetImg extends Button {

	  TextureRegion r;

	  VisImage i;

	  public AssetImg() {
		super(VisUI.getSkin(), "sel");

		i = new VisImage();
		add(i).expand().fill().pad(10);

		setProgrammaticChangeEvents(false);
		addListener(new ChangeListener() {
		    @Override
		    public void changed(ChangeEvent event, Actor actor) {
			  if (!isChecked()) {
				s.remove(AssetImg.this);
			  } else {
				Gdx.app.debug(tag, "set sel");
				s.set(AssetImg.this);
			  }
			  updateSel();
		    }
		});
	  }

	  public void setFor(TextureAtlas.AtlasRegion r) {
		this.r = r;
		TextureRegionDrawable d = new TextureRegionDrawable(r);
		i.setDrawable(d);

	  }

	  public TextureRegion getR() {
		return r;
	  }
    }

    public void setListener(ResponseListener l) {
	  this.l = l;
    }
}
