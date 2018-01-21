package com.draniksoft.ome.utils.ui.editorMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.ui.menu.BottomMenu;
import com.draniksoft.ome.editor.ui.menu.EditorWin;

public class MenuWinController {

    private static final String tag = "MenuWinController";

    public boolean menuHide = false;
    public boolean menuReplace = false;

    public static final byte TT_RESIZE = 1;
    public static final byte TT_REFRESH = 2;
    public static final byte TT_RESTORE = 3;

    UiSystem sys;
    BottomMenu m;
    EditorWin w;

    public MenuWinController(UiSystem sys, BottomMenu m, EditorWin w) {
	  this.sys = sys;
	  this.m = m;
	  this.w = w;

	  w.setVisible(false);
	  m.get().setWidth(sys.getStageW());
	  m.get().setPosition(0, 0);
    }


    public void apply(byte tt) {
	  Gdx.app.debug(tag, "Applyin " + tt);
	  if (tt == TT_REFRESH) {
		applyBasics();
	  } else if (tt == TT_RESIZE) {
		recalc();
	  } else if (tt == TT_RESTORE) {
		restore();
	  }
    }

    private void restore() {

	  Gdx.app.debug(tag, "Closing win " + w.getViewID());
	  float tx = w.getWidth() / sys.getStageW() * UiSystem.Defaults.aTime100PCT;
	  float ty = Math.abs(m.get().getY()) / sys.getStageH() * UiSystem.Defaults.aTime100PCT;

	  final String tag = w.getViewID();
	  w.addAction(Actions.sequence(
		    Actions.moveTo(sys.getStageW(), w.getY(), tx),
		    new Action() {
			  @Override
			  public boolean act(float delta) {
				w.clearWin();
				w.setVisible(false);

				Gdx.app.debug(MenuWinController.tag, "Win close action completed {" + tag + "}");
				return true;
			  }
		    }
	  ));

	  if (menuHide) {
		m.get().setWidth(sys.getStageW());
		m.get().addAction(Actions.sequence(
			  Actions.delay(tx),
			  Actions.moveTo(0, 0, ty))
		);

	  } else {
		Gdx.app.debug(tag, "sizin menu to full via action ");
		m.get().setPosition(0, 0);
		m.get().addAction(Actions.sizeTo(sys.getStageW(), m.get().getHeight(), tx));
	  }

    }

    private void recalc() {

	  if (w.isOpen()) {
		w.recalc();
		w.setWidth(w.getCalcWidth());
		w.setX(sys.getStageW() - w.getWidth());
		w.setVisible(true);

		if (menuReplace) {
		    m.get().setWidth(w.getX());
		} else {
		    m.get().setWidth(sys.getStageW());
		}
	  } else {
		m.get().setWidth(sys.getStageW());
		w.setX(sys.getStageW() + 1);
	  }


	  if (menuReplace || menuHide) {
		w.setY(0);
		w.setHeight(sys.getStageH());
	  } else {
		w.setY(m.get().getHeight());
		w.setHeight(sys.getStageH() - m.get().getHeight());
	  }

	  m.resized(sys.getStageW());
    }

    private void applyBasics() {

	  m.get().clearActions();
	  w.clearActions();

	  menuHide = w.cfg_menuH;
	  menuReplace = w.cfg_menuR;

	  w.setVisible(true);

	  w.recalc();
	  float newW = w.getCalcWidth();
	  float padd = sys.getStageW() - w.getCalcWidth();


	  Gdx.app.debug(tag, "Applyin basics with neww " + newW);


	  float tx = Math.abs(w.getX() - padd) / sys.getStageW() * UiSystem.Defaults.aTime100PCT;

	  if (menuHide) {
		m.get().setY(-m.get().getWidth());
	  } else if (menuReplace) {
		m.get().addAction(Actions.sizeTo(padd, m.get().getHeight(), tx));
	  } else {
		m.get().addAction(Actions.sizeTo(sys.getStageW(), m.get().getHeight(), 0));
	  }

	  m.setHidden(menuHide);

	  if (menuHide || menuReplace) {
		w.setY(0);
		w.setHeight(sys.getStageH());
	  } else {
		w.setY(m.get().getHeight());
		w.setHeight(sys.getStageH() - m.get().getHeight());
	  }

	  w.setLayoutEnabled(true);
	  w.addAction(
		    Actions.sequence(
				Actions.parallel(
					  Actions.moveTo(padd, w.getY(), tx),
					  Actions.sizeTo(newW, w.getHeight(), tx)
				)
		    ));

    }

}
