package com.draniksoft.ome.editor.systems.gui;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.systems.render.ui.UIRenderSystem;
import com.draniksoft.ome.editor.ui.BaseWindow;
import com.draniksoft.ome.editor.ui.wins.MOList;
import com.draniksoft.ome.editor.ui.wins.uimgmnt.WindowMgrWin;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;

import java.util.HashMap;

public class UiSystem extends BaseSystem {

    private static String tag = "UiSystem";

    public static class WinCodes {

        public static final int win1 = 1;
        public static final int winMgr = 2;

        public static HashMap<Integer, Class> map = new HashMap<Integer, Class>();

        static {
            System.out.println("Initializing wincodes::map");
            map.put(win1, MOList.class);
            map.put(winMgr, WindowMgrWin.class);
        }
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

        multiplexer.addProcessor(1, uiStage);


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

    public BaseWindow getWin(int c) {

        if (!wins.containsKey(c)) return null;

        return wins.get(c);
    }

    private boolean buildWin(int code) {

        BaseWindow w = null;

        try {
            if (WinCodes.map.containsKey(code))
                w = (BaseWindow) WinCodes.map.get(code).getConstructor().newInstance();
        } catch (Exception e) {
            Gdx.app.debug(tag, "", e);
        }
        if (w == null) return false;

        w.code = code;

        w.init(world);

        wins.put(code, w);
        uiStage.addActor(w);

        return true;

    }

    public ObjectMap.Values<BaseWindow> getWins() {
        return wins.values();
    }

    private void asembleMenuBar() {

        Menu fileM = new Menu("File");

        /*fileM.addItem(new WinOpenMenuItem("Open", WinCodes.open_proj, "lol", this));
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
*/
        menuBar.addMenu(fileM);

    }

}
