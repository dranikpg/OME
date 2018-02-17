package com.draniksoft.ome.support.pipemsg;

public class MsgDirection {

    public static final byte END = -1;

    public static final byte UP = 1;
    public static final byte DOWN = 2;
    public static final byte UP_TILL_JUNCTION = 3;
    public static final byte DOWN_TILL_JUNCTION = 4;
    public static final byte UP_TILL_COND = 5;
    public static final byte DOWN_TILL_COND = 6;

    // transmission line
    public static final byte UP_END = 20;

    public static final byte UP_ONE = UP_END + 1;
    public static final byte UP_TWO = UP_END + 2;
    public static final byte UP_THREE = UP_END + 3;

    public static final byte DOWN_END = 40;

    public static final byte DOWN_ONE = DOWN_END + 1;

}
