package com.draniksoft.ome.editor.ui.menu;

import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.utils.respone.ResponseCode;
import com.draniksoft.ome.utils.struct.ResponseListener;
import com.kotcrab.vis.ui.widget.VisTable;

public class BottomMenu extends VisTable {

    UiSystem uiSys;

    BaseBarButtons btns;


    public BottomMenu(UiSystem sys) {
	  this.uiSys = sys;
	  setBackground("primary");

	  init();

    }

    private void init() {
	  setHeight(Float.parseFloat(AppDO.I.LML().getArgument("bar_height")));

	  AppDO.I.LML().parseView(new ResponseListener() {
		@Override
		public void onResponse(short code) {
		    if (code == ResponseCode.SUCCESSFUL) {
			  btns = (BaseBarButtons) AppDO.I.LML().obtainParsed();
			  add(btns.getActor());
		    }
		}
	  }, "bar_base_buttons");

    }


}


