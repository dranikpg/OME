package com.draniksoft.ome.support.ui.lml;

import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.impl.tag.AbstractMacroLmlTag;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.parser.tag.LmlTagProvider;

public class ExtViewMacro extends AbstractMacroLmlTag {

    public ExtViewMacro(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
	  super(parser, parentTag, rawTagData);

	  BaseView v = AppDO.I.LML().tvw;

	  String name;
	  name = parser.parseString(getAttribute("name"));

	  String id;
	  id = parser.parseString(getAttribute("id"));

	  v.addIncld(name, id);
    }


    @Override
    public void handleDataBetweenTags(CharSequence rawData) {

    }

    public static LmlTagProvider getP() {
	  return new LmlTagProvider() {
		@Override
		public LmlTag create(LmlParser parser, LmlTag parentTag, StringBuilder rawTagData) {
		    return new ExtViewMacro(parser, parentTag, rawTagData);
		}
	  };
    }


}
