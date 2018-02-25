package com.draniksoft.ome.editor.res.drawable.constr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.draniksoft.ome.editor.res.drawable.group.AnimatedDrawable;
import com.draniksoft.ome.editor.res.drawable.group.GroupDrawable;
import com.draniksoft.ome.editor.res.drawable.group.StackDrawable;
import com.draniksoft.ome.editor.res.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.res.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.GroupResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.constructor.ResConstructor;
import com.draniksoft.ome.editor.res.res_mgmnt_base.types.ResSubT;

public class DrawableGroupConstructor extends GroupResConstructor<Drawable> {

    private static String tag = "DrawableGroupConstructor";

    transient LinkedDrawable sp;
    transient GroupDrawable dwb;

    public DrawableGroupConstructor() {
	  sp = new LinkedDrawable();
    }

    @Override
    public Drawable getSnapshot() {
	  return sp;
    }

    @Override
    public Drawable build() {

	  Array<Drawable> bds = new Array<Drawable>(getAr().size);

	  for (ResConstructor<Drawable> ch : getAr()) bds.add(ch.build());

	  GroupDrawable build = (GroupDrawable) dwb.copy();

	  build.update(bds);

	  return build;
    }

    @Override
    public void updateSources() {

	  if (type() == ResSubT.NULL) setType(ResSubT.STACK);

	  Array<Drawable> snaps = new Array<Drawable>(getAr().size);

	  for (ResConstructor<Drawable> ch : getAr()) snaps.add(ch.getSnapshot());

	  Gdx.app.debug(tag, "Source update " + snaps.size);

	  dwb.update(snaps);


    }

    @Override
    public void updateType() {

	  Array<Drawable> snaps = new Array<Drawable>(getAr().size);
	  for (ResConstructor<Drawable> ch : getAr()) snaps.add(ch.getSnapshot());

	  if (type() == ResSubT.STACK) {

		dwb = new StackDrawable(snaps);

	  } else {

		dwb = new AnimatedDrawable(snaps);

	  }

	  sp.link = dwb;

    }

    @Override
    protected void extendData() {
	  super.extendData();
	  updateSources();
    }

    @Override
    protected void shrinkData() {
	  super.shrinkData();
    }

    @Override
    protected void init() {
	  super.init();
    }
}
