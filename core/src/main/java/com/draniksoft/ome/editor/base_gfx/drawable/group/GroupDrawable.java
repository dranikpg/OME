package com.draniksoft.ome.editor.base_gfx.drawable.group;

import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;

public interface GroupDrawable {

    // rebuild the dwb with the same params but new sources
    Drawable copy(Array<Drawable> ar);

    // SHOULD NEVER RETURN ITSELF
    Drawable newCopy(Array<Drawable> ar);

}
