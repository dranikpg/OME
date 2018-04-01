package com.draniksoft.ome.editor.systems.support.flows;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.draniksoft.ome.editor.support.compositionObserver.DrawableCO;
import com.draniksoft.ome.editor.support.compositionObserver.IdentityCO;
import com.draniksoft.ome.editor.support.compositionObserver.PositionCO;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.editor.support.event.__base.OmeEventSystem;
import com.draniksoft.ome.editor.support.event.entityy.CompositionChangeE;
import com.draniksoft.ome.editor.support.event.entityy.SelectionChangeE;
import com.draniksoft.ome.editor.support.event.workflow.EditModeChangeE;
import com.draniksoft.ome.editor.support.event.workflow.ModeChangeE;
import com.draniksoft.ome.support.execution_base.ExecutionProvider;
import com.draniksoft.ome.utils.ESCUtils;
import com.draniksoft.ome.utils.JsonUtils;
import net.mostlyoriginal.api.event.common.Subscribe;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.Callable;

public class EditorSystem extends BaseSystem {

    private final String tag = "EditorSystem";

    public int sel = -1;

    IntMap<EditModeDesc> emDesc;

    EditModeDesc curEMD;
    EditModeDesc nexEMD;
    EditMode curEM;

    IntMap<CompositionObserver> comObs;

    @Wire(name = "engine_l")
    ExecutionProvider l;


    @Override
    protected void initialize() {

        l.exec(new Loader());

    }

    private class Loader implements Callable<Void> {
        @Override
        public Void call() {
            emDesc = new IntMap<EditModeDesc>();
            initBaseEms();
            comObs = new IntMap<CompositionObserver>();
            initComObs();
            steamComObsData();
            for (CompositionObserver p : comObs.values()) {
                p.init(world);
            }
            return null;
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

	  if (desc.ref.get() != null) return desc.ref.get();

        EditMode m = null;
        try {
            m = (EditMode) desc.c.getConstructor().newInstance();
		desc.ref = new WeakReference<EditMode>(m);
	  } catch (Exception e) {
		Gdx.app.error(tag, "", e);
	  }

        return m;

    }

    public EditMode constructEditMode(int id) {
        return constructEditMode(getEditModeDesc(id));
    }

    public void attachNewEditMode(EditModeDesc m) {

	  Gdx.app.debug(tag, "Construct requested " + m.name.get());

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

    public EditMode getCurEM() {
        return curEM;
    }

    public EditModeDesc getCurModeDesc() {
        return curEMD;
    }


    JsonReader r = new JsonReader();

    private void initBaseEms() {

        Gdx.app.debug(tag, "Parsing EM config file");

	  String rootString = Gdx.files.internal("data/editmodes.json").readString();
	  JsonValue root = r.parse(rootString);

        for (JsonValue v : root) {
		EditModeDesc d = new EditModeDesc();

		d.name = JsonUtils.parseText(v.get("name"), true);
		d.id = v.getInt("ID");

		d.selRequired = v.getBoolean("sr");
            d.iconID = v.has("iconID") ? v.getString("iconID") : "i_edit";

		if (v.has("av")) d.available = v.getBoolean("av");

            try {
                d.c = Class.forName(
                        "com.draniksoft.ome.editor.support.ems." +
                                v.getString("c"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

		if (d.c != null && d.id > 0)
		    emDesc.put(d.id, d);
        }


        Gdx.app.debug(tag, "Collected " + emDesc.size + " internal editmodes");


    }

    private void steamComObsData() {

        Gdx.app.debug(tag, "Parsing com_obs -> observer init ");

	  String rootS = Gdx.files.internal("data/comob_actions.json").readString();
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

	  addComOb(CompositionObserver.IDs.IDENTITY, new IdentityCO());

	  addComOb(CompositionObserver.IDs.POSITION, new PositionCO());

	  addComOb(CompositionObserver.IDs.DRAWABLE, new DrawableCO());


    }

    private void addComOb(int c, CompositionObserver o) {
        comObs.put(c, o);
	  o.ID = c;
    }


}
