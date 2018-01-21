package com.draniksoft.ome.editor.base_gfx.drawable.constr;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.base_gfx.drawable.group.AnimatedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.group.GroupDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.group.StackDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.simple.LinkedDrawable;
import com.draniksoft.ome.editor.base_gfx.drawable.utils.Drawable;
import com.draniksoft.ome.editor.res_mgmnt.constructor.GroupResConstructor;
import com.draniksoft.ome.editor.res_mgmnt.constructor.ResConstructor;
import com.draniksoft.ome.editor.res_mgmnt.t.ResSubT;

public class DrawableGroupConstructor extends GroupResConstructor<Drawable> {

    private static String tag = "DrawableGroupConstructor";

    LinkedDrawable sp;
    GroupDrawable dwb;

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
    public void typeUpdate() {

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
    public void parseFrom(JsonValue v, World _w) {

    }

    @Override
    public void serializeTo(JsonValue v) {

    }

}
