package hu.bme.mit.iet.pipe_game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /**
         * Létrehozzuk a GUIControl-t, amely a játék grafikus futtatásáért felelős.
         * Ennek a Run függvénye futtatja a játékot(Azaz először a beállításokat, majd a játékot)
         */
        GUIControl control = new GUIControl();
        control.Run();
    }
}