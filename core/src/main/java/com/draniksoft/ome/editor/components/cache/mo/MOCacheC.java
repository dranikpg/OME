package com.draniksoft.ome.editor.components.cache.mo;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.utils.struct.Pair;

public class MOCacheC extends Component {

    ResConstructor<Drawable> dwbC;

    Array<Pair<Vector2, Vector2>> pathRenderBds;

}
