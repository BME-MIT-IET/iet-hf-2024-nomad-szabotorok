package hu.bme.mit.iet.pipe_game;

public class Main {
    public static void main(String[] args) {
        /**
         * Létrehozzuk a GUIControl-t, amely a játék grafikus futtatásáért felelős.
         * Ennek a Run függvénye futtatja a játékot(Azaz először a beállításokat, majd a játékot)
         */
        GUIControl control = new GUIControl();
        control.run();
    }
}