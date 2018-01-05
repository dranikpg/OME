package com.draniksoft.ome.editor.support.compositionObserver;

import com.artemis.Aspect;
import com.badlogic.gdx.utils.IntMap;
import com.draniksoft.ome.editor.components.label.LabelC;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.actions.label.CreateLabelA;
import com.draniksoft.ome.editor.support.actions.label.SetTextAction;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.SimpleCompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.systems.support.ActionSystem;

import static com.draniksoft.ome.editor.support.compositionObserver.LabelCompositionObserver.ActionID.*;

public class LabelCompositionObserver extends SimpleCompositionObserver {

    public static final class ActionID {
	  public static final int CREATE = ActionDesc.BaseCodes.ACTION_CREATE;
	  public static final int DELETE = ActionDesc.BaseCodes.ACTION_CREATE;
	  public static final int RESET = ActionDesc.BaseCodes.ACTION_RESET;
	  public static final int CREATE_VW = ActionDesc.BaseCodes.ACTION_EDITVW_CREATE;

	  public static final int SET_TEXT = 11;
	  public static final int SET_COLOR = 12;

	  public static final int REFRESH = 13;

    }

    IntMap<ActionDesc> desc;

    @Override
    protected void on_selchanged(boolean previousActivity, int previd) {

    }

    @Override
    protected void on_selCompChanhed(boolean previousActivity) {

    }

    @Override
    protected void _init() {
	  desc = new IntMap<ActionDesc>();


	  if (__ds == null) return;
	  for (ActionDesc d : __ds) {
		desc.put(d.code, d);
	  }
    }

    @Override
    protected Aspect.Builder getAspectB() {
	  return Aspect.all(LabelC.class);
    }

    @Override
    public IntMap<ActionDesc> getDesc() {
	  return desc;
    }

    @Override
    public boolean isAviab(int ac, int e) {
	  if (ac == CREATE || ac == CREATE_VW) {
		return !matches(e);
	  } else {
		return matches(e);
	  }
    }

    @Override
    public boolean isAviab(int ac) {
	  return isAviab(ac, _selE);
    }

    @Override
    public ActionDesc getDesc(int ac) {
	  return desc.get(ac);
    }

    @Override
    public void execA(int id, int _e, boolean aT, Object... os) {
	  if (id == CREATE) {
		CreateLabelA a = new CreateLabelA();
		a._e = _e;
		a.baseT = "LEL";
		execA(a, aT);
	  } else if (id == SET_TEXT) {
		SetTextAction a = new SetTextAction();
		a._e = _e;
		a.newT = "Changed";
	  }

    }

    private void execA(Action a, boolean aT) {
	  if (aT) {
		_w.getSystem(ActionSystem.class).exec(a);
	  } else {
		a.invoke(_w);
	  }
    }

    @Override
    public String getName() {
	  return "Label";
    }

    @Override
    public boolean isViewAv(short id) {
	  return false;
    }

    @Override
    public String getViewID(short id) {
	  return null;
    }
}
