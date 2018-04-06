package com.draniksoft.ome.editor.res.color.typedata;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.draniksoft.ome.editor.res.color.simple.SimpleClP;
import com.draniksoft.ome.editor.res.impl.res_ifaces.Resource;
import com.draniksoft.ome.editor.res.impl.res_ifaces.WeakLinkedResource;
import com.draniksoft.ome.editor.res.impl.typedata.ResDataHandler;

public class SimpleClPTD implements ResDataHandler {


    public Color c;

    public SimpleClPTD(Color c) {
	  this.c = c;
    }

    public SimpleClPTD() {
    }

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
	  SimpleClP clp = new SimpleClP();
	  clp.color(c);
	  return clp;
    }

    @Override
    public ResDataHandler copy() {
	  SimpleClPTD cpy = new SimpleClPTD();
	  cpy.c = new Color(c);
	  return cpy;
    }
}
