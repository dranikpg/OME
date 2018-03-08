package com.draniksoft.ome.utils.lang;

public class PlainText implements Text {
    String t;

    public PlainText(String t) {
	  this.t = t;
    }

    public PlainText() {
    }

    @Override
    public String get() {
	  return t;
    }
}
