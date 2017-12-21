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
	  m.getActor().setWidth(sys.getStageW());
	  m.getActor().setPosition(0, 0);
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
	  float ty = Math.abs(m.getActor().getY()) / sys.getStageH() * UiSystem.Defaults.aTime100PCT;

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
		m.getActor().setWidth(sys.getStageW());
		m.getActor().addAction(Actions.sequence(
			  Actions.delay(tx),
			  Actions.moveTo(0, 0, ty))
		);

	  } else {
		m.getActor().addAction(Actions.sizeTo(sys.getStageW(), m.getActor().getHeight(), tx));
	  }

    }

    private void recalc() {

	  if (w.isOpen()) {
		w.recalc();
		w.setWidth(w.getCalcWidth());
		w.setX(sys.getStageW() - w.getWidth());
		w.setVisible(true);

		if (menuReplace) {
		    m.getActor().setWidth(w.getX());
		} else {
		    m.getActor().setWidth(sys.getStageW());
		}
	  } else {
		m.getActor().setWidth(sys.getStageW());
		w.setX(sys.getStageW() + 1);
	  }


	  if (menuReplace || menuHide) {
		w.setY(0);
		w.setHeight(sys.getStageH());
	  } else {
		w.setY(m.getActor().getHeight());
		w.setHeight(sys.getStageH() - m.getActor().getHeight());
	  }

	  m.resized(sys.getStageW());
    }

    private void applyBasics() {

	  w.clearActions();

	  if (menuHide) {
		m.getActor().setY(-m.getActor().getHeight());
	  }
	  w.setWidth(w.getCalcWidth());

	  recalc();

    }

}
