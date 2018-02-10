package com.draniksoft.ome.editor.support.event.projectVals;

import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResTypes;

public class GlobProjectValEvent {


    ResTypes t;

    public GlobProjectValEvent(ResTypes t) {
	  this.t = t;
    }

    public GlobProjectValEvent() {
    }

    public static class Updated extends GlobProjectValEvent {

    }
}
