package com.draniksoft.ome.editor.ui.edit.dwb_typevw;

import com.draniksoft.ome.editor.base_gfx.drawable_contructor.DwbConstructor;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;

public interface DwbEditI {

    void setFor(DwbConstructor c, EditDwbView.ActionHandler h);

    String getID();

}
