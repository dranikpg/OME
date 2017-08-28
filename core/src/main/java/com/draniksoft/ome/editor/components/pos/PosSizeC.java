package com.draniksoft.ome.editor.components.pos;

import com.artemis.PooledComponent;

public class PosSizeC extends PooledComponent {

    public int x,y;
    public int w,h;

    @Override
    protected void reset() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
    }
}
