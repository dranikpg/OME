package com.draniksoft.ome.editor.texmgmnt.acess;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRAccesor {
    public TextureAtlas.AtlasRegion atlasR;
    public TextureRegion accelR;
    public int usages = 0;

    public TextureRAccesor(TextureAtlas.AtlasRegion atlasR) {
	  this.atlasR = atlasR;
	  this.accelR = new TextureRegion(atlasR);
    }


    public void updateOn(TextureRAccesor ac) {
	  this.atlasR = ac.atlasR;
	  this.accelR = ac.accelR;
    }

    public TextureRegion registerUsage() {
	  usages++;
	  return accelR;
    }

    public void unregisterUsage() {
	  usages--;
    }
}
