package com.draniksoft.ome.editor.res.drawable.constr;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.res.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.res.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.LeafConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;

public class DrawableLeafContructor extends LeafConstructor<Drawable> {

    private static final String tag = "DrawableLeafContructor";

    LinkedDrawable sp;
    SimpleDrawable d;

    public DrawableLeafContructor() {
	  sp = new LinkedDrawable();
    }

    @Override
    public Drawable getSnapshot() {
	  return sp;
    }

    @Override
    public Drawable build() {
	  return d.copy();
    }

    @Override
    public void updateSources() {
	  if (type() == ResSubT.NULL) setType(ResSubT.SIMPLE);
    }

    @Override
    public void typeUpdate() {
	  if (d == null) d = new SimpleDrawable();
	  else d = (SimpleDrawable) d.copy();
	  sp.link = d;
    }

    public void setFor(TextureRegion r) {
	  d.r = r;
    }


}
