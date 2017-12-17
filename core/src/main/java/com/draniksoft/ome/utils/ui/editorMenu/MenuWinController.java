package com.draniksoft.ome.utils.ui.editorMenu;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.ui.menu.BottomMenu;
import com.draniksoft.ome.editor.ui.menu.EditorWin;

public class MenuWinController {

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
    }

    public void setMenuHide(boolean menuHide) {
	  this.menuHide = menuHide;
    }

    public void setMenuR(boolean menuR) {
	  this.menuReplace = menuR;
    }


    public void apply(byte tt) {
	  if (tt == TT_REFRESH) {
		applyBasics();
	  } else if (tt == TT_RESIZE) {
		recalc();
	  } else if (tt == TT_RESTORE) {
		restore();
	  }
    }

    private void restore() {

	  float tx = w.getWidth() / sys.getStageW() * UiSystem.Defaults.aTime100PCT;
	  float ty = Math.abs(m.getY()) / sys.getStageH() * UiSystem.Defaults.aTime100PCT;

	  w.addAction(Actions.sequence(
		    Actions.moveTo(sys.getStageW(), w.getY(), tx),
		    new Action() {
			  @Override
			  public boolean act(float delta) {
				w.clearWin();
				w.setVisible(false);
				return true;
			  }
		    }
	  ));

	  if (menuHide) {
		m.setWidth(sys.getStageW());
		m.addAction(Actions.sequence(
			  Actions.delay(tx),
			  Actions.moveTo(0, 0, ty))
		);

	  } else {
		m.addAction(Actions.sizeTo(sys.getStageW(), m.getHeight(), tx));
	  }

    }

    private void recalc() {

	  if (w.isOpen()) {
		w.recalc();
		w.setWidth(w.getCalcWidth());
		w.setX(sys.getStageW() - w.getWidth());
		w.setVisible(true);

		if (menuReplace) {
		    m.setWidth(w.getX());
		} else {
		    m.setWidth(sys.getStageW());
		}
	  } else {
		m.setWidth(sys.getStageW());
		w.setX(sys.getStageW() + 1);
	  }


	  if (menuReplace || menuHide) {
		w.setY(0);
		w.setHeight(sys.getStageH());
	  } else {
		w.setY(m.getHeight());
		w.setHeight(sys.getStageH() - m.getHeight());
	  }

    }

    private void applyBasics() {

	  w.clearActions();

	  if (menuHide) {
		m.setY(-m.getHeight());
	  }
	  w.setWidth(w.getCalcWidth());

	  recalc();

    }

}
