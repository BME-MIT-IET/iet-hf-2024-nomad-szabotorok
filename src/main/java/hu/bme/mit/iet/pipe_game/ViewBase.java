package hu.bme.mit.iet.pipe_game;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public abstract class ViewBase implements ActionListener {
    protected int x;
    protected int y;
    protected View view;

    /**
     * konstruktor, ami beállítja a view elemet
     * @param view
     */
    protected ViewBase(View view){
        this.view = view;

    }

    /**
     * a paraméterben kapott string-et feldolgozza, mint commandot
     * @param input
     */
    public void action(String input){
        List<String> temp = view.getTempCommand();
        String[] temp2 = input.split(" ");
        ArrayList<String> togive = new ArrayList<>(Arrays.asList(temp2));
        if(!temp.isEmpty()) {
            if (temp.get(0).equals("carrypipe") || temp.get(0).equals("move")) {
                view.addCommand(togive.get(0));
                view.addCommand(togive.get(1));
                view.manageLongCommand();
            } else if (temp.get(0).equals("pumpchangeflow")) {
                if (temp.size() == 2) {
                    view.addCommand(togive.get(0));
                    view.addCommand(togive.get(1));
                    view.manageLongCommand();
                } else if (temp.size() == 1)
                    view.addCommand(input);
            }
        }
    }

    /**
     * Felul kell definialni leszarmazzott View-kban
     */
    public abstract void update();

    /**
     * setter függvény, ami átállítja a koordinátákat
     * @param x
     * @param y
     */
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter függvény
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * getter függvény
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * getter függvény
     * @return
     */
    public String getID(){
        return "";
    }
    }
