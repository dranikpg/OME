package com.draniksoft.ome.editor.systems.gui;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.ui.menu.BottomMenu;
import com.draniksoft.ome.editor.ui.menu.EditorWin;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.support.ui.util.WindowAgent;
import com.draniksoft.ome.utils.ui.editorMenu.MenuWinController;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class UiSystem extends BaseSystem {
    private static String tag = "UiSystem";

    public static class Defaults {

        // animation time per screenWidth pixels
        public static float aTime100PCT = 2f;
    }

    @Wire(name = "top_stage")
    Stage uiStage;
    @Wire
    InputMultiplexer multiplexer;
    @Wire(name = "engine_l")
    IntelligentLoader engineL;
    @Wire(name = "ui_vp")
    Viewport vp;

    BottomMenu m;
    EditorWin w;

    MenuWinController ctr;

    String bkID;

    @Override
    protected void initialize() {
        engineL.passRunnable(new Loader());
        world.getSystem(EventSystem.class).registerEvents(this);
        multiplexer.addProcessor(0, uiStage);
    }

    @Override
    protected void processSystem() {
        vp.getCamera().update(false);
        vp.apply();
        uiStage.act();
        uiStage.draw();

        uiStage.setKeyboardFocus(null);
    }

    public void validateLayout() {
        ctr.apply(MenuWinController.TT_REFRESH);
    }

    public void realignLayout() {
        ctr.apply(MenuWinController.TT_RESIZE);
    }

    public void openWin(String id) {
        openWin(id, null);
    }

    public void openWin(String id, WindowAgent ag) {
        if (!w.canOpen(id)) return;
        _open(id, ag);
    }

    private void _open(String id, WindowAgent ag) {
        w.open(id, ag);
    }

    public void closeWin() {
        ctr.apply(MenuWinController.TT_RESTORE);
    }


    private class Loader implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {

            m = new BottomMenu(UiSystem.this);
            w = new EditorWin(world);

            w.setX(uiStage.getWidth());

            uiStage.addActor(w);
            uiStage.addActor(m);

            ctr = new MenuWinController(UiSystem.this, m, w);


        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }

    @Subscribe
    public void modeChanged(ModeChangeE e) {
        Gdx.app.debug(tag, "Mode changed");
    }

    @Subscribe
    public void resized(ResizeEvent e) {
        if (w.isOpen()) {
            w.recalc();
        }
        realignLayout();
    }

    public float getStageW() {
        return uiStage.getWidth();
    }

    public float getStageH() {
        return uiStage.getHeight();
    }

    public MenuWinController getCtr() {
        return ctr;
    }

    public void createBK() {
        bkID = w.getViewID();
    }

    public void inflateBK() {
        closeWin();
        if (bkID != null) {
            openWin(bkID);
        }
    }

}
