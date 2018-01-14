package com.draniksoft.ome.editor.base_gfx.drawable_contructor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.SimpleDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.GdxCompatibleDrawable;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;

public class LeafConstructor extends DwbConstructor {

    private static final String tag = "LeafConstructor";

    LinkedDrawable linkD;

    SimpleDrawable d;

    public LeafConstructor() {
	  linkD = new LinkedDrawable();
	  d = new SimpleDrawable();
    }

    public void setFor(TextureRegion r) {
	  d = new SimpleDrawable();
	  d.r = r;
	  linkD.link = d;
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
    protected void newNode() {
	  node = new EditDwbView.DwbNode(this);
    }

    public void setD(Drawable d) {
	  linkD.link = d;
    }


}
