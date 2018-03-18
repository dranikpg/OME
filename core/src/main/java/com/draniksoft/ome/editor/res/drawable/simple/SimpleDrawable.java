package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.utils.FUtills;

public class SimpleDrawable extends Drawable {

    private static final String tag = "SimpleDrawable";

    public TextureRAccesor r;

    public SimpleDrawable(TextureRAccesor r) {
	  this.r = r;
    }

    public SimpleDrawable() {
    }

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (r != null) b.draw(r.atl(), x, y, w, h);
    }

    public static SimpleDrawable parse(String s) {
	  Gdx.app.error(tag, "LOOKS LIKE OLD CODE !! ");
	  SimpleDrawable d = new SimpleDrawable();
	  return d;
    }

    public Drawable copy() {
	  SimpleDrawable n = new SimpleDrawable();
	  n.r = r;
	  return n;
    }

    @Override
    protected void _updateUsage(short sum, short delta) {
	  if (r != null) FUtills.updateUsage(r, delta);
    }
}
