package com.draniksoft.ome.editor.systems.support;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.support.container.EM_desc.BiLangEMDs;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.event.EditModeChangeE;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.map_load.LoadSaveManager;
import com.draniksoft.ome.editor.support.map_load.ProjectLoader;
import com.draniksoft.ome.editor.support.workflow.EditMode;
import com.draniksoft.ome.editor.support.workflow.compositionObserver.MOCompositionO;
import com.draniksoft.ome.editor.support.workflow.compositionObserver.TimedCompositionO;
import com.draniksoft.ome.editor.support.workflow.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.utils.ESCUtils;
import com.draniksoft.ome.utils.struct.Pair;
import net.mostlyoriginal.api.event.common.EventSystem;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public class EditorSystem extends BaseSystem implements LoadSaveManager {

    private final String tag = "EditorSystem";

    public int sel = -1;

    Array<EditModeDesc> emDesc;

    EditModeDesc curEMD;
    EditModeDesc nexEMD;
    EditMode curEM;

    IntMap<CompositionObserver> comObs;


    @Override
    protected void initialize() {
        emDesc = new Array<EditModeDesc>();
        initBaseEms();

        comObs = new IntMap<CompositionObserver>();
        initComObs();

        steamComObsData();

        for (CompositionObserver p : comObs.values()) {
            p.init(world);
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


        world.getSystem(EventSystem.class).dispatch(e);

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

        Gdx.app.debug(tag, "Got composition change notfic");

        if (e.e == sel && sel >= 0) {

            for (CompositionObserver p : comObs.values()) {
                p.selectionCompChanged();
            }

        }
    }


    /*


     */

    @Override
    public String getNode() {
        return null;
    }

    @Override
    public void loadL(JsonValue val, ProjectLoader l) {

    }

    @Override
    public boolean loadG(ProjectLoader l) {
        return false;
    }

    @Override
    public Pair<String, JsonValue> save() {
        return null;
    }

    /*



     */

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

        for (EditModeDesc d : emDesc) {
            if (d.id == id) return d;
        }


        return null;
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

        Gdx.app.debug(tag, "Construct requested");

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

    public Array<EditModeDesc> getEmDesc() {
        return emDesc;
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

        String rootString = Gdx.files.internal("desc/editmodes.json").readString();
        JsonValue root = r.parse(rootString);

        for (JsonValue v : root) {
            BiLangEMDs d = new BiLangEMDs();
            d.name_en = v.getString("name_en");
            d.name_ru = v.getString("name_ru");
            d.id = v.getInt("id");
            d.selRequired = v.getBoolean("sr");

            try {
                d.c = Class.forName(
                        "com.draniksoft.ome.editor.support.workflow.def_ems." +
                                v.getString("c"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (d.c != null && d.id > 0 && d.getName() != null)
                emDesc.add(d);
        }


        Gdx.app.debug(tag, "Collected " + emDesc.size + " internal editmodes");


    }

    private void steamComObsData() {

        String rootS = Gdx.files.internal("desc/com_obs.json").readString();
        JsonValue root = r.parse(rootS);

        Iterator<CompositionObserver> i = getComObsI();
        CompositionObserver o;

        while (i.hasNext()) {
            o = i.next();

            if (root.has("" + o.id)) {

                o.loadActionConfig(root.get("" + o.id));

            }

        }


    }

    private void initComObs() {


        comObs.put(CompositionObserver.IDs.MO_CO, new MOCompositionO());
        comObs.get(CompositionObserver.IDs.MO_CO).id = CompositionObserver.IDs.MO_CO;

        comObs.put(CompositionObserver.IDs.TIMED_CO, new TimedCompositionO());
        comObs.get(CompositionObserver.IDs.TIMED_CO).id = CompositionObserver.IDs.TIMED_CO;


    }

}
