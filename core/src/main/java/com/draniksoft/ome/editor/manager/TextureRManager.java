package com.draniksoft.ome.editor.manager;

import com.artemis.Manager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;
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

    /*
    	TextureRAccessor operations
     */

    public void updateUsage(TextureRAccesor ac, int dlt) {
	  ac.usages += dlt;
	  if (ac.redirect != null) updateUsage(ac.redirect, dlt);
    }

    public void updateReference(TextureRAccesor ac, int dlt) {
	  if (dlt == 0) return;
	  int odl = ac.references;
	  ac.references += dlt;

	  if (odl == 0 && ac.references > 0) {
		ac.ext.reference(1);
	  } else if (ac.references == 0 && odl > 0) {
		ac.ext.reference(-1);
	  }

	  if (ac.redirect != null) updateReference(ac.redirect, dlt);
    }

    public void redirectTo(TextureRAccesor ac1, TextureRAccesor ac2) {
	  ac1.redirect = ac2.redirect;
	  updateReference(ac2, ac1.references + 1); // one as the first one refers to the second
	  updateUsage(ac2, ac1.usages);
    }

    public void removeRd(TextureRAccesor ac) {
	  if (ac.redirect == null) return;
	  updateUsage(ac.redirect, -ac.usages);
	  updateReference(ac.redirect, -ac.references - 1); // remove the one from the redirect
	  ac.redirect = null;
    }

    public void refreshAC(TextureRAccesor ac, TextureAtlas.AtlasRegion r) {
	  ac.atlasR = r;
	  ac.accelR = new TextureRegion(r);
	  removeRd(ac);
    }


}
