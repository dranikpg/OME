package com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces;

import com.draniksoft.ome.support.pipemsg.MsgDirection;
import com.draniksoft.ome.support.pipemsg.MsgNode;

import static com.draniksoft.ome.editor.res.res_mgmnt_base.res_ifaces.Resource.MSG.USAGE_CHANGE;

public interface Resource<TYPE> {

    /*
        100 - 110
     */
    class MSG {

        // data : [delta, uses]
        public static final byte USAGE_CHANGE = 101;
    }

    TYPE self();

    TYPE copy();

    Resource<TYPE> parent();

    void parent(Resource<TYPE> p);


    class MsgHelper {
        // should be capped by root drawable
        public static void updateUsage(MsgNode n, byte delta) {
            n.msg(USAGE_CHANGE, MsgDirection.DOWN, new short[]{delta, 0});
        }
    }


}
