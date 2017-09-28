package com.draniksoft.ome.editor.systems.gui;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.support.actions.proj.SaveProjectAction;
import com.draniksoft.ome.editor.systems.render.ui.UIRenderSystem;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.ui.BaseWindow;
import com.draniksoft.ome.editor.ui.utils.UriMenuItem;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;

public class UiSystem extends BaseSystem {

    public static class WinCodes {

        public static final int open_proj = 1;

    }

    UIRenderSystem topSys;

    @Wire(name = "top_stage")
    Stage uiStage;

    @Wire
    InputMultiplexer multiplexer;


    ObjectMap<Integer, BaseWindow> wins;

    MenuBar menuBar;

    Table rootT;

    @Override
    protected void initialize() {

        wins = new ObjectMap<Integer, BaseWindow>();

        rootT = new Table();
        rootT.setFillParent(true);
        uiStage.addActor(rootT);

        menuBar = new MenuBar();

        asembleMenuBar();


        rootT.add(menuBar.getTable()).expand().top();


        //uiStage.setDebugAll(true);

        multiplexer.addProcessor(uiStage);

    }


    @Override
    protected void processSystem() {

    }

    /**
     * We can open something "Twice" because the uri can point to something else
     */
    public void open(int code, String uri) {

        if (!wins.containsKey(code)) {
            if (!buildWin(code)) {
                return;
            }
        }

        wins.get(code).open(uri);


    }

    public void close(int code) {

        /**
         * We can't close something that is not created,
         * or something that is closed
         */
        if (!wins.containsKey(code) || !wins.get(code).isOpen()) {
            return;
        }

        wins.get(code).close();

    }

    private boolean buildWin(int code) {

        BaseWindow w = new BaseWindow("Tst") {
            @Override
            public void init() {

            }

            @Override
            public void open(String uri) {
                row();
                add(uri);
            }

            @Override
            public void close() {

            }

            @Override
            public boolean isOpen() {
                return false;
            }
        };
        w.setResizable(true);


        wins.put(code, w);
        uiStage.addActor(w);

        return true;

    }


    private void asembleMenuBar() {

        Menu fileM = new Menu("File");

        fileM.addItem(new UriMenuItem("Open", WinCodes.open_proj, "lol", this));
        fileM.addItem(new MenuItem("Save", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                world.getSystem(ActionSystem.class).exec(new SaveProjectAction());
            }
        }));


        Menu editM = new Menu("Edit");
        editM.addItem(new MenuItem("Undo", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                world.getSystem(ActionSystem.class).undo();
            }
        }));

        menuBar.addMenu(fileM);

    }

}
