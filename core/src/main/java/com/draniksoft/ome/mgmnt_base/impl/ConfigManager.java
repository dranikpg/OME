package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.support.event.config.ConfigValChangeE;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.configs.ConfigDao;
import com.draniksoft.ome.support.configs.ConfigValueType;
import com.draniksoft.ome.support.configs.configd_types.BundleConfigDao;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.JsonUtils;
import net.mostlyoriginal.api.event.common.EventSystem;

import java.util.HashMap;
import java.util.Iterator;

public class ConfigManager extends AppDataManager {

    private static final String tag = "ConfigManager";

    HashMap<String, ConfigDao> m;

    ObjectMap<String,  // index that changes
            ObjectMap<String, // index that needs change
                    ObjectMap<Object, Object>>> trig; // val :: key

    JsonValue jv;

    @Override
    protected void startupLoad(IntelligentLoader l) {
        m = new HashMap<String, ConfigDao>();

        //            val that changes      val that needs ch   val : key
        trig = new ObjectMap<String, ObjectMap<String, ObjectMap<Object, Object>>>();

        loadDaos();

        parseTrigger();
    }

    private void parseTrigger() {

        for (JsonValue v : jv) {
            if (!v.has("trig")) continue;
            for (JsonValue pv : v.get("trig")) {
                trig.get(pv.name).put(v.name, new ObjectMap<Object, Object>());
                for (JsonValue uv : pv) {
                    String kS = uv.name;
                    String vS = uv.asString();
                    Object kV;
                    Object vV;
                    kV = JsonUtils.parseTpye(kS, getConfT(pv.name));
                    vV = JsonUtils.parseTpye(vS, getConfT(v.name));
                    Gdx.app.debug(tag, v.name + " " + pv.name);
                    trig.get(pv.name).get(v.name).put(kV, vV);

                }
            }
        }


        Gdx.app.debug(tag, "Parsed trigger");

        Gdx.app.debug(tag, trig.toString());
    }


    @Override
    protected void engineLoad(IntelligentLoader l) {

    }

    @Override
    protected void terminateLoad() {

        for (ConfigDao d : m.values()) {
            FUtills.putPrefsV(AppDO.I.getPrefs(), "cfg_" + d.getId(), d.getV(d.getT()), d.getT());
        }

    }

    public boolean hasConfig(String id) {
        return m.containsKey(id);
    }

    public Class getConfT(String id) {
        return m.get(id).getT();
    }

    public <T> T getConfVal(String id, Class<T> val) {
        return m.get(id).getV(val);
    }

    public void setConfVal(String id, Object val) {
        if (m.get(id).setV(val)) {
            Gdx.app.debug(tag, "Changing " + id);
            if (MainBase.engine != null) {
                MainBase.engine.getSystem(EventSystem.class).dispatch(new ConfigValChangeE(id));
            }
            checkTrigger(id, val);
        }

    }

    private void checkTrigger(String id, Object val) {
        for (ObjectMap.Entry<String, ObjectMap<Object, Object>> e : trig.get(id).entries()) {
            if (e.value.containsKey(val)) {
                setConfVal(e.key, e.value.get(val));
            }
        }
    }

    public Iterator<ConfigDao> getConfI() {
        return m.values().iterator();
    }


    private void loadDaos() {

        JsonReader rd = new JsonReader();
        jv = rd.parse(Gdx.files.internal("_data/configs.json"));

        for (JsonValue v : jv) {
            constructDao(v);
        }

        Gdx.app.debug(tag, "Fetched " + m.size() + " configs");

    }

    private void addRdM(String id) {
        trig.put(id, new ObjectMap<String, ObjectMap<Object, Object>>());
    }

    private void constructDao(JsonValue v) {

        String id = v.name;

        ConfigValueType t = fetchConfigVT(v.get("vt"));

        String nameK = v.getString("namek");
        String descK = v.getString("desck");

        ConfigDao d = new BundleConfigDao(id, t, nameK, descK);

        if (AppDO.I.getPrefs().contains("cfg_" + id)) {
            d.setV(FUtills.getVal(AppDO.I.getPrefs(), t.getT(), "cfg_" + id));
        } else {
            d.setV(JsonUtils.getVal(t.getT(), v.get("defv")));
        }

        addRdM(id);

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
