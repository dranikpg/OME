package com.draniksoft.ome.editor.ui.edit.dwb_typevw;

import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res_mgmnt.constructor.ResConstructor;
import com.draniksoft.ome.editor.ui.edit.EditDwbView;

public interface DwbEditI {

    void setFor(ResConstructor<Drawable> c, EditDwbView.Handler h);

    void typeUpdated();

    String getID();

}
