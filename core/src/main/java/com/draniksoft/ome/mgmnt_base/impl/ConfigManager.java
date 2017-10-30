package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.configs.ConfigDao;
import com.draniksoft.ome.support.configs.ConfigValueType;
import com.draniksoft.ome.support.configs.configd_types.BundleConfigDao;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.JsonUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class ConfigManager extends AppDataManager {

    private static final String tag = "ConfigManager";

    HashMap<String, ConfigDao> m;
    HashMap<String, LinkedHashSet<ConfigDao>> tags;

    @Override
    protected void startupLoad(IntelligentLoader l) {
        m = new HashMap<String, ConfigDao>();
        tags = new HashMap<String, LinkedHashSet<ConfigDao>>();

        loadDaos();

    }


    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {


    }


    private void loadDaos() {

        JsonReader rd = new JsonReader();
        JsonValue root = rd.parse(Gdx.files.internal("_data/configs.json"));

        for (JsonValue v : root) {

            constructDao(v);

        }

        Gdx.app.debug(tag, "Fetched " + m.size() + " configs");

    }

    private void constructDao(JsonValue v) {

        String id = v.name;

        ConfigValueType t = fetchConfigVT(v.get("vt"));

        String nameK = v.getString("namek");
        String descK = v.getString("desck");

        ConfigDao d = new BundleConfigDao(t, nameK, descK);

        if (AppDO.I.getPrefs().contains("cfg_" + id)) {
            d.setV(FUtills.getVal(AppDO.I.getPrefs(), t.getT(), "cfg_" + id));
        } else {
            d.setV(JsonUtils.getVal(t.getT(), v.get("defv")));
        }


        for (String s : v.get("tags").asStringArray()) {
            d.getTags().add(s);
            if (!(tags.containsKey(s))) tags.put(s, new LinkedHashSet<ConfigDao>());
            tags.get(s).add(d);

        }


        m.put(id, d);
    }

    private ConfigValueType fetchConfigVT(JsonValue vt) {

        if (vt.type() == JsonValue.ValueType.stringValue) {
            String v = vt.asString();
            if (v.equals("bool")) return ConfigValueType.boolT;
            if (v.equals("freestr")) return ConfigValueType.freeStringT;
        }

        return null;
    }


}
