package com.draniksoft.ome.editor.support;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.draniksoft.ome.editor.components.gfx.DrawableC;
import com.draniksoft.ome.editor.components.supp.SelectionC;
import com.draniksoft.ome.editor.components.time.TimedMoveC;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.manager.DrawableMgr;
import com.draniksoft.ome.editor.manager.ProjectMgr;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.actions.loc.AddTimeC;
import com.draniksoft.ome.editor.support.actions.loc.CreateLocationA;
import com.draniksoft.ome.editor.support.container.MoveDesc;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.input.NewLocIC;
import com.draniksoft.ome.editor.support.input.SelectIC;
import com.draniksoft.ome.editor.support.input.TimedSelectIC;
import com.draniksoft.ome.editor.support.render.core.OverlyRendererI;
import com.draniksoft.ome.editor.support.workflow.EditMode;
import com.draniksoft.ome.editor.support.workflow.NewLocEditMode;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;
import com.draniksoft.ome.editor.systems.pos.PhysicsSys;
import com.draniksoft.ome.editor.systems.render.editor.OverlayRenderSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;
import com.draniksoft.ome.editor.systems.support.WorkflowSystem;
import com.draniksoft.ome.mgmnt_base.AppDataObserver;
import com.draniksoft.ome.utils.Const;
import com.draniksoft.ome.utils.Env;
import com.draniksoft.ome.utils.dao.AssetDDao;
import com.draniksoft.ome.utils.struct.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
     * Environment utils
     */


    public void denv_setb(String n, int b) throws NoSuchFieldException, IllegalAccessException {

        Field f = Env.class.getDeclaredField(n);

        f.setBoolean(null, b == 1);


    }

    public void log_denv() throws IllegalAccessException {

        Field[] fields = Env.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                console.log(f.getName() + " :: " + f.getBoolean(null));
            }
        }

    }

    public void log_appv() {

        console.log("" + Const.appVersion);

    }

    public void log_appfn() {

        console.log(Const.appVFullName);

    }

    /**
     * FIle mgmnt utils
     */
    public void log_ophis() {

        console.log(AppDataObserver.getI().getOpngHisM().getLastOpAr().toString());

    }

    public void load(String p) {

        world.getSystem(ProjecetLoadSys.class).setBundle(new MapLoadBundle(p));
        world.getSystem(ProjecetLoadSys.class).load();

    }

    public void load_h(int i) {

        world.getSystem(ProjecetLoadSys.class).setBundle(new MapLoadBundle(

                AppDataObserver.getI().getOpngHisM().getLastOpAr().get(i)

        ));
        world.getSystem(ProjecetLoadSys.class).load();

    }

    public void save() {

        world.getSystem(ProjecetLoadSys.class).save();

    }

    public void gname() {

        console.log(world.getSystem(ProjectMgr.class).getName());

    }

    public void sname(String n) {

        world.getSystem(ProjectMgr.class).setmName(n);

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

    public void log_em() {


        EditMode m = world.getSystem(WorkflowSystem.class).getCurEM();

        if (m == null) {
            console.log("NULL");
            return;
        }

        console.log(m.getClass().getSimpleName());

    }

    public void em_newL() {

        world.getSystem(WorkflowSystem.class).attachEditMode(new NewLocEditMode());

    }


    /**
     *
     * Entity log utils
     *
     */

    public void le_ppos(int _e) {

        Pair<Float, Float> p = world.getSystem(PhysicsSys.class).getPos(_e);

        console.log("" + p.getElement0() + " " + p.getElement1());

    }


    /**
     * Entity transform methods
     */

    public void te_setdwbn(int e, String newN) {

        try {

            DrawableC c = world.getMapper(DrawableC.class).get(e);

            c.d = new TextureRegionDrawable(world.getSystem(DrawableMgr.class).getRegion(newN));

        } catch (Exception ex) {

            Gdx.app.error("CM", "", ex);

        }

    }


    public void te_addtc(int _e, int se, int ee) {

        world.getSystem(ActionSystem.class).exec(new AddTimeC(_e, se, ee));

    }

    public void te_addMoveC(int s, int e, int x, int y, int _e) {

        ComponentMapper<TimedMoveC> cm = world.getMapper(TimedMoveC.class);

        if (!cm.has(_e)) {
            cm.create(_e);
            cm.get(_e).a = new Array<MoveDesc>();
        }

        MoveDesc d = new MoveDesc();
        d.s = s;
        d.e = e;
        d.x = x;
        d.y = y;
        Pair<Float, Float> p = world.getSystem(PhysicsSys.class).getPos(_e);
        d.sx =  p.getElement0().intValue();
        d.sy = p.getElement1().intValue();

        cm.get(_e).a.add(d);


    }

    /**
     * AssetDManager utils
     */

    public void log_locassl() {

        console.log("Items at :: " + AppDataObserver.getI().getAssetDM().getDirP());

        for (AssetDDao dao : AppDataObserver.getI().getAssetDM().getDaos()) {

            console.log("Name = " + dao.name + "; uri = " + dao.uri + "; ID = " + dao.id);

        }


    }

    public void load_exassbyid(int i) {

        world.getSystem(DrawableMgr.class).loadDwbl(
                AppDataObserver.getI().getAssetDM().getDaos().get(i)
        );

    }

    public void log_avass() {

        Array<AssetDDao> ds = world.getSystem(DrawableMgr.class).getAllAviabDao();

        for (AssetDDao d : ds) {

            console.log(d.id + "  [" + d.uri + "]");

        }

    }

    public void log_ass() {


        ObjectMap<String, AssetDDao> d = world.getSystem(DrawableMgr.class).getAllDescI();

        for (String id : d.keys()) {

            console.log(d.get(id).name + " ~uri=" + d.get(id).uri + " ~id=" + d.get(id).id);

        }

    }

    public void log_asspc(String id) {

        Array<TextureAtlas.AtlasRegion> rs = world.getSystem(DrawableMgr.class).getAtlas(id).getRegions();


        for (TextureAtlas.AtlasRegion r : rs) {

            console.log("name= " + r.name + "; ~id = " + r.index);

        }


    }


    public void log_assrds() {

        Map<String, String> m = world.getSystem(DrawableMgr.class).getRedirects();

        for (Map.Entry<String, String> e : m.entrySet()) {

            console.log(e.getKey() + " = " + e.getValue());

        }

    }


    /**
     *
     * Gfx utils like fps and delta time
     *
     *
     */


    public void gdlta(){
        console.log(Float.toString(Gdx.graphics.getDeltaTime()));
    }

    public void log_perf() {

        console.log(((PMIStrategy) world.getInvocationStrategy()).getO());

    }

    public void logmaxperf() {

        console.log(((PMIStrategy) world.getInvocationStrategy()).getMO());

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

        ImmutableBag<BaseSystem > s = world.getSystems();
        String name;

        int il = String.valueOf(s.size()).length() + 1;
        int sl = 0;

        for(int i = 0; i < s.size(); i ++){
            sl = Math.max(s.get(i).getClass().getSimpleName().length(),sl);
        }

        for(int i = 0; i < s.size(); i++){

            name = s.get(i).getClass().getSimpleName();

            console.log(formatIntToStrCL(il,i+1) + " " + formatString(sl,name) + " - " + (s.get(i).isEnabled() ? "Running" : "Suspended"));

        }

    }

    /*

    Location utils

     */


    public void newl(int x, int y, int h, int w, String txt) {

        CreateLocationA a = new CreateLocationA(x, y, w, h);
        a.dwbIU = txt;
        world.getSystem(ActionSystem.class).exec(a);

    }

    /**
     * Input utils
     */

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

        world.getSystem(InputSys.class).setMainIC(new NewLocIC());

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
        return  String.format("%1$"+l+ "s", String.valueOf(val));
    }

    private String formatString(int l, String s){
        return String.format("%1$"+l+ "s", s);
    }

}
