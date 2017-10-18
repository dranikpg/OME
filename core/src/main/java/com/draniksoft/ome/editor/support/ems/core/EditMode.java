package com.draniksoft.ome.editor.support.ems.core;

import com.artemis.World;

public interface EditMode {

    void attached(World _w);

    void update();

    void detached();



}
