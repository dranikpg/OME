package com.draniksoft.ome.editor.texmgmnt.ext.b;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.extensions.sub.SubExtension;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;

import java.util.Iterator;

public abstract class AssetSubExtension extends SubExtension {

    private static final String tag = "AssetSubExtension";

    protected ObjectMap<String, TextureRAccesor> map;

    AssetSubExtension prev;

    public AssetSubExtension() {
        map = new ObjectMap<String, TextureRAccesor>();
    }

    public Iterator<TextureRAccesor> getAll() {
        return map.values().iterator();
    }

    public ObjectMap<String, TextureRAccesor> getMap() {
        return map;
    }

    public abstract TextureRAccesor get(String uri);

    //

    protected void indexPrev() {
        if (prev == null) return;
        ObjectMap<String, TextureRAccesor> impM = prev.getMap();
        map.putAll(impM);
        if (impM.size > 0) {
            Gdx.app.debug(tag, "Picked up unresolved stuff");
        }
        prev = null;
    }

}

