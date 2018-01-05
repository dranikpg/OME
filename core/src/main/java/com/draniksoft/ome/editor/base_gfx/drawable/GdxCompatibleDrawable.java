package com.draniksoft.ome.editor.base_gfx.drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GdxCompatibleDrawable implements Drawable {

    private static final String tag = "GdxCompatibleDrawable";

    public com.draniksoft.ome.editor.base_gfx.drawable.Drawable d;

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
	  Gdx.app.debug(tag, "Drawing " + width + " " + height);
	  if (d != null) d.draw(batch, x, y, width, height);
    }

    @Override
    public float getLeftWidth() {
	  return 0;
    }

    @Override
    public void setLeftWidth(float leftWidth) {

    }

    @Override
    public float getRightWidth() {
	  return 0;
    }

    @Override
    public void setRightWidth(float rightWidth) {

    }

    @Override
    public float getTopHeight() {
	  return 0;
    }

    @Override
    public void setTopHeight(float topHeight) {

    }

    @Override
    public float getBottomHeight() {
	  return 0;
    }

    @Override
    public void setBottomHeight(float bottomHeight) {

    }

    @Override
    public float getMinWidth() {
	  return 0;
    }

    @Override
    public void setMinWidth(float minWidth) {

    }

    @Override
    public float getMinHeight() {
	  return 0;
    }

    @Override
    public void setMinHeight(float minHeight) {

    }

    public static GdxCompatibleDrawable from(com.draniksoft.ome.editor.base_gfx.drawable.Drawable d) {
	  GdxCompatibleDrawable gd = new GdxCompatibleDrawable();
	  gd.d = d;
	  return gd;
    }
}
