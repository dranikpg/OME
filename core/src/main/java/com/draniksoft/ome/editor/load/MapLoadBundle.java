package com.draniksoft.ome.editor.load;

public class MapLoadBundle {

    public String inDir;
    public String outDir;

    public MapLoadBundle(String fPath) {
	  this.inDir = fPath;
	  outDir = inDir;
    }

    public MapLoadBundle() {
    }
}
