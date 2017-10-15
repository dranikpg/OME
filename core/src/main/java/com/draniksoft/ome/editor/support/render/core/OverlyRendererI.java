package com.draniksoft.ome.editor.support.render.core;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface OverlyRendererI {


    void render(int _e, SpriteBatch b, OrthographicCamera c);

    void added(World _w);

    void removed();

    void hidden();

    int[] getPos();

    int getId();


}
