package com.draniksoft.ome.editor.res.color.typedata;

import com.artemis.World;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.color.ColorProvider;
import com.draniksoft.ome.editor.res.color.group.ColorPAnimation;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.GroupResTypeDataH;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;

public class ColorAnimTD implements ResDataHandler<ColorProvider>, GroupResTypeDataH<ColorProvider> {
    @Override
    public void initL(Array<ColorProvider> ar) {

    }

    @Override
    public void update(Array<ColorProvider> ar) {

    }

    @Override
    public Resource<ColorProvider> build(Array<ColorProvider> ar) {
	  return new ColorPAnimation((ColorProvider[]) ar.toArray(ColorProvider.class));
    }

    @Override
    public ResDataHandler<ColorProvider> asHanlder() {
	  return this;
    }

    @Override
    public void init() {

    }

    @Override
    public void initL(WeakLinkedResource<ColorProvider> link) {

    }

    @Override
    public void deinitL() {

    }

    @Override
    public Resource<ColorProvider> build(World w) {
	  return null;
    }

    @Override
    public ResDataHandler<ColorProvider> copy() {
	  return null;
    }
}
