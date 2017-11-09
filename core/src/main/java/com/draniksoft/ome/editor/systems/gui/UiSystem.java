package com.draniksoft.ome.editor.systems.gui;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.ui.core.BaseWin;
import com.draniksoft.ome.editor.ui.core.menu.Menu;
import com.draniksoft.ome.editor.ui.core.menu.MenuContentSupplierI;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.util.HashMap;

public class UiSystem extends BaseSystem {

    private static String tag = "UiSystem";

    public static class WinCodes {

        public static final int win1 = 1;

        public static HashMap<Integer, Class> map = new HashMap<Integer, Class>();

        static {


        }

    }
    @Wire(name = "top_stage")
    Stage uiStage;
    @Wire
    InputMultiplexer multiplexer;
    @Wire(name = "engine_l")
    IntelligentLoader engineL;
    @Wire(name = "ui_vp")
    Viewport vp;


    BaseWin w;
    IntMap<BaseWin> wins;

    Menu mn;

    BaseWin bk;


    @Override
    protected void initialize() {

        engineL.passRunnable(new Loader());

        multiplexer.addProcessor(0, uiStage);

    }

    public void open(int code) {
        open(null, code);
    }

    public void open(String args, int code) {

        boolean d = false;
        if (w != null) {
            close(w.code);
            d = true;
        }

        if (!wins.containsKey(code)) {
            if (!constructWin(code)) return;
        }

        w = wins.get(code);
        w.open(args, d);
    }

    private boolean constructWin(int code) {

        Gdx.app.debug(tag, "Constructing " + WinCodes.map.get(code).getClass().getSimpleName());

        BaseWin w;
        try {
            w = (BaseWin) WinCodes.map.get(code).getConstructor().newInstance();
        } catch (Exception e) {
            Gdx.app.error(tag, "", e);
            return false;
        }

        w.code = code;
        wins.put(code, w);
        w.init(world);

        uiStage.addActor(w);

        w.setHeight(Gdx.graphics.getHeight());
        w.updateBounds(Gdx.graphics.getWidth());
        w.immdClose();


        return true;
    }

    public void close(int code) {
        try {
            w.close();
            w = null;
        } catch (Exception e) {
            Gdx.app.error(tag, "WIN::CLOSE", e);
        }
    }

    public void createBK() {
        if (w == null) return;
        bk = w;
        close(bk.code);
    }

    public void restoreBK() {
        if (bk != null) open("#bkrestore", bk.code);
        bk = null;
    }

    /*

    Menu Part

     */


    public boolean isMenuOpen() {
        return mn.isOpen();
    }

    public MenuContentSupplierI getMenuSupplier() {
        if (!isMenuOpen()) return null;
        return mn.getSup();
    }

    public void closeMenu() {
        mn.close();
    }

    public void openMenu() {
        openMenu(null);
    }

    public void openMenu(String args) {
        mn.open(args);
    }

    @Override
    protected void processSystem() {

        vp.getCamera().update(false);

        vp.apply();

        uiStage.act();

        uiStage.draw();

    }


    @Subscribe
    public void modeChanged(ModeChangeE e) {

        Gdx.app.debug(tag, "Mode changed");

    }

    @Subscribe
    public void resized(ResizeEvent e) {

        for (BaseWin win : wins.values()) {
            win.setHeight(e.h);
            win.updateBounds(e.w);
        }

        mn.resized(e.w, e.h);

    }

    private class Loader implements IRunnable {


        @Override
        public void run(IntelligentLoader l) {


            wins = new IntMap<BaseWin>();

            mn = new Menu();

            JsonValue rootJV = new JsonReader().parse(Gdx.files.internal("_data/ui_vals.json"));

            mn.init(world);
            mn.buildSups(rootJV);
            mn.addToStage(uiStage);

            mn.resized(vp.getWorldHeight(), vp.getWorldHeight());

        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

}
