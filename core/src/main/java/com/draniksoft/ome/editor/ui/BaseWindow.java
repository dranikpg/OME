package com.draniksoft.ome.editor.ui;

import com.artemis.World;
import com.kotcrab.vis.ui.widget.VisWindow;

public abstract class BaseWindow extends VisWindow {

    public int code;

    public BaseWindow(String title) {
        super(title);
    }

    public abstract void init(World w);

    public abstract void open(String uri);

    public abstract void close();

    public abstract boolean isOpen();

}
