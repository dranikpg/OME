package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.map_load.ProjectLoader;
import com.draniksoft.ome.utils.struct.Pair;
import dint.Dint;

public class TimeMgr extends BaseSystem implements LoadSaveManager {

    // map data
    int lowerB;
    int upperB;

    // current time
    int cur = -1;

    // days per time stamp
    int d_stmp = 1;

    float timeStamp = 0;
    float stampMax = 5; // in seconds

    boolean edit = false;

    public TimeMgr() {


    }

    @Override
    protected void initialize() {

        cur = Dint.today();

    }

    @Override
    protected void processSystem() {

        if (edit) {
            return;
        }

        timeStamp += Gdx.graphics.getRawDeltaTime();

        if (timeStamp > stampMax) {
            timeStamp -= stampMax;
            cur = Dint.addDays(cur, d_stmp);
        }

    }

    public float getStepPrecent() {

        return timeStamp / stampMax;

    }

    @Override
    public String getNode() {
        return null;
    }

    @Override
    public void loadL(JsonValue val, ProjectLoader l) {

    }

    @Override
    public boolean loadG(ProjectLoader l) {
        return true;
    }

    @Override
    public Pair<String, JsonValue> save() {
        return null;
    }

    public int getTime() {
        return cur;
    }

    public boolean isInRange(int d) {

        return upperB >= d && d >= lowerB;
    }

    public boolean isInRange(int d1, int d2) {

        return isInRange(d1) && isInRange(d2);

    }

    public boolean isNow(int d) {

        return cur == d;

    }

    // return weather the current date is in the given interval
    public boolean isNow(int d1, int d2) {

        return d1 <= cur && cur <= d2;

    }


    public int getLowerB() {
        return lowerB;
    }

    public int getUpperB() {
        return upperB;
    }
}
