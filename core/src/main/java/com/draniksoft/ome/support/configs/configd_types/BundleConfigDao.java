package com.draniksoft.ome.support.configs.configd_types;

import com.draniksoft.ome.support.configs.ConfigDao;
import com.draniksoft.ome.support.configs.ConfigValueType;
import com.draniksoft.ome.utils.SUtils;

public class BundleConfigDao extends ConfigDao {

    public String nameK, descK;

    public BundleConfigDao(String id, ConfigValueType t) {
        super(id, t);
    }

    public BundleConfigDao(String id, ConfigValueType t, String nameK, String descK) {
        super(id, t);
        this.nameK = nameK;
        this.descK = descK;
    }

    @Override
    public String getName() {
        return SUtils.getS(nameK);
    }

    @Override
    public String getDesc() {
        return SUtils.getS(descK);
    }
}
