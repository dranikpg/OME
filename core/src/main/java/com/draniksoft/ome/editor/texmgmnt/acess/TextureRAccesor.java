package com.draniksoft.ome.editor.texmgmnt.acess;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;

public class TextureRAccesor {

    private static final String tag = "TextureRAccessor";

    public AssetSubExtension ext;

    public TextureAtlas.AtlasRegion atlasR;
    public TextureRegion accelR;

    public TextureRAccesor redirect;

    public int usages = 0;
    public int references = 0;

    public TextureRAccesor(TextureAtlas.AtlasRegion atlasR) {
	  this.atlasR = atlasR;
	  this.accelR = new TextureRegion(atlasR);
    }

    public TextureAtlas.AtlasRegion atl() {
	  if (redirect != null) return redirect.atl();
	  return atlasR;
    }

    public TextureRegion acl() {
	  if (redirect != null) return redirect.acl();
	  return accelR;
    }


    @Override
    public String toString() {
	  if (redirect != null) return "RD -> " + redirect.toString();
	  return atlasR.name + "-u(" + usages + ")";
    }
}
