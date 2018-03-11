package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.utils.FUtills;

public class SimpleDrawable extends Drawable {

    private static final String tag = "SimpleDrawable";

    public TextureRegion r;

    public SimpleDrawable(TextureRegion r) {
	  this.r = r;
    }

    public SimpleDrawable() {
    }

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (r != null) b.draw(r, x, y, w, h);
    }

    public static SimpleDrawable parse(String s) {
	  Gdx.app.error(tag, "LOOKS LIKE OLD CODE !! ");
	  TextureAtlas.AtlasRegion r = FUtills.fetchAtlasR(s);
	  if (r == null) return null;
	  SimpleDrawable d = new SimpleDrawable();
	  d.r = r;
	  return d;
    }

    public Drawable copy() {
	  SimpleDrawable n = new SimpleDrawable();
	  n.r = r;
	  return n;
    }

}
