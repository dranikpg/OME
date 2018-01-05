package com.draniksoft.ome.utils.ui.wgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.draniksoft.ome.editor.base_gfx.drawable.Drawable;

public class DrawableActor extends Actor {

    public Drawable d;

    @Override
    public void draw(Batch batch, float parentAlpha) {

	  if (d != null) d.draw(batch, getX(), getY(), getHeight(), getHeight());

    }


}
