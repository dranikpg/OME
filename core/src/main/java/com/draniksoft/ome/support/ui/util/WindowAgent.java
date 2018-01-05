package com.draniksoft.ome.support.ui.util;

import com.draniksoft.ome.support.ui.viewsys.BaseWinView;

public interface WindowAgent {

    // called just before the vw is set to the table and opened
    void opened(BaseWinView vw);

    void notifyClosing();

    void closed();

}
