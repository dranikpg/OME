package com.draniksoft.ome.utils;


import com.draniksoft.ome.mgmnt_base.AppDataObserver;

/**
 *
 * Utils for code shortening
 *
 */
public class SUtils {

    public static String f_m(String k, Object... a){
        return AppDataObserver.getI().L().format_m(k,a);
    }

}
