package com.draniksoft.ome.editor.base_gfx.color;

import com.badlogic.gdx.graphics.Color;

public class LinkColor implements ColorProvider {

    public ColorProvider pv;

    public int id = -1;


    @Override
    public void get(Color c) {
        pv.get(c);
    }
}
