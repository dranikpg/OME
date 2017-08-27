package com.draniksoft.ome.editor.esc_utils;

import com.artemis.BaseSystem;
import com.artemis.InvocationStrategy;
import com.badlogic.gdx.utils.ObjectMap;

public class PMIStrategy extends InvocationStrategy {

    long ms;

    ObjectMap<String, Long> cP;
    ObjectMap<String, Long> mP;


    @Override
    protected void initialize() {

        cP = new ObjectMap<String, Long>();
        mP = new ObjectMap<String, Long>();
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

            cP.put(systemsData[i].getClass().getSimpleName(), System.currentTimeMillis() - ms);
            mP.put(systemsData[i].getClass().getSimpleName(), Math.max(System.currentTimeMillis() - ms,
                    mP.get(systemsData[i].getClass().getSimpleName(), 0L)));

        }

        updateEntityStates();
    }

    public String getO(){
        return cP.toString();
    }

    public String getMO() {
        return mP.toString();
    }

    @Override
    public void setEnabled(BaseSystem system, boolean value) {
        super.setEnabled(system, value);

        if(!value){
            cP.put(system.getClass().getSimpleName(), 0L);
        }

    }
}
