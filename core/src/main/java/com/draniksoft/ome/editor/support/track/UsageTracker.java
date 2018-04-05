package com.draniksoft.ome.editor.support.track;

public interface UsageTracker {

    short usages();

    short usage(short delta);

}
