package com.draniksoft.ome.editor.support.render.core;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface OverlayRendererI {

    class IDs {

        public static final int SelR = 1;

	  public static final int MoveMO = 3;
	  public static final int NewMOR = 4;

	  public static final int PathR = 7;
    }

    void render(SpriteBatch b, OrthographicCamera c);

    void render(ShapeRenderer r, OrthographicCamera c);

    void added(World _w);

    int[] getPos();

    int getId();


}
