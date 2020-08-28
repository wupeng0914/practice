package com.marvel.tank;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        TankFrame frame = TankFrame.INSTANCE;
        frame.setVisible(true);
        for (;;){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.repaint();
        }
    }
}
