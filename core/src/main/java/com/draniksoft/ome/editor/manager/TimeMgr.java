package com.draniksoft.ome.editor.manager;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.draniksoft.ome.editor.load.LoadSaveManager;
import com.draniksoft.ome.editor.load.ProjectLoader;
import com.draniksoft.ome.editor.load.ProjectSaver;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.support.load.IntelligentLoader;
import dint.Dint;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

public class TimeMgr extends BaseSystem implements LoadSaveManager {

    private static final String tag = "TimeMgr";

    // map data
    int lowerB;
    int upperB;

    // current time
    int cur = -1;

    // days per time stamp

    int y_stmp = 1;

    float timeStamp = 0;
    float stampMax = 5; // in seconds


    public TimeMgr() {


    }

    @Override
    protected void initialize() {

        lowerB = Dint.create(0, 0, 0);
        upperB = Dint.create(10, 0, 0);

        cur = lowerB;

        Gdx.app.debug(tag, "" + cur);

        setEnabled(false);

        world.getSystem(EventSystem.class).registerEvents(this);
    }

    @Override
    protected void processSystem() {

        if (Dint.diff(upperB, cur) <= 0) return;

        timeStamp += Gdx.graphics.getRawDeltaTime();

        if (timeStamp > stampMax) {

            timeStamp -= stampMax;

            cur = Dint.addYears(cur, y_stmp);

            Gdx.app.debug(tag, "" + cur);

        }

    }

    public float getStepPrecent() {

        return timeStamp / stampMax;

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

    @Subscribe
    public void modeChanged(ModeChangeE e) {

        this.cur = lowerB;

        setEnabled(e.SHOW_MODE);
    }

    @Override
    public void save(IntelligentLoader l, ProjectSaver s) {

    }

    @Override
    public void load(IntelligentLoader il, ProjectLoader ld) {

    }
}
