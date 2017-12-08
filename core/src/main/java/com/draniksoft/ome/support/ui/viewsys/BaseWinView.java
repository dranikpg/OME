package com.draniksoft.ome.support.ui.viewsys;

import com.badlogic.gdx.math.MathUtils;
import com.draniksoft.ome.editor.ui.WinControllerOverlay;

public abstract class BaseWinView extends BaseView {


    boolean keepOld;
    boolean percentMode;
    float pct;
    float w;

    boolean pctClamp;
    float minP;
    float maxP;
    float minW;
    float maxW;

    boolean hideM;
    boolean replaceB;

    public static class WinDao {

	  public boolean pctMode = false;

	  public float pct = 0f;
	  public int w = 0;

	  public boolean pctClamp = false;

	  public float minp = 0;
	  public float maxp = 1;

	  public float minW = 0;
	  public float maxW = Float.MAX_VALUE;

	  public boolean hideM = false;
	  public boolean replaceM = false;

	  public boolean keepold = false;

    }

    public void consumeConfig(WinDao d) {
	  percentMode = d.pctMode;
	  pct = d.pct;
	  w = d.w;
	  maxW = d.maxW;
	  minW = d.minW;
	  minP = d.minp;
	  maxP = d.maxp;
	  pctClamp = d.pctClamp;
	  hideM = d.hideM;
	  replaceB = d.replaceM;
    }

    public void init(WinControllerOverlay c) {
	  c.setMenuHide(hideM);
	  c.setMenuReplace(replaceB);
    }

    public void calc(WinControllerOverlay c) {

	  float _w = 0;
	  float _pct = 0;

	  if (keepOld) {
		_w = c.getCW();
		_pct = c.getWW() / _w;
	  } else if (percentMode) {
		_w = c.getWW() * pct;
		_pct = pct;
	  } else {
		_pct = w / c.getWW();
		_w = w;
	  }


	  _w = MathUtils.clamp(_w, minW, maxW);

	  c.setCalcW(_w);

    }


}
