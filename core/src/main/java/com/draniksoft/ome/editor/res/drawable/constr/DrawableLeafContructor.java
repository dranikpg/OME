package com.draniksoft.ome.editor.res.drawable.constr;

import com.draniksoft.ome.editor.res.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.res.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.LeafConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.utils.FUtills;

public class DrawableLeafContructor extends LeafConstructor<Drawable> {

    private static final String tag = "DrawableLeafContructor";

    transient LinkedDrawable sp;
    transient SimpleDrawable d;

    private transient TextureRAccesor rg;

    private String reg = "";

    public DrawableLeafContructor() {
	  sp = new LinkedDrawable();
    }

    @Override
    public Drawable getSnapshot() {
	  return sp;
    }

    @Override
    public Drawable build() {
	  return new SimpleDrawable(rg);
    }

    @Override
    public ResConstructor<Drawable> copy() {
	  DrawableLeafContructor ct = new DrawableLeafContructor();
	  ct.rg = rg;
	  ct.reg = reg;
	  return ct;
    }

    @Override
    public void updateSources() {
	  if (type() == ResSubT.NULL) setType(ResSubT.SIMPLE);
    }

    @Override
    public void updateType() {
	  d = new SimpleDrawable(rg);
	  sp.link = d;
    }

    public void setFor(String s) {
	  reg = s;
	  setFor(FUtills.getRAC(reg));
    }

    public void setFor(TextureRAccesor r) {
	  rg = r;
	  if (LIVE_MODE && d != null) d.r = r;
    }

    @Override
    protected void init() {
	  super.init();
	  if (reg.length() > 0) {
		rg = FUtills.getRAC(reg);
	  }
    }

    @Override
    protected void extendData() {
	  super.extendData();
	  updateType();
	  updateSources();
    }


    @Override
    protected void shrinkData() {
	  super.shrinkData();
    }
}
