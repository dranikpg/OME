package com.draniksoft.ome.editor.ui.wins.asset;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.event.asset.AssetListChange;
import com.draniksoft.ome.editor.ui.SupportedReliantWin;
import com.draniksoft.ome.utils.dao.AssetDDao;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.util.adapter.AbstractListAdapter;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.widget.*;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class AssetMgmntWin extends SupportedReliantWin {

    public AssetMgmntWin() {
        super("Asset management window");
    }

    SimpleListAdapter<AssetDDao> adapter;
    ListView<AssetDDao> lw;

    DrawableMgr mgr;

    VisTable leftT;
    VisTable rightT;
    VisTable bottomT;
    VisSplitPane pane;

    VisTextField searchField;
    VisTextButton firstButton;
    VisTextButton secondButton;
    BusyBar loadB;

    boolean loadMode = false;

    @Override
    public void _init(World w) {

        mgr = w.getSystem(DrawableMgr.class);
        w.getSystem(EventSystem.class).registerEvents(this);

        leftT = new VisTable();
        rightT = new VisTable();
        bottomT = new VisTable();
        pane = new VisSplitPane(leftT, rightT, false);

        adapter = new SimpleListAdapter<AssetDDao>(filterDesc(false)) {

            @Override
            protected VisTable createView(AssetDDao item) {
                VisTable t = new VisTable();
                t.add(item.name).padTop(5);
                return t;
            }


        };
        lw = new ListView<AssetDDao>(adapter);

        adapter.setSelectionMode(AbstractListAdapter.SelectionMode.SINGLE);
        lw.setItemClickListener(new ListView.ItemClickListener<AssetDDao>() {
            @Override
            public void clicked(AssetDDao item) {
                secondButton.setVisible(true);
                updateInspector(item);
            }
        });

        leftT.add(lw.getMainTable()).expand().fill();


        add(pane).expand().fill().padTop(10).padRight(15);
        row();
        add(new Separator()).expandX().fillX().padTop(10).padBottom(10);
        row();
        add(bottomT).expandX().fill();

        initBarElements();
        leftT.row();
        leftT.add(searchField).expandX().fillX().padTop(5);

        changeMode(false);
        updateToMode();

        setResizable(true);
        setResizeBorder(15);
        setSize(600, 400);

    }

    private void initBarElements() {

        loadB = new BusyBar();

        searchField = new VisTextField();
        searchField.setMessageText("Type to search");
        firstButton = new VisTextButton("");
        secondButton = new VisTextButton("");

        firstButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (loadMode) {
                    changeMode(false);
                } else {
                    changeMode(true);
                }
                updateToMode();

            }
        });

        secondButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (loadMode) {

                    if (adapter.getSelection().size > 0)
                        mgr.loadDwbl(adapter.getSelection().get(0));

                }

            }
        });

        secondButton.setVisible(false);


        //bottomT.add(searchField).left().padLeft(5).padRight(20);

        bottomT.add(firstButton).padLeft(15).expand().left();

        bottomT.add(secondButton).padRight(20);

        bottomT.row();

        HorizontalGroup hg = new HorizontalGroup();
        hg.addActor(loadB);

        bottomT.add(hg).colspan(3).fillX().padTop(5).padBottom(10);
    }

    private void reloadBar() {

        if (loadMode) {

            firstButton.setText("Back");
            secondButton.setText("Load");

        } else {

            firstButton.setText("Add assets");
            secondButton.setText("Remove");

        }

    }

    float tileSize = 160;

    private void updateInspector(AssetDDao d) {

        rightT.clearChildren();

        if (d == null) return;

        rightT.add(d.name);
        rightT.row();

        rightT.add(d.uri).padBottom(10);
        rightT.row();


        if (d.loaded) {
            HorizontalFlowGroup gp = new HorizontalFlowGroup();

            gp.setSpacing(tileSize / 10);

            ScrollPane p = new VisScrollPane(gp);

            TextureAtlas a = mgr.getAtlas(d.id);

            for (TextureAtlas.AtlasRegion r : a.getRegions()) {

                Table tb = new VisTable();

                VisImage img = new VisImage(r);
                tb.add(img);
                tb.row();

                VisLabel l = new VisLabel(r.name + (r.index > 0 ? "(" + r.index + ")" : ""));
                l.setWrap(true);
                tb.add(l).expandX().width(tileSize);

                tb.setSize(tileSize, tileSize);
                gp.addActor(tb);
            }

            rightT.add(p).expand().fill().padLeft(5);
            p.setScrollingDisabled(true, false);


        }


    }

    private Array<AssetDDao> filterDesc(boolean onlyToLoad) {

        if (onlyToLoad) {

            Array<AssetDDao> ads = mgr.getAllAviabDao();
            Array<AssetDDao> ds = new Array<AssetDDao>(ads.size);

            for (AssetDDao dao : ads) {

                if (!dao.sysmz && !dao.loaded) ds.add(dao);

            }

            return ds;

        } else {
            ObjectMap.Values<AssetDDao> vs = mgr.getAllDescI().values();
            Array<AssetDDao> ds = new Array<AssetDDao>(mgr.getAllDescI().size);

            for (AssetDDao d : vs) {

                if (!d.sysmz) ds.add(d);

            }

            return ds;
        }

    }


    private void changeMode(boolean val) {
        loadMode = val;
    }

    private void updateToMode() {

        reloadBar();

        if (loadMode) {
            adapter.clear();
            adapter.addAll(filterDesc(true));
        } else {
            adapter.clear();
            adapter.addAll(filterDesc(false));
        }

        adapter.itemsChanged();
        updateInspector(null);

    }


    @Override
    public void on_opened(String uir) {

    }

    @Override
    public void on_closed() {

        if (getStage().getScrollFocus().getParent() == rightT)

            getStage().setScrollFocus(null);


    }


    @Override
    protected void maximized() {

    }

    @Override
    public void minimized() {

    }


    @Override
    public void act(float delta) {
        super.act(delta);

        loadB.setVisible(mgr.getLoadDs().size != 0);

        if (adapter.getSelection().size > 0) {
            if (mgr.getLoadDs().size != 0)
                secondButton.setVisible(false);
            else secondButton.setVisible(true);
        }

    }

    @Subscribe
    public void onLoadedAssChanged(AssetListChange e) {

        if (!loadMode) {
            adapter.add(mgr.getAllDescI().get(e.id));
            adapter.itemsChanged();
        } else {
            adapter.removeValue(mgr.getAllDescI().get(e.id), true);
            adapter.itemsChanged();
        }
    }
}
