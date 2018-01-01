package com.draniksoft.ome.support.ui.viewsys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.utils.ui.editorMenu.WinControllerOverlay;


public abstract class BaseWinView extends BaseView {

    public boolean WINMODE = false;

    private static final String tag = "BaseWinView";

    boolean keepOld;

    float w;

    float max = 1f;
    float min = 0f;

    boolean hideM;
    boolean replaceB;

    boolean dynamic;

    boolean scrollX = true;
    boolean scrollY = true;

    public static class WinDao {

	  public float w = 0;

	  public float max = 1f;
	  public float min = 0f;

	  public boolean hideM = false;
	  public boolean replaceM = false;

	  public boolean dynamic = false;

	  public boolean scrollX = false;
	  public boolean scrollY = true;

    }

    public final void consumeConfig(WinDao d) {
	  w = d.w;
	  max = d.max;
	  min = d.min;
	  hideM = d.hideM;
	  replaceB = d.replaceM;
	  dynamic = d.dynamic;
	  scrollX = d.scrollX;
	  scrollY = d.scrollY;
    }

    public void init(WinControllerOverlay c) {
	  c.setMenuHide(hideM);
	  c.setMenuReplace(replaceB);
    }

    public void closeWin() {
	  _w.getSystem(UiSystem.class).closeWin();
    }

    public void recalc() {
	  if (WINMODE) _w.getSystem(UiSystem.class).validateLayout();
    }

    public void calc(WinControllerOverlay c) {

	  float ww = c.getWW();
	  float _w = 0;

	  if (dynamic) {
		if (getActor() instanceof Layout) {
		    _w = ((Layout) getActor()).getPrefWidth();
		} else {
		    _w = getActor().getWidth();
		}
	  } else {
		float _pct = 0;

		if (keepOld) {
		    _w = c.getCW();
		    _pct = c.getWW() / _w;
		} else if (w < 2f) {
		    _w = c.getWW() * w / 100f;
		    _pct = w;
		} else {
		    _pct = w / c.getWW();
		    _w = w;
		}
	  }
	  Gdx.app.debug(tag, "Vals " + min + " " + max + " ww " + ww);

	  float _minw = min; if (min < 2f) _minw = ww * min;
	  float _maxw = max; if (max < 2f) _maxw = ww * max;

	  Gdx.app.debug(tag, "Setting on " + _w + " min " + _minw + " max " + _maxw);

	  _w = MathUtils.clamp(_w, _minw, _maxw);

	  Gdx.app.debug(tag, "Final w is " + _w);

	  c.setCalcW(_w);
	  c.setScroll(scrollX, scrollY);

    }


}
