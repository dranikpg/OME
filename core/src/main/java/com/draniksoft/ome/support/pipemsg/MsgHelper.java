package com.draniksoft.ome.support.pipemsg;

public class MsgHelper {
    public static void init(MsgNode n) {
	  n.msg(MsgBaseCodes.INIT, MsgDirection.DOWN, null);
    }
    public static void deinit(MsgNode n) {
	  n.msg(MsgBaseCodes.DEINIT, MsgDirection.DOWN, null);
    }
}
