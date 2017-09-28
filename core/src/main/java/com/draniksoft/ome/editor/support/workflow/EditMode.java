package com.draniksoft.ome.editor.support.workflow;

import com.artemis.World;

public interface EditMode {

    void attached(World _w);

    void run();

    void detached();

    void stopped();

    void resumed();


}
