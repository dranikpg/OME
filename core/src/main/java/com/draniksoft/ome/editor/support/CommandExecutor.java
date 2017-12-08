package com.draniksoft.ome.editor.support;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.components.selection.SelectionC;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.esc_utils.OmeStrategy;
import com.draniksoft.ome.editor.load.MapLoadBundle;
import com.draniksoft.ome.editor.manager.MapMgr;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.manager.drawable.SimpleDrawableMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.actions.mapO.ChangeDwbA;
import com.draniksoft.ome.editor.support.compositionObserver.abstr.CompositionObserver;
import com.draniksoft.ome.editor.support.container.CO_actiondesc.ActionDesc;
import com.draniksoft.ome.editor.support.container.EM_desc.EditModeDesc;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.ems.core.EditMode;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.input.NewMOIC;
import com.draniksoft.ome.editor.support.input.SelectIC;
import com.draniksoft.ome.editor.support.input.TimedSelectIC;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjectLoadSystem;
import com.draniksoft.ome.editor.systems.gui.UiSystem;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.*;
import com.draniksoft.ome.mgmnt_base.base.AppDO;
import com.draniksoft.ome.mgmnt_base.impl.ConfigManager;
import com.draniksoft.ome.support.configs.ConfigDao;
import com.draniksoft.ome.utils.Const;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.dao.AssetDDao;
import com.draniksoft.ome.utils.struct.Pair;

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

    /**
     * Show edit mode urils
     */

    public void set_mode(boolean m) {

        try {

            world.getSystem(WorkflowSys.class).switchMode(m);

        } catch (Exception e) {
            Gdx.app.error("", "", e);
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

    /**
     * Editor Renderer utils
     */

    public void log_ers() {

        StringBuilder b = new StringBuilder();

        for (OverlyRendererI r : world.getSystem(OverlayRenderSys.class).getRs()) {

            b.append(r.getId()).append("; ");

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

        world.getSystem(OverlayRenderSys.class).removeRdrByPlace(a, o);

    }

    /**
     * Editmode utils
     */

    public void log_avem() {

        Iterator<EditModeDesc> ds = world.getSystem(EditorSystem.class).getEmDesc();


        EditModeDesc d;
        while (ds.hasNext()) {
            d = ds.next();
            console.log(d.getName() + " [ " + d.id + " ] " + " (av" + d.aviabT + ")" + " -> " + d.c.getSimpleName());
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

    public void le_ppos(int _e) {

        Pair<Float, Float> p = world.getSystem(PhysicsSys.class).getPhysPos(_e);

        console.log("" + p.getElement0() + " " + p.getElement1());

    }

    /*


     */


    public void log_cos() {

        console.log("Logging for  " + world.getSystem(EditorSystem.class).sel);

        CompositionObserver o;
        Iterator<CompositionObserver> i = world.getSystem(EditorSystem.class).getComObsI();
        while (i.hasNext()) {
            o = i.next();
            console.log(o.getName() + " [Lang dep]" + o.getClass().getSimpleName() + "(" + +o.id + ")" + " :: " + (o.isSelActive() ? "Triggered" : "Unaffected"));

        }

    }

    public void log_cos_acts(int id) {

        CompositionObserver o = world.getSystem(EditorSystem.class).getComOb(id);

        if (o == null) {
            console.log("Not found");
            return;
        }

        try {


            for (ActionDesc d : o.getDesc().values()) {

                console.log(d.code + " -> " + d.getName() + "  || " + " nooarg = " + d.noargpsb + " aviab = " + o.isAviab(d.code));

            }
        } catch (Exception e) {

            Gdx.app.error("Console", "", e);

        }

    }




    /**
     * Entity transform methods
     */

    public void te_sd(int e, String newN) {

        ChangeDwbA a = new ChangeDwbA();
        a._e = e;
        a.dwid = newN;

        world.getSystem(ActionSystem.class).exec(a);

    }


    public void te_addMoveC(int s, int e, int x, int y, int _e) {

        try {
            ComponentMapper<TimedMoveC> cm = world.getMapper(TimedMoveC.class);

            if (!cm.has(_e)) {
                cm.create(_e);
                cm.get(_e).a = new Array<MoveDesc>();
            }

            MoveDesc d = new MoveDesc();
            d.time_s = s;
            d.time_e = e;
            d.end_x = x;
            d.end_y = y;
            Pair<Float, Float> p = world.getSystem(PhysicsSys.class).getPhysPos(_e);
            d.start_x = p.getElement0().intValue();
            d.start_y = p.getElement1().intValue();

            cm.get(_e).a.add(d);
        } catch (Exception er) {
            Gdx.app.error("", ",", er);
        }

    }

    /**
     * AssetDManager utils
     */


    public void log_avass() {

        Iterator<AssetDDao> i = world.getSystem(SimpleDrawableMgr.class).getAviabDaoI();

        AssetDDao d;
        while (i.hasNext()) {
            d = i.next();
            console.log(d.id + "  [" + d.uri + "]");
        }

    }

    public void log_ass() {


        try {

            Iterator<AssetDDao> i = world.getSystem(SimpleDrawableMgr.class).getLoadedDaoI();

            AssetDDao d;
            while (i.hasNext()) {
                d = i.next();
                console.log(d.id + " [" + d.uri + "]");
            }
        } catch (Exception e) {
            Gdx.app.error("", "", e);
        }

    }

    public void log_asspc(String id) {

        Array<TextureAtlas.AtlasRegion> rs = world.getSystem(SimpleDrawableMgr.class).getAtlas(id).getRegions();


        for (TextureAtlas.AtlasRegion r : rs) {

            console.log("name= " + r.name + "; ~id = " + r.index);

        }


    }


    public void log_assrds() {

        Iterator<ObjectMap.Entry<String, String>> m = world.getSystem(SimpleDrawableMgr.class).getRedirectsI();

        ObjectMap.Entry<String, String> e;

        while (m.hasNext()) {
            e = m.next();
            console.log(e.key + " = " + e.value);

        }

    }

    public void load_ass(String id) {

        AssetDDao d = world.getSystem(SimpleDrawableMgr.class).getAviabDao(id);

        if (d != null) {

            world.getSystem(SimpleDrawableMgr.class).loadDao(d);

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

    public void sczoom(float z){

        ((OrthographicCamera) world.getInjector().getRegistered("game_cam")).zoom = (z / 100f);

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

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all(SelectionC.class)).getEntities();

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < es.size(); i++) {

            out.append(es.get(i)).append("  ");

        }

        console.log(out.toString());

    }


    /**
     * Action utils
     */

    public void log_acts() {

        LinkedList<Action> as = world.getSystem(ActionSystem.class).getStack();

        int il = String.valueOf(as.size()).length() + 1;

        for (int i = 0; i < as.size(); i++) {

            console.log(formatIntToStrCL(il, i + 1) + " - " + as.get(i).getClass().getSimpleName());

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

    public void log_astd() {

        console.log(world.getSystem(ActionSystem.class).getStack().size() + " of " + world.getSystem(ActionSystem.class).getMaxStackSize());

    }

    public void set_asts(int i) {

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
