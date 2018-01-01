package com.draniksoft.ome.mgmnt_base.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.config.ConfigValChangeE;
import com.draniksoft.ome.main_menu.MainBase;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.base.AppDataManager;
import com.draniksoft.ome.support.configs.ConfReferencer;
import com.draniksoft.ome.support.configs.ConfigDao;
import com.draniksoft.ome.support.configs.ConfigValueType;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.utils.FUtills;
import com.draniksoft.ome.utils.JsonUtils;

import java.util.HashMap;
import java.util.Iterator;

public class ConfigManager extends AppDataManager {


    private static final String tag = "ConfigManager";

    HashMap<String, ConfigDao> m;

    ObjectMap<String,  // index that changes
            ObjectMap<String, // index that needs change
			  ConfReferencer<Object, Object>>> trig; // val :: key

    JsonValue jv;

    @Override
    protected void startupLoad(IntelligentLoader l) {
        m = new HashMap<String, ConfigDao>();

        //            val that changes      val that needs ch   val : key
	  trig = new ObjectMap<String, ObjectMap<String, ConfReferencer<Object, Object>>>();

        loadDaos();

        parseTrigger();
    }

    private void parseTrigger() {
        for (JsonValue v : jv) {
            if (!v.has("trig")) continue;
            for (JsonValue pv : v.get("trig")) {
		    trig.get(pv.name).put(v.name, new ConfReferencer<Object, Object>());
		    for (JsonValue uv : pv) {
                    String kS = uv.name;
                    String vS = uv.asString();
                    Object kV;
                    Object vV;
                    kV = JsonUtils.parseTpye(kS, getConfT(pv.name));
                    vV = JsonUtils.parseTpye(vS, getConfT(v.name));
			  trig.get(pv.name).get(v.name).insert(kV, vV);
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
            FUtills.putPrefsV(AppDO.I.getPrefs(), "_cfg_" + d.getId(), d.getV(d.getT()), d.getT());
        }
        Gdx.app.debug(tag, "Moved config snapshot to prefs");

    }

    public boolean hasConfig(String id) {
        return m.containsKey(id);
    }

    public Class getConfT(String id) {
        return m.get(id).getT();
    }

    public ConfigValueType getConfVT(String id) {
        return m.get(id).getVT();
    }

    public <T> T getConfVal(String id, Class<T> val) {
        return m.get(id).getV(val);
    }

    public int getConfVal_I(String id) {
	  return getConfVal(id, Integer.class);
    }

    public boolean getConfVal_B(String id) {
	  return getConfVal(id, Boolean.class);
    }

    public String getConfVal_S(String id) {
	  return getConfVal(id, String.class);
    }

    public void setConfVal(String id, Object val) {
        if (m.get(id).setV(val)) {
            Gdx.app.debug(tag, "Changing " + id);
            if (MainBase.engine != null) {
		    MainBase.engine.getSystem(OmeEventSystem.class).dispatch(new ConfigValChangeE(id));
		}
		checkTrigger(id, val);
        }

    }

    private void checkTrigger(String id, Object val) {
	  for (ObjectMap.Entry<String, ConfReferencer<Object, Object>> e : trig.get(id).entries()) {
		if (e.value.hasConf(val)) {
		    Object nval = e.value.get(val);
		    if (nval instanceof String && ((String) nval).startsWith("$+")) {
			  String newID = ((String) nval).substring(2);
			  if (hasConfig(newID)) {
				nval = getConfVal(newID, getConfT(newID));
			  }
		    }
		    setConfVal(e.key, nval);
		} else {
		    if (e.value.hasOption()) {
			  setConfVal(e.key, e.value.getOptional());
		    }
		}
        }
    }

    public Iterator<ConfigDao> getConfI() {
        return m.values().iterator();
    }


    private void loadDaos() {

        JsonReader rd = new JsonReader();
        jv = rd.parse(Gdx.files.internal("_data/configs.json"));

        boolean forceNew = AppDO.I.getPrefs().getBoolean("cfg_reset", false);

        for (JsonValue v : jv) {
            constructDao(v, forceNew);
        }

        Gdx.app.debug(tag, "Fetched " + m.size() + " configs");

    }


    private void addRdM(String id) {
	  trig.put(id, new ObjectMap<String, ConfReferencer<Object, Object>>());
    }

    private void constructDao(JsonValue v, boolean forceDef) {

        String id = v.name;

        ConfigValueType t = fetchConfigVT(v.get("vt"));

        ConfigDao d = new ConfigDao(id, t);

        if (!forceDef && AppDO.I.getPrefs().contains("_cfg_" + id)) {
            d.setV(FUtills.getVal(AppDO.I.getPrefs(), t.getT(), "_cfg_" + id));
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
            if (v.equals("freeint")) return ConfigValueType.freeIntT;
        } else {
            String id = vt.getString("id");
            if (id.equals("boundInt")) {
                return ConfigValueType.constructBoundedIntT(vt.getInt("s"), vt.getInt("e"), vt.getInt("st", 1));
            }

        }

        return null;
    }


}
