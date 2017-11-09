package com.draniksoft.ome.editor.ui.core.menu;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class MenuContentSupplierI {


    public static int id;

    protected Menu m;
    protected World w;

    public void init(Menu m, World w) {
        this.m = m;
        this.w = w;
        _init();
    }

    protected abstract void _init();

    public abstract void build(Table t);


}
