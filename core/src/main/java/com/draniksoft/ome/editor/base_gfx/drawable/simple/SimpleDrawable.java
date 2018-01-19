package com.draniksoft.ome.editor.base_gfx.drawable.simple;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.utils.FUtills;

public class SimpleDrawable implements Drawable {

    private static final String tag = "SimpleDrawable";

    public TextureRegion r;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  b.draw(r, x, y, w, h);
    }

    public static SimpleDrawable parse(String s) {
	  TextureAtlas.AtlasRegion r = FUtills.fetchAtlasR(s);
	  if (r == null) return null;
	  SimpleDrawable d = new SimpleDrawable();
	  d.r = r;
	  return d;
    }

    public static String serialize(SimpleDrawable d) {
	  return null;
    }

    public Drawable copy() {
	  return null;
    }

    @Override
    public void destruct() {

    }
}
