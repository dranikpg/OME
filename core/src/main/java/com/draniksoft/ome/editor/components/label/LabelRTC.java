package com.draniksoft.ome.editor.components.label;

import com.artemis.Component;
import com.artemis.annotations.Transient;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

@Transient
public class LabelRTC extends Component {

    public BitmapFontCache c;
    public Color color;

    public float w;
    public float h;
}
