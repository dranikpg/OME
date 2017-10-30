package com.draniksoft.ome.editor.support.container.CO_actiondesc;

import com.draniksoft.ome.mgmnt_base.base.AppDataObserver;

public class BiLangActionDEsc extends ActionDesc {

    public String name_ru;
    public String desc_ru;
    public String name_en;
    public String desc_en;


    @Override
    public String getName() {

        if (AppDataObserver.getI().L().isEn()) {
            return name_en;
        } else
            return name_ru;

    }

    @Override
    public String getDesc() {
        if (AppDataObserver.getI().L().isEn()) {
            return desc_en;
        } else
            return desc_ru;

    }
}
