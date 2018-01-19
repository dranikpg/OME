package com.draniksoft.ome.editor.base_gfx.color.simple;

import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.base_gfx.color.utils.ColorProvider;

public class LinkColor implements ColorProvider {

    public ColorProvider pv;

    public int id = -1;


    @Override
    public void get(Color c) {
        pv.get(c);
    }
}
