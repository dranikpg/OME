package com.draniksoft.ome.editor.ui.utils;

public interface WinResponseListener {

    short closed = 1;
    short submitted = 2;
    short _ = 3;


    void rsp(short code, Object info);


}
