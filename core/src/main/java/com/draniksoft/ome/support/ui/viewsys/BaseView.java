package com.draniksoft.ome.support.ui.viewsys;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.struct.Pair;
import com.github.czyzby.lml.parser.LmlParser;

public abstract class BaseView {

    public String ID;
    public String lang;
    public boolean active = false;

    Array<Pair<String, String>> inclds;
    Array<BaseView> rtIncs;

    BaseView parent = null;

    public BaseView() {
	  inclds = new Array<Pair<String, String>>();
	  rtIncs = new Array<BaseView>();
    }


    /*
    Kept for compatibility reasons here, is null in a non world env
    */

    protected World _w;

    public void injectW(World w) {
	  this._w = w;
    }

    public abstract Actor getActor();

    public abstract void preinit();

    public abstract void postinit();

    //

    public void opened() {
	  active = true;
	  AppDO.I.LML().injectIncludes(this);
    }

    public void closed() {
	  clearInjectedIncludes();
	  active = false;
    }

    public void clearInjectedIncludes() {
	  for (BaseView v : rtIncs) {
		v.closed();
	  }
	  rtIncs.clear();
    }

    //

    public void setParent(BaseView vw) {
	  parent = vw;
    }

    public BaseView getParent() {
	  return parent;
    }

    public void clearParent() {
	  parent = null;
    }

    //

    public void clearInclds() {
	  inclds.clear();
    }

    public void removeIncldbName(String name) {
	  for (int i = 0; i < inclds.size; i++) {
		if (inclds.get(i).getElement0().equals(name)) {
		    inclds.removeIndex(i);
		}
	  }
    }

    public void removeIncldbVID(String id) {
	  for (int i = 0; i < inclds.size; i++) {
		if (inclds.get(i).getElement1().equals(id)) {
		    inclds.removeIndex(i);
		}
	  }
    }

    public void addIncld(String name, String id) {
	  inclds.add(Pair.createPair(name, id));
    }

    public Array<Pair<String, String>> getInclds() {
	  return inclds;
    }


    protected void handleInclude(String nm, BaseView vw) {
	  if (!active) return;
	  vw.addedAsInclude(vw);
	  vw.opened();
	  rtIncs.add(vw);
    }

    public void obtainIncld(String name, BaseView vw) {
	  handleInclude(name, vw);
    }


    public void addedAsInclude(BaseView p) {
	  parent = p;
    }

    //

    public void clearData() {
	  if (active) return;
	  parent = null;
	  active = false;
	  inclds.clear();
	  rtIncs.clear();
    }

    public void prepareParser(LmlParser p) {
    }

    public void clearParser(LmlParser p) {
    }

}
