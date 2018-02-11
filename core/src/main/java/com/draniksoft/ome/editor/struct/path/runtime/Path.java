package com.draniksoft.ome.editor.struct.path.runtime;

import com.draniksoft.ome.utils.struct.Points;

public class Path {

    // cached points
    public Points pts;

    // start and end time
    int st, et;

    // lookup table to translate percentages
    public TranslationTable tb;

}
