package com.draniksoft.ome.ui_addons;

import com.kotcrab.vis.ui.widget.LinkLabel;
import com.kotcrab.vis.ui.widget.VisLabel;

public abstract class LinkButton extends LinkLabel {

    public LinkButton(CharSequence url, String style) {
        super(url, url, style);
    }

    public LinkButton(CharSequence url) {
        super(url);

        setListener(new LinkLabelListener() {
            @Override
            public void clicked(String url) {
                on_press();
            }
        });

	  VisLabel l;
    }

    public abstract void on_press();


}
