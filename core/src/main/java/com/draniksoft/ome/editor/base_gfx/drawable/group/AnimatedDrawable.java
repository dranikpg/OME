package com.draniksoft.ome.editor.base_gfx.drawable.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.EmptyDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.utils.GUtils;

import java.util.Arrays;

public class AnimatedDrawable extends GroupDrawable {

    public Animation<Drawable> a;

    float d = 0;

    public AnimatedDrawable() {

    }

    int lf = -1;

    public AnimatedDrawable(Array<Drawable> dwbA) {
	  if (dwbA.size == 0) dwbA.add(new EmptyDrawable());
	  a = new Animation<Drawable>(0.5f, dwbA);
	  a.setPlayMode(Animation.PlayMode.LOOP);

    }

    @Override
    public void draw(Batch b, float x, float y, float w, float h) {
	  if (lf != GUtils.FRAME) {
		d += Gdx.graphics.getRawDeltaTime();
		lf = GUtils.FRAME;
	  }
	  a.getKeyFrame(d).draw(b, x, y, w, h);
    }



    @Override
    public void update(Array<Drawable> ar) {

	  a = new Animation<Drawable>(a.getFrameDuration(), ar.toArray());
	  a.setPlayMode(Animation.PlayMode.LOOP);

    }

    @Override
    public Drawable copy() {
	  AnimatedDrawable n = new AnimatedDrawable();
	  n.a = new Animation<Drawable>(a.getFrameDuration(), Arrays.copyOf(a.getKeyFrames(), a.getKeyFrames().length));
	  return n;
    }
}
