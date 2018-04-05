package com.draniksoft.ome.editor.res.drawable.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GdxCompatibleDrawable implements Drawable {

    private static final String tag = "GdxCompatibleDrawable";

    public com.draniksoft.ome.editor.res.drawable.Drawable d;

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
	  //if (d != null) d.draw(batch, x, y, width, height);
    }

    float mw = 0;
    float mh = 0;

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
	  return mw;
    }

    @Override
    public void setMinWidth(float minWidth) {
	  mw = minWidth;
    }

    @Override
    public float getMinHeight() {
	  return mh;
    }

    @Override
    public void setMinHeight(float minHeight) {
	  mh = minHeight;
    }

    public static GdxCompatibleDrawable from(com.draniksoft.ome.editor.res.drawable.Drawable d) {
	  GdxCompatibleDrawable gd = new GdxCompatibleDrawable();
	  gd.d = d;
	  return gd;
    }
}
