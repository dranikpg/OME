package com.draniksoft.ome.editor.ui.wins.asset;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.support.event.asset.AssetListChange;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.ui.ReliantBaseWin;
import com.draniksoft.ome.editor.ui.utils.WinResponseListener;
import com.draniksoft.ome.utils.dao.AssetDDao;
import com.kotcrab.vis.ui.layout.HorizontalFlowGroup;
import com.kotcrab.vis.ui.widget.BusyBar;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class DrawableSelectionWin extends ReliantBaseWin {

    World _w;

    HorizontalFlowGroup group;
    BusyBar b;

    ObjectMap<String, TextureAtlas> ar;
    Array<AssetDDao> curdaos;
    WinResponseListener l;

    boolean updateNeeded = true;
    DrawableMgr mgr;

    VisTable rootT;
    VisTable mainT;

    int ptr;

    public DrawableSelectionWin() {
        super("Choose an asset");
    }

    @Override
    public void init(World w) {
        this._w = w;
        setModal(true);
        centerWindow();

        mgr = _w.getSystem(DrawableMgr.class);

        group = new HorizontalFlowGroup();
        group.setSpacing(10);
        b = new BusyBar();

        //add(b).expandX().fillX().padBottom(5).padTop(5);
        row();

        ScrollPane p = new ScrollPane(group);
        p.setScrollingDisabled(true, false);

        rootT = new VisTable();
        rootT.add(p).expand().fill();
        add(rootT).expand().fill().padLeft(10).padRight(10);


        _w.getSystem(EventSystem.class).registerEvents(this);

        setResizable(true);
        setResizeBorder(20);

        addCloseButton();

    }


    @Override
    public void addCloseButton() {

        VisTextButton b = new VisTextButton("X");
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selfClose();
            }
        });

        getTitleTable().add(b).expand().right();

    }

    private void updateList() {

        curdaos = mgr.getLoaded();
        ar = mgr.getAllAtlasI();
        ptr = -1;

        group.clearChildren();
        b.setVisible(true);

        updateNeeded = false;

    }

    public void reinit(WinResponseListener l) {
        this.l = l;

        if (updateNeeded) updateList();


    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (curdaos == null) return;

        if (ptr >= curdaos.size - 1) return;

        ptr++;

        addDao(curdaos.get(ptr), ar.get(curdaos.get(ptr).id));

        if (ptr == curdaos.size - 1) b.setVisible(false);

    }

    float boxS = 150;

    private void addDao(final AssetDDao assetDDao, TextureAtlas textureAtlas) {

        if (assetDDao.sysmz) return;

        for (final TextureAtlas.AtlasRegion r : textureAtlas.getRegions()) {


            VisImageButton b = new VisImageButton(new TextureRegionDrawable(r));
            b.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selected(assetDDao.id + "@" + r.name + "@" + r.index);

                }
            });

            b.setSize(boxS, boxS);
            group.addActor(b);
        }


    }

    private void selected(String s) {

        l.rsp(WinResponseListener.submitted, s);
        l = null;

        _w.getSystem(UiSystem.class).close(this.code);

    }

    public void selfClose() {

        if (l != null) l.rsp(WinResponseListener.closed, null);
        _w.getSystem(UiSystem.class).close(this.code);

    }


    @Override
    public void on_opened(String uir) {

    }


    @Override
    public void on_closed() {

    }


    @Subscribe
    public void asseListChanged(AssetListChange e) {
        if (isVisible()) {
            updateList();
        } else {
            updateNeeded = true;
        }
    }


}
