package com.draniksoft.ome.editor.support.container.EM_desc;

import com.draniksoft.ome.mgmnt_base.AppDataObserver;

public class BiLangEMDs extends EditModeDesc {

    public String name_ru;
    public String name_en;

    @Override
    public String getName() {

        if (AppDataObserver.getI().L().isEn()) {
            return name_en;
        } else
            return name_ru;

    }
}
