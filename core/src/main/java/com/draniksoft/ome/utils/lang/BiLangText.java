package com.draniksoft.ome.utils.lang;

import com.draniksoft.ome.mgmnt_base.base.AppDO;

public class BiLangText implements Text {

    public String ru, en;

    public BiLangText(String en, String ru) {
	  this.ru = ru;
	  this.en = en;
    }

    public BiLangText() {

    }

    @Override
    public String get() {
	  if (AppDO.I.L().isEnL()) {
		return en;
	  } else {
		return ru;
	  }
    }

}
