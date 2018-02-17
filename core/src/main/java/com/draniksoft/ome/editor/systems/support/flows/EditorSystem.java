package com.draniksoft.ome.editor.systems.support.flows;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.support.compositionObserver.*;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.EM_desc.BiLangEMDs;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.event.workflow.EditModeChangeE;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.support.load.IntelligentLoader;
import com.draniksoft.ome.support.load.interfaces.IRunnable;
import com.draniksoft.ome.utils.ESCUtils;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public class EditorSystem extends BaseSystem {

    private final String tag = "EditorSystem";

    public int sel = -1;

    IntMap<EditModeDesc> emDesc;

    EditModeDesc curEMD;
    EditModeDesc nexEMD;
    EditMode curEM;

    IntMap<CompositionObserver> comObs;

    @Wire(name = "engine_l")
    IntelligentLoader l;


    @Override
    protected void initialize() {


        l.passRunnable(new Loader());

    }

    private class Loader implements IRunnable {
        @Override
        public void run(IntelligentLoader l) {

            emDesc = new IntMap<EditModeDesc>();
            initBaseEms();

            comObs = new IntMap<CompositionObserver>();
            initComObs();

            steamComObsData();

            for (CompositionObserver p : comObs.values()) {
                p.init(world);
            }
        }

        @Override
        public byte getState() {
            return RUNNING;
        }
    }


    @Override
    protected void processSystem() {

    }

    /*



     */


    private void attachEditMode(EditMode mode) {

        if (curEM != null) {
            curEM.detached();
        }
        curEMD = null;

        if (nexEMD != null) curEMD = nexEMD;
        nexEMD = null;

        EditModeChangeE e = new EditModeChangeE();
        e.prevEM = curEM;

        curEM = mode;
        if (curEM != null)
            mode.attached(world);

        e.newEM = curEM;

        Gdx.app.debug(tag, "Changed to new Editmode");


        world.getSystem(OmeEventSystem.class).dispatch(e);

    }

    public void detachEditMode() {
        attachEditMode(null);
    }

    /*


     */


    @Subscribe(priority = ESCUtils.EVENT_MAX_PRIORITY)
    public void selectionChanged(SelectionChangeE e) {

        sel = e.n;
        for (CompositionObserver p : comObs.values()) {
            p.setSelection(e.n);
        }

    }

    @Subscribe(priority = ESCUtils.EVENT_HIGH_PRIORITY)
    public void compositionChanged(CompositionChangeE e) {

        if (e.e == sel && sel >= 0) {

            for (CompositionObserver p : comObs.values()) {
                p.selectionCompChanged();
            }

        }
    }

    @Subscribe
    public void modeChange(ModeChangeE e) {



    }

    public CompositionObserver getComOb(int id) {
        return comObs.containsKey(id) ? comObs.get(id) : null;
    }

    public Array<CompositionObserver> getComObs() {
        return comObs.values().toArray();
    }

    public Iterator<CompositionObserver> getComObsI() {
        return comObs.values().iterator();
    }

    public EditModeDesc getEditModeDesc(int id) {
        return emDesc.get(id);
    }

    public EditMode constructEditMode(EditModeDesc desc) {

        if (desc == null) return null;

        EditMode m = null;
        try {
            m = (EditMode) desc.c.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return m;

    }

    public EditMode constructEditMode(int id) {
        return constructEditMode(getEditModeDesc(id));
    }

    public void attachNewEditMode(EditModeDesc m) {

	  Gdx.app.debug(tag, "Construct requested " + m.getName());

        if (m == null) {
            Gdx.app.debug(tag, "New desc is null");
            return;
        }

        nexEMD = m;
        attachEditMode(constructEditMode(nexEMD));
    }

    public void attachNewEditMode(int id) {
        attachNewEditMode(getEditModeDesc(id));
    }

    public Iterator<EditModeDesc> getEmDesc() {
        return emDesc.values().iterator();
    }

    public IntMap.Values<EditModeDesc> getEmDescVs() {
        return emDesc.values();
    }


    public EditMode getCurEM() {
        return curEM;
    }

    public EditModeDesc getCurModeDesc() {
        return curEMD;
    }


    JsonReader r = new JsonReader();

    private void initBaseEms() {

        Gdx.app.debug(tag, "Parsing EM config file");

        String rootString = Gdx.files.internal("_data/editmodes.json").readString();
        JsonValue root = r.parse(rootString);

        for (JsonValue v : root) {
            BiLangEMDs d = new BiLangEMDs();
            d.name_en = v.getString("name_en");
            d.name_ru = v.getString("name_ru");
		d.id = v.getInt("ID");
		d.selRequired = v.getBoolean("sr");
            d.iconID = v.has("iconID") ? v.getString("iconID") : "i_edit";

            if (v.has("sav")) d.aviabT = v.getInt("sav");

            try {
                d.c = Class.forName(
                        "com.draniksoft.ome.editor.support.ems." +
                                v.getString("c"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (d.c != null && d.id > 0 && d.getName() != null)
                emDesc.put(d.id, d);
        }


        Gdx.app.debug(tag, "Collected " + emDesc.size + " internal editmodes");


    }

    private void steamComObsData() {

        Gdx.app.debug(tag, "Parsing com_obs -> observer init ");

        String rootS = Gdx.files.internal("_data/com_obs.json").readString();
        JsonValue root = r.parse(rootS);

        Iterator<CompositionObserver> i = getComObsI();
        CompositionObserver o;

        while (i.hasNext()) {
            o = i.next();

		if (root.has("" + o.ID)) {

		    o.loadActionConfig(root.get("" + o.ID));

            }

        }


    }

    private void initComObs() {

	  addComOb(CompositionObserver.IDs.POSITION, new PositionCO());

	  addComOb(CompositionObserver.IDs.DRAWABLE, new DrawableCO());

	  addComOb(CompositionObserver.IDs.MapObject, new MapObjectCO());

	  addComOb(CompositionObserver.IDs.PATH, new PathCompositionO());

        addComOb(CompositionObserver.IDs.LABEL, new LabelCompositionObserver());

    }

    private void addComOb(int c, CompositionObserver o) {
        comObs.put(c, o);
	  o.ID = c;
    }

    public void setEMDavbt(int emd, int i) {

        getEditModeDesc(emd).aviabT = i;

    }

}
