package hu.bme.mit.iet.pipe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class WaterSourceView extends ViewBase {
    private WaterSource waterSource;
    private JButton button;
    private ArrayList<JLabel> players;

    /**
     * Konstruktor, ami létrehozza az osztály grafikus megjelenéséhez szükséges swing elemeket
     * @param view
     * @param waterSource
     */
    public WaterSourceView(View view, WaterSource waterSource) {
        super(view);
        this.waterSource = waterSource;
        button = new JButton();
        button.addActionListener(this);
        players = new ArrayList<>();
    }

    /**
     * Grafikusan frissíti a példány állapotát:
     * leszedi a rajta levő játékosokat, majd helyesen felteszi őket újra
     */
    @Override
    public void update() {
        button.setBounds(x, y, 50, 50);
        button.setBackground(Color.GREEN);

        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        view.getPanel().remove(button);

        while (!players.isEmpty()){
            view.getPanel().remove(players.get(0));
            players.remove(0);
        }

        for (int i = 0; i < waterSource.players.size(); ++i) {
            JLabel player = new JLabel(waterSource.players.get(i).getId());
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
    public String getID() {
        return waterSource.getId();
    }

    /**
     * megvalósítja az ActionListener interface-t
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
        action(waterSource.getId());
    }
}