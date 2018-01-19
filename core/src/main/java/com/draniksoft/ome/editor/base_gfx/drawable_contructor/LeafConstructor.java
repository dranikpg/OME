package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;
import com.draniksoft.ome.utils.JsonUtils;

public class LeafConstructor extends DwbConstructor {

    private static final String tag = "LeafConstructor";

    LinkedDrawable linkD;

    SimpleDrawable d;
    TextureRegion r;

    public LeafConstructor() {
	  linkD = new LinkedDrawable();
	  d = new SimpleDrawable();
    }

    public void setFor(TextureRegion r) {
	  d = new SimpleDrawable();
	  this.r = r;
	  d.r = r;
	  linkD.link = d;
    }

    public TextureRegion getR() {
	  return r;
    }

    @Override
    public GdxCompatibleDrawable getGdxSnapshot() {
	  return GdxCompatibleDrawable.from(linkD);
    }

    @Override
    public Drawable getSnapshot() {
	  return linkD;
    }

    @Override
    public void updateSources() {

    }

    @Override
    public Drawable construct() {
	  return d;
    }

    @Override
    public void putData(JsonValue v) {
	  v.addChild(JsonUtils.createIntV(1));
    }

    @Override
    public void fetchData(JsonValue v) {

    }

    @Override
    protected void newNode() {
	  node = new EditDwbView.DwbNode(this);
    }

    public void setD(Drawable d) {
	  linkD.link = d;
    }

    @Override
    public void destruct() {

    }
}
