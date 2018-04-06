package com.draniksoft.ome.editor.res.color.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.utils.FM;

public class ColorPAnimation extends ColorProvider {
    Animation<ColorProvider> a;
    int LF = -1;

    float KF = 0;

    public ColorPAnimation(ColorProvider[] pv) {
	  a = new Animation<ColorProvider>(MathUtils.random(0.5f, 3f), pv);
	  a.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void updateAnim() {
	  LF = FM.FRAME;
	  KF += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void getC(Color c) {
	  if (LF < FM.FRAME) {
		updateAnim();
	  }
	  a.getKeyFrame(KF).getC(c);
    }

    @Override
    public Color getC() {
	  if (LF < FM.FRAME) {
		updateAnim();
	  }
	  return a.getKeyFrame(KF).getC();
    }
}
