package com.draniksoft.ome.editor.ui;

import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.kotcrab.vis.ui.widget.VisTable;

public class BottomMenu extends VisTable {

    UiSystem uiSys;

    boolean hidden;

    public BottomMenu(UiSystem sys) {
	  this.uiSys = sys;
	  setBackground("d_light_strip_blue");
    }


}


