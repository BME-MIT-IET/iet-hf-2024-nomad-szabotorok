package hu.bme.mit.iet.pipe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PumpView extends ViewBase{
    private Pump pump;
    private JButton button;
    private ArrayList<JLabel> players;

    /**
     * Konstruktor, ami létrehozza az osztály grafikus megjelenéséhez szükséges swing elemeket
     * @param view
     * @param pump
     */
    public PumpView(View view, Pump pump){
        super(view);
        this.pump = pump;
        button = new JButton();
        button.addActionListener(this);
        players = new ArrayList<>();
    }

    /**
     * grafikusan frissíti az adott példányt a jelenlegi állapotnak megfelelően
     * játékosok újrahelyezése
     */
    @Override
    public void update() {
        button.setBounds(x,y,50,50);
        button.setBackground(new Color(89, 158, 227,255));
        if (pump.isBroken()) {
            button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
        }
        else {
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }

        /**
         * Eloszor levesszuk az elozo dolgokat
         */
        view.getPanel().remove(button);

        while (!players.isEmpty()){
            view.getPanel().remove(players.get(0));
            players.remove(0);
        }
        /**
         * Majd feltesszuk oket a pane-re
         */
        for (int i = 0; i < pump.players.size(); ++i){
            JLabel player = new JLabel(pump.players.get(i).getId());
            player.setFont(new Font("Arial", Font.BOLD, 10));
            if (i < 3)
                player.setBounds(x+1+i*16, y, 16, 16);
            else
                player.setBounds(x+1+(i-3)*16, y+32, 16, 16);
            players.add(player);
            view.getPanel().add(player, Integer.valueOf(2));
        }

        view.getPanel().add(button, Integer.valueOf(1));
    }

    /**
     * getter függvény
     * @return
     */
    @Override
    public String getID(){
        return pump.getId();
    }

    /**
     * ActionListener interface megvalósítása
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
        action(pump.getId());
    }
}