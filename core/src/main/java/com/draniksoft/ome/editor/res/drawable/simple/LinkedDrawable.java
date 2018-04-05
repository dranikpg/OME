package com.draniksoft.ome.editor.res.drawable.simple;

import com.badlogic.gdx.math.Vector2;
import com.cyphercove.gdx.flexbatch.FlexBatch;
import com.draniksoft.ome.editor.manager.ResourceManager;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.LinkedResource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.RootResource;
import com.draniksoft.ome.editor.res.impl.types.ResTypes;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.utils.struct.Pair;

public class LinkedDrawable extends Drawable implements LinkedResource {

    Pair<String, Integer> address;
    Drawable link;

    public LinkedDrawable(Pair<String, Integer> adr) {
	  this.address = adr;
    }

    @Override
    public void draw(FlexBatch b, int x, int y) {
	  if (link != null) link.draw(b, x, y);
    }

    @Override
    public void draw(FlexBatch b, int x, int y, int w, int h) {
	  if (link != null) link.draw(b, x, y, w, h);
    }

    @Override
    public boolean contains(Vector2 p) {
	  if (link == null) return false;
	  return link.contains(p);
    }

    @Override
    public void size(Vector2 v) {
	  if (link != null) link.size(v);
    }

    @Override
    public void setR(RootResource r) {
	  this.link = (Drawable) r;
    }

    @Override
    public Pair<String, Integer> getLink() {
	  return address;
    }

    @Override
    protected void _init() {
	  MainBase.engine.getSystem(ResourceManager.class).register(this, ResTypes.DRAWABLE, address.K(), address.V());
    }


    @Override
    protected void _deinit() {
	  MainBase.engine.getSystem(ResourceManager.class).unregister(ResTypes.DRAWABLE, address.K(), address.V());
    }

    @Override
    protected void _updateUsage(short sum, short delta) {
	  if (link != null && link instanceof RootResource) ((RootResource) link).usg().usage(delta);
    }
}
