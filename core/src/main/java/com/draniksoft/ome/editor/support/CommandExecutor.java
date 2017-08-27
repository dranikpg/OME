package com.draniksoft.ome.editor.support;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.launch.MapLoadBundle;
import com.draniksoft.ome.editor.manager.ProjectManager;
import com.draniksoft.ome.editor.systems.file_mgmnt.ProjecetLoadSys;

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

        console.log(world.getSystem(ProjectManager.class).getName());

    }

    public void sname(String n) {

        world.getSystem(ProjectManager.class).setmName(n);

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

    public void logperf(){

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

    public void sys_state(String name) {
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
