package com.draniksoft.ome.support.pipemsg;

/*
	UP-DOWN tree messaging system for ui, resources, resource managers and co
	Replaces customized methods.
 */
public interface MsgNode {

    void msg(byte msg, byte dir, byte[] data);

}
