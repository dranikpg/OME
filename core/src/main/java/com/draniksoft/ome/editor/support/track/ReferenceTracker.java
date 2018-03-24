package com.draniksoft.ome.editor.support.track;

public interface ReferenceTracker {

    int references();

    int reference(int delta);

}
