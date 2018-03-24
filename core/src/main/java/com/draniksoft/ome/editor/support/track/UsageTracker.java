package com.draniksoft.ome.editor.support.track;

public interface UsageTracker {

    int usages();

    int usage(int delta);

}
