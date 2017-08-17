package com.draniksoft.ome.editor.support;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.draniksoft.ome.editor.components.MapC;
import com.draniksoft.ome.editor.components.PosSizeC;
import com.draniksoft.ome.editor.components.TexRegC;
import com.draniksoft.ome.editor.esc_utils.PMIStrategy;
import com.draniksoft.ome.editor.manager.ArchTransmuterMgr;

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

    public void mapchrdr(boolean rdr){

    }

    /**
     * Cammy utils
     *
     */

    public void scpos(int x,int y){

        world.getInjector().getRegistered(OrthographicCamera.class).position.set(x,y,0);

    }

    public void sczoom(float z){

        world.getInjector().getRegistered(OrthographicCamera.class).zoom = (z/100f);

    }


    /**
     * World System utilities
     */

    public void stopsys(String name){
        manageSysState(name,false);
    }

    public void runsys(String name){
        manageSysState(name,true);
    }

    public void sys_stat(String name){
        BaseSystem s = findSystem(name);
        if(s == null){
            console.log("No system found");
        }else{
            console.log( s.isEnabled() ? "Running" : "Suspended");
        }
    }

    public void list_sys_states(){

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

    public void loadMap(int w, int h) {

        IntBag es = world.getAspectSubscriptionManager().get(Aspect.all(MapC.class)).getEntities();

        for(int i = 0; i < es.size(); i ++ ){
            world.delete(es.get(i));
        }

        Texture t = new Texture(Gdx.files.absolute("/media/vlad/Second/dev/tmp/OME_C/map.png"));

        TextureRegion[][] ts = TextureRegion.split(t,w,h);

        for(int i = 0; i < ts.length; i++){

            for(int j = 0; j < ts.length; j++){

                int e = world.getSystem(ArchTransmuterMgr.class).build(ArchTransmuterMgr.Codes.MAP_C);

                TexRegC dc = world.getMapper(TexRegC.class).get(e);
                dc.d = ts[i][j];

                PosSizeC ps = world.getMapper(PosSizeC.class).get(e);
                ps.x = j*w;
                ps.y = t.getHeight() - (i*h);
                ps.w = w;
                ps.h = h;

            }

        }


    }

}
