package com.draniksoft.ome.editor.res.drawable.typedata;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.drawable.Drawable;
import com.draniksoft.ome.editor.res.drawable.group.StackDrawable;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.GroupResTypeDataH;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;

public class StackDwbTD implements ResDataHandler<Drawable>, GroupResTypeDataH<Drawable> {
    @Override
    public void init() {

    }

    @Override
    public void initL(WeakLinkedResource link) {

    }

    @Override
    public void deinitL() {

    }

    @Override
    public Resource build(World w) {
	  return null;
    }

    @Override
    public ResDataHandler copy() {
	  return null;
    }

    @Override
    public void initL(Array<Drawable> ar) {

    }

    @Override
    public void update(Array<Drawable> ar) {

    }

    @Override
    public Resource<Drawable> build(Array<Drawable> ar) {
	  StackDrawable dwb = new StackDrawable(ar);
	  return dwb;
    }

    @Override
    public ResDataHandler<Drawable> asHanlder() {
	  return this;
    }
}
