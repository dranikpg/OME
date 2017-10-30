package com.draniksoft.ome.utils;


import com.draniksoft.ome.mgmnt_base.base.AppDO;

/**
 *
 * Utils for code shortening
 *
 */
public class SUtils {

    public static String getS(String k) {
        return AppDO.I.L().get(k);
    }

}
