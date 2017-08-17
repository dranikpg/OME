package com.draniksoft.ome.editor.esc_utils;

import com.artemis.BaseSystem;
import com.artemis.InvocationStrategy;
import com.badlogic.gdx.utils.ObjectMap;

public class PMIStrategy extends InvocationStrategy {

    long ms;

    ObjectMap<String,Long> mp;

    @Override
    protected void initialize() {

        mp = new ObjectMap<String, Long>();

    }

    @Override
    protected void process() {
        BaseSystem[] systemsData = systems.getData();
        for (int i = 0, s = systems.size(); s > i; i++) {
            if (disabled.get(i))
                continue;

            updateEntityStates();

            ms = System.currentTimeMillis();

            systemsData[i].process();

            mp.put(systemsData[i].getClass().getSimpleName(),System.currentTimeMillis() - ms);

        }

        updateEntityStates();
    }

    public String getO(){
        return mp.toString();
    }

    @Override
    public void setEnabled(BaseSystem system, boolean value) {
        super.setEnabled(system, value);

        if(!value){
            mp.put(system.getClass().getSimpleName(),0L);
        }

    }
}
