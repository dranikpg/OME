package com.draniksoft.ome.editor.components.tps;

import com.artemis.PooledComponent;

public class MapC extends PooledComponent {

    public int id;

    @Override
    protected void reset() {
        id = 0;
    }
}
