package com.draniksoft.ome.editor.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TexRegC extends PooledComponent {

    public TextureRegion d;

    @Override
    protected void reset() {
        d = null;
    }
}
