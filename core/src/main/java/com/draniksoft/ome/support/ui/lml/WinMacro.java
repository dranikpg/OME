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

	  if (hasAttribute("w")) {
		d.w = parser.parseInt(getAttribute("w"));
	  }

	  if (hasAttribute("min")) {
		d.min = parser.parseFloat(getAttribute("min"));
	  }

	  if (hasAttribute("max")) {
		d.max = parser.parseFloat(getAttribute("max"));
	  }

	  if (hasAttribute("dynamic")) {
		d.dynamic = parser.parseBoolean(getAttribute("dynamic"));
	  }

	  if (hasAttribute("hide_menu")) {
		d.hideM = parser.parseBoolean(getAttribute("hide_menu"));
	  }

	  if (hasAttribute("fullspan")) {
		d.replaceM = parser.parseBoolean(getAttribute("fullspan"));
	  }

	  if (hasAttribute("scX")) {
		d.scrollX = parser.parseBoolean(getAttribute("scX"));
	  }

	  if (hasAttribute("scY")) {
		d.scrollY = parser.parseBoolean(getAttribute("scY"));
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
