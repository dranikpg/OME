package com.draniksoft.ome.editor.base_gfx.drawable.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;

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
    public void serialize(JsonValue value) {
	  return;
    }


}
