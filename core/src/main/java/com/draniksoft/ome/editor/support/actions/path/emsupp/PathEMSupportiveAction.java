package com.draniksoft.ome.editor.support.actions.path.emsupp;

import com.artemis.World;
import com.draniksoft.ome.editor.components.path.PathDescC;
import com.draniksoft.ome.editor.components.path.PathRunTimeC;
import com.draniksoft.ome.editor.support.actions.Action;

public interface PathEMSupportiveAction {

    Action self();

    void ifor(int e);

    void prepocess(World w, PathDescC descC, PathRunTimeC rtC);

    void discard(World w, PathDescC descC, PathRunTimeC rtC);

}
