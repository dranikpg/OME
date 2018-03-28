package com.draniksoft.ome.support.pipemsg;

/*
	UP-DOWN tree messaging system for ui, resources, resource managers and co
	Replaces customized method invocation chains.
 */
public interface MsgNode {

    void msg(short msg, byte dir, Object data);

}
