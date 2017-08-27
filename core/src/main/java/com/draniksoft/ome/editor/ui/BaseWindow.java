package com.draniksoft.ome.editor.ui;

import com.kotcrab.vis.ui.widget.VisWindow;

public abstract class BaseWindow extends VisWindow {

    public BaseWindow(String title) {
        super(title);
    }


    public abstract void init();

    public abstract void open(String uri);

    public abstract void close();

    public abstract boolean isOpen();

}
