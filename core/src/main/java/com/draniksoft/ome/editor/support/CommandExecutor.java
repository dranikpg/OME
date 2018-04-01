package com.draniksoft.ome.editor.support;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.esc_utils.OmeStrategy;
import com.draniksoft.ome.editor.extensions.Extension;
import com.draniksoft.ome.editor.extensions.export.ExtensionExporter;
import com.draniksoft.ome.editor.extensions.stg.ExtensionDao;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.manager.ExtensionManager;
import com.draniksoft.ome.editor.manager.MapMgr;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.struct.text_ext_test.TheTextSubExt;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.input.back.SelectIC;
import com.draniksoft.ome.editor.support.input.back.TimedSelectIC;
import com.draniksoft.ome.editor.support.input.base_mo.NewMOIC;
import com.draniksoft.ome.editor.support.render.core.SbsRenderer;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;
import com.draniksoft.ome.editor.systems.gfx_support.CameraSys;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PositionSystem;
import com.draniksoft.ome.editor.systems.render.editor.SubsidiaryRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.ConsoleSys;
import com.draniksoft.ome.editor.systems.support.ExecutionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.flows.EditorSystem;
import com.draniksoft.ome.editor.systems.support.flows.WorkflowSys;
import com.draniksoft.ome.editor.texmgmnt.acess.TextureRAccesor;
import com.draniksoft.ome.editor.texmgmnt.ext.b.AssetSubExtension;
import com.draniksoft.ome.editor.texmgmnt.ext.gp_ext.AssetGroupSubExt;
import com.draniksoft.ome.editor.texmgmnt.ext.groups.AssetGroup;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.impl.ConfigManager;
import com.draniksoft.ome.support.configs.ConfigDao;
import com.draniksoft.ome.support.execution_base.sync.SyncTask;
import com.draniksoft.ome.support.execution_base.ut.StepLoader;
import com.draniksoft.ome.support.ui.viewsys.BaseView;
import com.draniksoft.ome.utils.Const;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.GU;
import com.draniksoft.ome.utils.cam.Target;
import com.draniksoft.ome.utils.struct.ResponseListener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class CommandExecutor extends com.strongjoshua.console.CommandExecutor {

    World world;

    public CommandExecutor(World world){
        this.world = world;
    }


    /**
     * Simple console utils
     *
     */

    public void clear(){
        console.clear();
    }

    public void resize(int xp, int yp){

        console.setSizePercent(xp, yp);

    }

    public void pos(int xp, int yp) {

	  console.setPositionPercent(xp, yp);

    }

    /**
     * Show edit mode urils
     */

    public void set_mode(boolean m) {

	  if (m) {
		world.getSystem(WorkflowSys.class).requestShow();
	  } else {
		world.getSystem(WorkflowSys.class).requestEdit();
	  }
        console.log("Show mode " + world.getSystem(WorkflowSys.class).getSHOW_M());
    }


    /**
     * Environment utils
     */


    public void denv_set(String n, int b) throws NoSuchFieldException, IllegalAccessException {

        Env.changeVal(n, b == 1);

    }

    public void log_denv() throws IllegalAccessException {

        Field[] fields = Env.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                console.log(extendString(f.getName(), 10) + " =  " + f.getBoolean(null));
            }
        }

    }

    public void log_appv() {

        console.log("" + Const.appVersion);

    }

    public void log_appfn() {

        console.log(Const.appVFullName);

    }

    /*
        Heap Utils
     */

    public void run_gc() {
        System.gc();
    }

    public void log_mem() {

    }

    /*
        Config utils
     */


    public void log_cf() {
        Iterator<ConfigDao> i = AppDO.I.C().getConfI();
        ConfigDao d;
        while (i.hasNext()) {
            d = i.next();
            console.log(d.getId() + "   ::" + d.getVT().getClass().getSimpleName() + "  =  " + d.getV(d.getT()).toString());
        }
    }

    public void log_cf(String id) {
        ConfigManager c = AppDO.I.C();
        console.log(id + "    ::" + c.getConfVT(id).getClass().getSimpleName() + "   =  " + c.getConfVal(id, c.getConfT(id)).toString());
    }

    public void set_cf(String id, boolean b) {
        AppDO.I.C().setConfVal(id, b);
    }

    public void set_cf(String id, int b) {
        AppDO.I.C().setConfVal(id, b);
    }

    public void set_cf(String id, String b) {
        AppDO.I.C().setConfVal(id, b);
    }

    /*
        Execution utils
     */

    public void log_shd() {
        ExecutionSystem s = world.getSystem(ExecutionSystem.class);
        console.log("Total " + s.getShdTaskACount());
        Iterator<SyncTask> it = s.getShdTasks();
        SyncTask t;
        while (it.hasNext()) {
            t = it.next();
            console.log(t.getClass().getSimpleName() + " (~" + t.getFQ() + ";+" + t.getPhase() + ")");
        }
    }


    /**
     * FIle mgmnt utils
     */

    public void set_mlb(String p) {

        world.getSystem(ProjectLoadSystem.class).setBundle(new MapLoadBundle(p));

    }

    public void save() {
        try {
            world.getSystem(ProjectLoadSystem.class).save();
        } catch (Exception e) {
            Gdx.app.error("", "", e);
        }
    }

    public void load_h() {
        try {
            world.getSystem(ProjectLoadSystem.class).setBundle(new MapLoadBundle(AppDO.I.LH().getLast().get(0)));
            world.getSystem(ProjectLoadSystem.class).load();
        } catch (Exception e) {
            Gdx.app.error("", "", e);
        }
    }


    public void log_lastop() {

        try {
            for (String o : AppDO.I.LH().getLast()) {
                console.log(o);
            }
        } catch (Exception e) {
            Gdx.app.error("C", "", e);
        }

    }

    /*
        Extension utils
     */

    public void log_ext() {
        for (Iterator<Extension> it = world.getSystem(ExtensionManager.class).getAll(); it.hasNext(); ) {
            Extension e = it.next();
            console.log(e.ID + " (" + e.t.name() + "-" + e.s.toString() + ")");
        }
    }

    public void log_map_ext() {
        try {
            log_ext(null);
        } catch (Exception e) {
            Gdx.app.error("", "", e);
        }
    }
    public void log_ext(String id) {
        Extension e = world.getSystem(ExtensionManager.class).getExt(id);
        if (e == null) {
            console.log("NOT FOUND");
            return;
        }
        console.log(e.ID + "  (" + e.dao.name.get() + ")");
        console.log(e.t.name() + "-" + e.s.toString());
        console.log(e.map.toString());
    }
    public void log_av_ext() {
        try {
            for (Iterator<ExtensionDao> it = world.getSystem(ExtensionManager.class).getAllDaos(); it.hasNext(); ) {
                ExtensionDao d = it.next();
                console.log(d.ID + "   (" + d.URI + ")  " + d.state.name());
            }
        } catch (Exception e) {
            Gdx.app.error("CE", "", e);
        }
    }

    public void load_ext(String id) {
        try {
            world.getSystem(ExtensionManager.class).loadExtensions(id);
        } catch (Exception e) {
            Gdx.app.error("CE", "", e);
        }
    }

    public void log_av_ext(String id) {
        ExtensionDao d = world.getSystem(ExtensionManager.class).findDao(id);
        if (d == null) {
            console.log("NULL");
            return;
        }
        console.log(d.ID + " ~ " + d.name.get());
        console.log(d.state.name());
        console.log(d.daos.toString());
        console.log(d.req.toString());
    }

    public void export_ext() {
        export_ext(null);
    }

    public void export_ext(String id) {

        Extension e = world.getSystem(ExtensionManager.class).getExt(id);

        StepLoader l = new StepLoader(world.getSystem(ExecutionSystem.class),
                new ResponseListener() {
                    @Override
                    public void onResponse(short code) {
                        console.log("exported");
                    }
                });

        ExtensionExporter exp = new ExtensionExporter(e, null);
        exp.setProvider(l);

        l.setDisableCC(1);

        l.reset();
        l.exec(exp);

    }

    /*
        Text test base
     */
    @Deprecated
    public void log_text() {
        log_text(null);
    }

    @Deprecated
    public void log_text(String id) {
        Extension ext = world.getSystem(ExtensionManager.class).getExt(id);
        TheTextSubExt e = ext.getSub(TheTextSubExt.class);
        if (e == null) return;
        console.log(e.t.toString());
    }

    @Deprecated
    public void set_text(String text) {
        set_text(text, null);
    }

    @Deprecated
    public void set_text(String text, String id) {
        Extension ext = world.getSystem(ExtensionManager.class).getExt(id);
        TheTextSubExt e = ext.getSub(TheTextSubExt.class);
        if (e == null) return;
        e.t.text = text;
    }

    /*
        Some res utils
     */

    public void log_etydwbc(int e) {
        try {
            DrawableC c = world.getMapper(DrawableC.class).get(e);
            console.log(c.ctr.toString());
        } catch (Exception er) {
            Gdx.app.error("CE", "", er);
        }
    }

    /**
     * Editor Renderer utils
     */

    public void log_ers() {

        StringBuilder b = new StringBuilder();

        for (SbsRenderer r : world.getSystem(SubsidiaryRenderSys.class).getRs()) {

            b.append(r.getClass().getSimpleName()).append("; \n ");

        }

        console.log(b.toString());


    }

    public void remove_rdr_by_place(String as, String os) {

        String[] a_s = as.split(",");
        int[] a = new int[a_s.length];
        for (int i = 0; i < a_s.length; i++) a[i] = Integer.parseInt(a_s[i]);


        String[] o_s = os.split(",");
        int[] o = new int[o_s.length];
        for (int i = 0; i < o_s.length; i++) o[i] = Integer.parseInt(o_s[i]);

        world.getSystem(SubsidiaryRenderSys.class).removeRdrByPlaceBK(a, o);

    }

    /**
     * Editmode utils
     */

    public void log_avem() {

        Iterator<EditModeDesc> ds = world.getSystem(EditorSystem.class).getEmDesc();


        EditModeDesc d;
        while (ds.hasNext()) {
            d = ds.next();
            console.log(d.name.get() + " [ " + d.id + " ] " + " (av " + d.available + ")" + " -> " + d.c.getSimpleName());
        }


    }

    public void log_emdesc() {

        EditModeDesc d = world.getSystem(EditorSystem.class).getCurModeDesc();

        if (d == null) console.log("NULL");
        else {
            console.log(d.c.getSimpleName());
        }
    }

    public void log_em() {


        EditMode m = world.getSystem(EditorSystem.class).getCurEM();

        if (m == null) {
            console.log("NULL");
            return;
        }

        console.log(m.getClass().getSimpleName());

    }

    public void em_set(int id) {

        try {
            world.getSystem(EditorSystem.class).attachNewEditMode(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void em_remove() {

        world.getSystem(EditorSystem.class).detachEditMode();

    }


    /**
     *
     * Entity log utils
     *
     */



    /*


     */


    public void log_cos() {

        console.log("Logging for  " + world.getSystem(EditorSystem.class).sel);

        CompositionObserver o;
        Iterator<CompositionObserver> i = world.getSystem(EditorSystem.class).getComObsI();
        while (i.hasNext()) {
            o = i.next();
            console.log(o.getName() + " [Lang dep]" + o.getClass().getSimpleName() + "(" + +o.ID + ")" + " :: " + (o.isSelActive() ? "Triggered" : "Unaffected"));

        }

    }

    public void log_cos_a(int id) {

        CompositionObserver o = world.getSystem(EditorSystem.class).getComOb(id);

        if (o == null) {
            console.log("Not found");
            return;
        }

        try {


            for (ActionDesc d : o.getDesc().values()) {

                console.log(d.code + " -> " + d.name.get() + "  || " + " aviab = " + o.isAviab(d.code));

            }
        } catch (Exception e) {

            Gdx.app.error("Console", "", e);

        }

    }

    public void exec_cos(int _e, int id, int a) {
        try {
            world.getSystem(EditorSystem.class).getComOb(id).execA(a, _e, false);
        } catch (Exception e) {
            console.log(e.getMessage());
            Gdx.app.error("", "EXEC_COS :: " + _e + " " + id + " :: " + a + " \n ", e);
        }
    }



    /**
     * Entity transform methods#
     */



    /**
     * AssetManager utils
     */

    public void log_mgr_stat() {
        AssetManager mgr = world.getInjector().getRegistered(AssetManager.class);
        console.log(mgr.getDiagnostics());
    }

    public void log_mgr_loaded(String path) {
        AssetManager mgr = world.getInjector().getRegistered(AssetManager.class);
        console.log("Loaded " + mgr.isLoaded(path));
    }


    /*
        AssetExt utils
     */


    public void log_asspc(String id) {
        AssetSubExtension as = world.getSystem(ExtensionManager.class).getSub(id, AssetSubExtension.class);
        if (as == null) {
            console.log("AssetSubExt not found");
            return;
        }

        TextureRAccesor ac;
        for (Iterator<TextureRAccesor> it = as.getAll(); it.hasNext(); ) {
            ac = it.next();
            String out = "";
            if (ac.atlasR.index == -1) {
                out += ac.atlasR.name;
            } else {
                out += ac.atlasR.name + "@ " + ac.atlasR.index;
            }
            out += " ~ usg:" + ac.usages;
            console.log(out);
        }
    }


    public void log_assgps(String id) {

        Extension ext = world.getSystem(ExtensionManager.class).getExt(id);

        if (ext == null || !ext.hasSub(AssetGroupSubExt.class)) {
            console.log("No AssetGroupSubExt");
            return;
        }

        AssetGroupSubExt ge = ext.getSub(AssetGroupSubExt.class);

        for (AssetGroup g : ge.getGroups()) {
            console.log(g.name.get() + " [ " + g.matcher.getClass().getSimpleName() + " ] -> " + g.size());
        }
    }



    /**
     *
     * Gfx utils like fps and delta time
     *
     *
     */


    public void pf_s() {

        world.getInvocationStrategy().setEnabled(world.getSystem(ConsoleSys.class), false);

        ((OmeStrategy) world.getInvocationStrategy()).fetchUpdtProfileD();


        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                world.getInvocationStrategy().setEnabled(world.getSystem(ConsoleSys.class), true);


            }
        }.start();

    }

    public void pf() {
        ((OmeStrategy) world.getInvocationStrategy()).fetchUpdtProfileD();
    }

    public void log_gpf() {

        OmeStrategy s = world.getInvocationStrategy();

        console.log("GL_CALLS    " + s.getGLProfileP(OmeStrategy.GL_PROFILE_PROP.GL_CLASS));
        console.log("DRAW_CALLS  " + s.getGLProfileP(OmeStrategy.GL_PROFILE_PROP.DRAW_CALLS));
        console.log("TEX_BINDS   " + s.getGLProfileP(OmeStrategy.GL_PROFILE_PROP.TEXS_BIND));
        console.log("SHADER_SW   " + s.getGLProfileP(OmeStrategy.GL_PROFILE_PROP.SHADER_SWITCHES));
        console.log("");
        console.log("FPS: " + Gdx.graphics.getFramesPerSecond());

    }

    public void log_fps() {
        console.log("FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    public void log_spf() {

        OmeStrategy s = world.getInvocationStrategy();

        for (Map.Entry<Class, Integer> e : s.getSysT().entrySet()) {
            console.log(e.getKey().getSimpleName() + "    " + e.getValue());
        }

    }


    /**
     * Cammy utils
     *
     */

    public void scpos(int x,int y){

        ((OrthographicCamera) world.getInjector().getRegistered("game_cam")).position.set(x, y, 0);

    }

    public void camscale() {

        console.log("" + GU.CAM_SCALE);

    }

    public void cam_etg(int _e) {
        cam_etg(_e, true);
    }

    public void cam_etg(int _e, boolean free) {
        Target.EntityPosTarget t = new Target.EntityPosTarget();
        t._e = _e;
        t.freeOnReach = free;
        t.ps = world.getSystem(PositionSystem.class);
        world.getSystem(CameraSys.class).setTarget(t);
    }

    public void cam_ptg(int x, int y) {
        Target.PosTarget t = new Target.PosTarget();
        t.coords = new Vector2(x, y);
        world.getSystem(CameraSys.class).setTarget(t);
    }

    public void cam_freet() {
        world.getSystem(CameraSys.class).setTarget(null);
    }

    /**
     * World System utilities
     */

    public void stopsys_n(String name) {
        manageSysState(name,false);
    }

    public void stopsys(int id) {
        world.getSystems().get(id - 1).setEnabled(false);
    }

    public void runsys_n(String name) {
        manageSysState(name,true);
    }

    public void runsys(int id) {
        world.getSystems().get(id - 1).setEnabled(true);
    }

    public void sys_state(int i) {
        BaseSystem s = world.getSystems().get(i - 1);
        if (s == null) {
            console.log("No system found");
        } else {
            console.log(s.isEnabled() ? "Running" : "Suspended");
        }
    }

    public void sys_state_n(String name) {
        BaseSystem s = findSystem(name);
        if(s == null){
            console.log("No system found");
        }else{
            console.log( s.isEnabled() ? "Running" : "Suspended");
        }
    }

    public void sys_states() {

        try {
            ImmutableBag<BaseSystem > s = world.getSystems();
            String name;
            for(int i = 0; i < s.size(); i++){

                name = s.get(i).getClass().getSimpleName();

                console.log(i + 1 + "  " + name + " - " + (s.get(i).isEnabled() ? "Running" : "Suspended"));

            }
        } catch (Exception e) {
            Gdx.app.error("", "", e);
        }

    }

    /*

    Location utils

     */


    /**
     * Input utils
     */
    public void log_ip() {


        Array<InputProcessor> cps = world.getInjector().getRegistered(InputMultiplexer.class).getProcessors();
        for (InputProcessor p : cps) {
            console.log(p.getClass().getSimpleName());
        }
    }


    public void log_ic() {

        InputController m = world.getSystem(InputSys.class).getMainIC();
        InputController d = world.getSystem(InputSys.class).getDefIC();

        if (m != null) {
            console.log("Main ic channel : " + m.getClass().getSimpleName());
        } else {
            console.log("Main ic channel : NULL");
        }

        if (d != null) {
            console.log("Def ic channel : " + d.getClass().getSimpleName());
        } else {
            console.log("Def ic channel : NULL");
        }


    }

    public void ic_sel() {
        world.getSystem(InputSys.class).setDefIC(new SelectIC());
    }

    public void ic_tsel() {
        world.getSystem(InputSys.class).setDefIC(new TimedSelectIC());
    }

    public void ic_mnull() {

        world.getSystem(InputSys.class).setMainIC(null);

    }

    public void ic_newl() {

        world.getSystem(InputSys.class).setMainIC(new NewMOIC());

    }

    /**
     * Selection utils
     */

    public void log_sel() {

        console.log("" + world.getSystem(EditorSystem.class).sel);

    }


    /**
     * Action utils
     */

    public void log_acts() {

        LinkedList<Action> as = world.getSystem(ActionSystem.class).getStack();

        for (int i = 0; i < as.size(); i++) {

            console.log("" + (i + 1) + " - " + as.get(i).getClass().getSimpleName());

        }

    }

    public void log_actsd() {

        LinkedList<Action> as = world.getSystem(ActionSystem.class).getStack();

        int il = String.valueOf(as.size()).length() + 1;

        for (int i = 0; i < as.size(); i++) {

            console.log(formatIntToStrCL(il, i + 1) + " - " + as.get(i).getClass().getSimpleName() + " - " +

                    (as.get(i).isUndoable() ? "Undoable" : "Not undoable ") + (as.get(i).isCleaner() ? "Cleaner" : ""));

        }

    }

    public void log_act_sts() {

        console.log(world.getSystem(ActionSystem.class).getStack().size() + " of " + world.getSystem(ActionSystem.class).getMaxStackSize());

    }

    public void set_act_sts(int i) {

        world.getSystem(ActionSystem.class).setMaxStackSize(i);

    }

    public void undoa() {

        try {

            world.getSystem(ActionSystem.class).undo();

        } catch (Exception e) {

            console.log(e.toString());

        }


    }

    // adds

    /**
     * UI UTILS
     */


    public void log_ui() {

        ObjectMap.Entries<String, BaseView> i = AppDO.I.LML().getVI();

        ObjectMap.Entry<String, BaseView> n;

        while (i.hasNext()) {
            n = i.next();
            console.log(n.key + " :: " + (n.value.active ? "active" : "available"));
        }

    }

    public void openwin(String code) {
	  try {
		world.getSystem(UiSystem.class).openWin(code);
	  } catch (Exception e) {
		Gdx.app.error("CC", "", e);
	  }
    }

    public void closewin() {
	  world.getSystem(UiSystem.class).closeWin();
    }

    /*
    * Map utils
    */

    public void load_map(String path) {

        world.getSystem(MapMgr.class).loadMap(path);

    }





    /**
     * Time utils
     */

    public void log_curt() {

        console.log("Time " + world.getSystem(TimeMgr.class).getTime());

    }

    public void time() {

        console.log("" + world.getSystem(TimeMgr.class).getTime());

    }

    public void t_isnow(int d1, int d2) {

        console.log("" + world.getSystem(TimeMgr.class).isNow(d1, d2));

    }


    /**
     * Entity Utils
     */

    public void log_ec() {

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all()).getEntities();

        console.log("Current N(E) : " + es.size());
    }

    public void log_es() {

        log_es(0, Integer.MAX_VALUE);


    }

    public void log_es(int s){

        log_es(s, Integer.MAX_VALUE);

    }

    public void log_es(int s, int e) {

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all()).getEntities();


        int i = s;
        int l = Math.min(e, es.size());

        console.log("Logging from " + s + " " + l);

        int _e;

        Array<String> out = new Array<String>();
        String c = "";

        Bag b = new Bag();

        while (i < l) {

            _e = es.get(i);

            b.clear();
            b = world.getEntity(_e).getComponents(b);

            c = c.concat("" + _e + "(" + world.getEntity(_e).getCompositionId() + ") ");
            for (int j = 0; j < b.size(); j++) {
                c = c.concat(b.get(j).getClass().getSimpleName());
                c = c.concat(" & ");
            }
            c = c.concat(" ::  ");

            if (i % 2 == 0 || i == l - 1) {
                out.add(c);
                c = "";
            }

            i++;
        }


        for (String ous : out){

            console.log(ous);

        }

    }


    /**
     * Here are the bigger method implementations
     */

    private BaseSystem findSystem(String name){
        for(BaseSystem sys : world.getSystems()){
            if(sys.getClass().getSimpleName().equals(name)){
                return sys;
            }
        }
        return null;
    }

    private void manageSysState(String name, boolean state){

        BaseSystem s = findSystem(name);

        if(s == null)
            console.log("No system found");
        else
            s.setEnabled(state);
    }

    private String formatIntToStrCL(int l, int val){
        return String.format("%1$" + l + "time_s", String.valueOf(val));
    }

    private String formatString(int l, String s){
        return String.format("%1$" + l + "time_s", s);
    }


    private String extendString(String name, int l) {

        String o = new String();

        o = o.concat(name);

        for (int i = 0; i < l - name.length(); i++) {
            o = o.concat(" ");
        }
        return o;

    }


}
