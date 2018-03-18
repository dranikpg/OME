package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.editor.texmgmnt.ext.AssetSubExtension;
import com.draniksoft.ome.utils.SUtils;
import com.draniksoft.ome.utils.struct.MtPair;

/*
	Replacement for "old" AssManager & SimpleAssManager fail
	Handles texture regions
	At the moment its still a placeholder,
	texture-merge will be handled here


 */
public class TextureRManager extends Manager {

    private static final String tag = "TextureRManger";

    MtPair<String, String> tpP;

    @Override
    protected void initialize() {
	  tpP = new MtPair<String, String>();
    }

    // get from full path
    public TextureRAccesor get(String uri) {
	  SUtils.parseAssetURI(uri, tpP);
	  AssetSubExtension ext = world.getSystem(ExtensionManager.class).getSub(tpP.K(), AssetSubExtension.class);
	  if (ext == null) return null;
	  return ext.get(tpP.V());
    }

    public void updateUsage(TextureRAccesor ac, int dlt) {
	  ac.usages += dlt;
	  if (ac.usages < 0) {
		Gdx.app.error(tag, "NEGATIVE USAGE AMOUNT " + ac.toString());
	  }
    }


}
