package com.draniksoft.ome.editor.support;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.draniksoft.ome.editor.components.pos.PhysC;
import com.draniksoft.ome.editor.components.state.InactiveC;
import com.draniksoft.ome.editor.components.state.TInactiveC;
import com.draniksoft.ome.editor.components.supp.SelectionC;
import com.draniksoft.ome.editor.components.time.TimeC;
import com.draniksoft.ome.editor.components.tps.LocationC;
import com.draniksoft.ome.editor.components.tps.MapC;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.manager.ProjectMgr;
import com.draniksoft.ome.editor.manager.TimeMgr;
import com.draniksoft.ome.editor.support.actions.Action;
import com.draniksoft.ome.editor.support.actions.loc.AddTimeC;
import com.draniksoft.ome.editor.support.actions.loc.CreateLocationA;
import com.draniksoft.ome.editor.support.input.CreateLocIC;
import com.draniksoft.ome.editor.support.input.InputController;
import com.draniksoft.ome.editor.support.input.SelectIC;
import com.draniksoft.ome.editor.support.input.TimedSelectIC;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;
import com.draniksoft.ome.editor.systems.support.ActionSystem;
import com.draniksoft.ome.editor.systems.support.InputSys;

import java.util.LinkedList;

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
     * FIle mgmnt utils
     */

    public void load(String p) {

        world.getSystem(ProjecetLoadSys.class).setBundle(new MapLoadBundle(p));
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

        world.getSystem(ActionSystem.class).exec(new CreateLocationA(x, y, h, w, txt));

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

        world.getSystem(InputSys.class).setMainIC(new CreateLocIC());

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


    public void add_tc(int _e, int se, int ee) {

        world.getSystem(ActionSystem.class).exec(new AddTimeC(_e, se, ee));

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

    public void log_es(int s, int e) {

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all()).getEntities();


        int i = s;
        int l = Math.min(e, es.size());

        console.log("Logging from " + s + " " + l);

        int _e;
        while (i < l) {

            _e = es.get(i);

            console.log(i + " : " +
                    (world.getMapper(LocationC.class).has(_e) ? "Location, " : " ") +
                    (world.getMapper(MapC.class).has(_e) ? "Map, " : " ") +
                    (world.getMapper(SelectionC.class).has(_e) ? "Selected, " : " ") +
                    (world.getMapper(PhysC.class).has(_e) ? "Body, " : " ") +
                    (world.getMapper(TimeC.class).has(_e) ? "Timed, " : " ") +
                    (world.getMapper(TInactiveC.class).has(_e) ? "Timed::Inactive, " : " ") +
                    (world.getMapper(InactiveC.class).has(_e) ? "Inactive, " : " ")
            );

            i++;
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
