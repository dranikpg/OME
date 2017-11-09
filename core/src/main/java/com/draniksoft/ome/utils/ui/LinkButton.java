package com.draniksoft.ome.utils.ui;

import com.kotcrab.vis.ui.widget.LinkLabel;

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

    }

    public abstract void on_press();


}
