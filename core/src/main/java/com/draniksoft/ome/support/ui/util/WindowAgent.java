package com.draniksoft.ome.support.ui.util;

import com.draniksoft.ome.support.ui.viewsys.BaseWinView;

public interface WindowAgent {

    // called just before the vw is set to the table and opened
    <T extends BaseWinView> void opened(T vw);

    void notifyClosing();

    void closed();

}
