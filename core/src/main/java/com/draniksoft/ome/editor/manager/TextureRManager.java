package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
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

    public TextureRAccesor consume(String uri) {
	  TextureRAccesor ac = get(uri);
	  if (ac == null) return null;
	  consume(ac);
	  return ac;
    }

    public void consume(TextureRAccesor ac) {
	  ac.registerUsage();
    }

    public void release(TextureRAccesor ac) {
	  ac.unregisterUsage();
    }


}
