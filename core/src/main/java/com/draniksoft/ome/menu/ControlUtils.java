package com.draniksoft.ome.menu;

import com.draniksoft.ome.editor.EditorAdapter;
import com.draniksoft.ome.editor.launch.LaunchBundle;
import com.draniksoft.ome.utils.GUtils;

public class ControlUtils {

    public static void launchEditor(LaunchBundle b){

        GUtils.getWindow().closeWindow();

        GUtils.getApp().newWindow(new EditorAdapter(b),GUtils.getEditorConfig());

    }

}
