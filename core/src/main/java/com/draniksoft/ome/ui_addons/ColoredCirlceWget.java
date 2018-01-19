package com.draniksoft.ome.ui_addons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.draniksoft.ome.editor.base_gfx.color.simple.LinkColor;
import com.draniksoft.ome.editor.base_gfx.color.utils.ColorProvider;
import com.draniksoft.ome.editor.base_gfx.color.utils.RootColorProvider;
import com.kotcrab.vis.ui.VisUI;

public class ColoredCirlceWget extends Actor implements Layout {

    Drawable d;
    ColorProvider c;

    Color tmpC;

    public ColoredCirlceWget() {
	  d = VisUI.getSkin().getDrawable("c");
	  tmpC = new Color();
    }

    public ColoredCirlceWget(RootColorProvider c) {
	  this();
	  this.c = c;
    }

    public void setC(LinkColor c) {
	  this.c = c;
    }

    @Override
    public void act(float delta) {
	  super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
	  super.draw(batch, parentAlpha);

	  Color bc = batch.getColor();

	  c.get(tmpC);
	  tmpC.a = Math.min(parentAlpha, tmpC.a);
	  batch.setColor(tmpC);

	  d.draw(batch, getX(), getY(), getWidth(), getHeight());

	  batch.setColor(bc);
    }

    @Override
    public void layout() {
	  float w = getWidth();
	  float h = getHeight();
	  setSize(Math.min(w, h), Math.min(w, h));
    }

    @Override
    public void invalidate() {

    }

    @Override
    public void invalidateHierarchy() {

    }

    @Override
    public void validate() {

    }

    @Override
    public void pack() {

    }

    @Override
    public void setFillParent(boolean fillParent) {

    }

    @Override
    public void setLayoutEnabled(boolean enabled) {

    }

    @Override
    public float getMinWidth() {
	  return 0;
    }

    @Override
    public float getMinHeight() {
	  return 0;
    }

    @Override
    public float getPrefWidth() {
	  return 30;
    }

    @Override
    public float getPrefHeight() {
	  return 30;
    }

    @Override
    public float getMaxWidth() {
	  return 500;
    }

    @Override
    public float getMaxHeight() {
	  return 500;
    }
}
