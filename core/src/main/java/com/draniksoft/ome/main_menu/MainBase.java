package com.draniksoft.ome.main_menu;

import com.badlogic.gdx.Game;

public class MainBase extends Game {

    @Override
    public void create() {

        setScreen(new MenuScreen());

    }


}
