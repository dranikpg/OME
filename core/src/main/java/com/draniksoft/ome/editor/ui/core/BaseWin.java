package com.draniksoft.ome.editor.ui.core;

import com.artemis.World;
import com.kotcrab.vis.ui.widget.VisTable;

public abstract class BaseWin extends VisTable {

    boolean open = false;
    protected World _w;

    public int code;
    public String name;
    
    public void init(World w) {
        this._w = w;
        _init();

    }

    public abstract void _init();

    public abstract void open(String args, boolean delay);

    public abstract void close();

    public abstract void updateBounds(float gw, float gh);

    public abstract void immdClose();


}
