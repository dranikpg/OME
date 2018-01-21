package com.draniksoft.ome.editor.base_gfx.drawable.constr;

import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res_mgmnt.constructor.LeafConstructor;
import com.draniksoft.ome.editor.res_mgmnt.t.ResSubT;

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
	  d = (SimpleDrawable) d.copy();
	  sp.link = d;
    }

    public void setFor(TextureRegion r) {
	  d.r = r;
    }

    @Override
    public void parseFrom(JsonValue v, World _w) {

    }

    @Override
    public void serializeTo(JsonValue v) {

    }
}
