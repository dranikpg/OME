package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.systems.render.UIRenderSystem;
import com.draniksoft.ome.editor.ui.BaseWindow;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;

public class UiSystem extends BaseSystem {

    public static class WinCodes {

    }

    UIRenderSystem topSys;

    @Wire(name = "top_stage")
    Stage uiStage;


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
        menuBar.addMenu(new Menu("LOL KEK"));

        rootT.add(menuBar.getTable()).expand().bottom().fillX();


        uiStage.setDebugAll(true);
    }

    @Override
    protected void processSystem() {

    }

    /**
     * We can open something "Twice" because the uri can point to something else
     */
    public void open(int code, String uri) {

        if (!wins.containsKey(code)) {
            if (!buildWin()) {
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

    private boolean buildWin() {

        return true;

    }


}
