package com.draniksoft.ome.lwjgl3.awt_utils;

import javax.swing.*;

public class ErrorDialog extends JFrame {

    public ErrorDialog(){


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton b=new JButton("click me");
        b.setBounds(30,100,80,30);// setting button position
        add(b);//adding button into frame
        setSize(300,300);//frame size 300 width and 300 height
        setLayout(null);//no layout manager
        setVisible(true);//now frame will be visible, by default not visible

    }

}
