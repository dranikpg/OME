package com.draniksoft.ome.editor.systems.gui;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.draniksoft.ome.editor.support.event.base_gfx.ResizeEvent;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.editor.ui.insp.InspView;
import com.draniksoft.ome.editor.ui.menu.BottomMenu;
import com.draniksoft.ome.editor.ui.menu.EditorWin;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.support.ui.util.WindowAgent;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.draniksoft.ome.utils.ui.editorMenu.MenuWinController;
import net.mostlyoriginal.api.event.common.Subscribe;

public class UiSystem extends BaseSystem {
    private static String tag = "UiSystem";

    public static class Defaults {

        // animation time per screenWidth pixels
	  public static float aTime100PCT = 0.7f;
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
        multiplexer.addProcessor(0, uiStage);

        AppDO.I.LML().parseView(new ResponseListener() {
            @Override
            public void onResponse(short code) {

                initBase();

            }
        }, "bar");

    }

    private void initBase() {

        m = AppDO.I.LML().obtainParsed();

        w = new EditorWin(world);

	  uiStage.addActor(m.get());
	  uiStage.addActor(w);

        m.opened();

        ctr = new MenuWinController(this, m, w);


    }

    @Override
    protected void processSystem() {
        vp.getCamera().update(false);
        vp.apply();
        uiStage.act();
        uiStage.draw();

    }

    public void validateLayout() {
        Gdx.app.debug(tag, "validating layout");
        ctr.apply(MenuWinController.TT_REFRESH);
    }

    public void realignLayout() {
        if (ctr != null) ctr.apply(MenuWinController.TT_RESIZE);
    }

    public void openWin(String id) {
        openWin(id, null);
    }

    public void openWin(String id, WindowAgent ag) {
        if (!w.canOpen(id)) return;
        _open(id, ag);
    }

    private void _open(String id, WindowAgent ag) {
	  w.notifyClosing();
	  w.open(id, ag);
    }

    public void closeWin() {
	  Gdx.app.debug(tag, "Called closewin");
	  ctr.apply(MenuWinController.TT_RESTORE);
	  w.notifyClosing();
    }


    private class Loader implements IRunnable {

        @Override
        public void run(IntelligentLoader l) {



        }

        @Override
        public byte getState() {
            return IRunnable.RUNNING;
        }
    }



    @Subscribe
    public void resized(ResizeEvent e) {

        realignLayout();
    }

    @Subscribe
    public void modeC(ModeChangeE e) {
        if (e instanceof ModeChangeE.ShowEnterEvent) {
            createBK();
            m.modeChanged(true);
        } else if (e instanceof ModeChangeE.ShowQuitEvent) {
            inflateBK();
            m.modeChanged(false);
        }
    }

    public void openInspector(final int id) {

	  openWin("inspector", new WindowAgent() {
		@Override
		public void opened(BaseWinView vw) {
		    ((InspView) vw).initFor(id);
		}

		@Override
		public void notifyClosing() {
		    Gdx.app.debug(tag, "Inspector is closing ");

		}

		@Override
		public void closed() {

		}
	  });
    }

    @Subscribe
    public void selChanged(SelectionChangeE e) {
	  Gdx.app.debug(tag, "Win wget " + w.getViewID());
	  if (e.n >= 0) {
		openInspector(e.n);
	  } else if (w.getViewID() != null && w.getViewID().equals("inspector")) {
		closeWin();
	  }

    }

    public float getWinW() {
	  return w.isOpen() ? w.getWidth() : 0;
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

	  Gdx.app.debug(tag, "Inflating bk");

	  if (w.isOpen()) closeWin();

        if (bkID != null) {
            openWin(bkID);
        }
    }

}
