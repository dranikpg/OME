package com.draniksoft.ome.editor.texmgmnt.ext;

import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;

import java.util.Iterator;

public abstract class AssetSubExtension extends SubExtension {

    public abstract Iterator<TextureRAccesor> getAll();

    public abstract TextureRAccesor get(String uri);

}

