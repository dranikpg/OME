package com.draniksoft.ome.support.ui.lml;

import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.ui.viewsys.BaseWinView;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.tag.AbstractMacroLmlTag;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;

public class WinMacro extends AbstractMacroLmlTag {

    public WinMacro(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
	  super(parser, parentTag, rawTagData);

	  BaseWinView v = (BaseWinView) AppDO.I.LML().tvw;

	  BaseWinView.WinDao d = new BaseWinView.WinDao();

	  if (hasAttribute("pct_mode")) {
		d.pctMode = parser.parseBoolean(getAttribute("pct_mode"));
	  }

	  if (hasAttribute("pct")) {
		d.pct = parser.parseFloat(getAttribute("pct"));
	  }

	  if (hasAttribute("width")) {
		d.w = parser.parseInt(getAttribute("width"));
	  }


	  if (hasAttribute("hide_menu")) {
		d.hideM = parser.parseBoolean(getAttribute("hide_menu"));
	  }

	  if (hasAttribute("fullspan")) {
		d.replaceM = parser.parseBoolean(getAttribute("fullspan"));
	  }


	  if (hasAttribute("pct_clamp")) {
		d.pctClamp = parser.parseBoolean(getAttribute("pct_clamp"));
	  }

	  if (hasAttribute("minw")) {
		d.minW = parser.parseFloat(getAttribute("minw"));
	  }

	  if (hasAttribute("maxw")) {
		d.maxW = parser.parseFloat(getAttribute("maxw"));
	  }

	  if (hasAttribute("minp")) {
		d.minp = parser.parseFloat(getAttribute("minp"));
	  }

	  if (hasAttribute("maxp")) {
		d.maxp = parser.parseFloat(getAttribute("maxp"));
	  }

	  v.consumeConfig(d);

    }

    public static LmlTagProvider getP() {
	  return new LmlTagProvider() {
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
		    return new WinMacro(parser, parentTag, rawTagData);
		}
	  };
    }

    @Override
    public void handleDataBetweenTags(CharSequence rawData) {
	  appendTextToParse(rawData);
    }
}
