package com.draniksoft.ome.editor.struct.text_ext_test;

public class TheText {

    public String name;
    public String text;


    @Override
    public String toString() {
	  return name + ": \n " + text;
    }
}
