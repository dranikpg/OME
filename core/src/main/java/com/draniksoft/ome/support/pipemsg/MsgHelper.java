package com.draniksoft.ome.support.pipemsg;

import com.draniksoft.ome.utils.FUtills;

public class MsgHelper {

    public static void init(MsgNode n) {
	  n.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, FUtills.NULL_ARRAY);
    }

    public static void deinit(MsgNode n) {
	  n.msg(MsgBaseCodes.DEINIT, MsgDirection.DOWN, FUtills.NULL_ARRAY);
    }

}
