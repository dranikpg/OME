package com.draniksoft.ome.menu.dynamic_vs;

import com.kotcrab.vis.ui.widget.VisTable;

public abstract class DynamicView extends VisTable {

   public abstract void init();

   public abstract void open(String args);

   public abstract void on_close();

   public abstract void on_refresh_req();

}
