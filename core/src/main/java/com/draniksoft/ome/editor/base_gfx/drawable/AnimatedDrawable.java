package com.draniksoft.ome.editor.base_gfx.drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

public class AnimatedDrawable implements Drawable {

    Animation<Drawable> a;

    float d = 0;

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  d += Gdx.graphics.getRawDeltaTime();
	  a.getKeyFrame(d).draw(b, x, y, w, h);
    }

    @Override
    public Drawable copy() {
	  return null;
    }

    @Override
    public String serialize() {
	  return null;
    }


}
