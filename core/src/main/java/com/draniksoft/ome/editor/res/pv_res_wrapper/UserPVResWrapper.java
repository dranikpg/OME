package com.draniksoft.ome.editor.res.pv_res_wrapper;

public class UserPVResWrapper extends PVResWrapper {

    String name;

    @Override
    public boolean allowed(MODIFIC m) {
	  return true;
    }

    @Override
    public void setName(String name) {
	  this.name = name;
    }

    @Override
    public String getName() {
	  return name;
    }

    @Override
    public boolean isBase() {
	  return false;
    }
}
