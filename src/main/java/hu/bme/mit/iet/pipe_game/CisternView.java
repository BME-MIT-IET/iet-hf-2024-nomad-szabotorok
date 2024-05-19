package hu.bme.mit.iet.pipe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CisternView extends ViewBase{
    private Cistern cistern;
    private JButton button;
    private JButton part;
    private ArrayList<JLabel> players;

    /**
     * Konstruktor, ami létrehozza az osztály grafikus megjelenéséhez szükséges swing elemeket
     * @param view
     * @param cistern
     */
    public CisternView(View view, Cistern cistern) {
        super(view);
        this.cistern = cistern;
        button = new JButton();
        part = new JButton();
        button.addActionListener(this);
        players = new ArrayList<>();
    }

    /**
     * Grafikusan rissíti a példány állapotát:
     * leszedi a rajta levő játékosokat, majd helyesen felteszi őket
     * frissíti a part JButtont a jelenlegi állapotoknak megfelelően
     */
    @Override
    public void update() {
        button.setBounds(x,y,50,50);
        button.setBackground(Color.RED);

        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


        /** Eloszor leszedjuk az elozo dolgokat a pane-rol
         */
        view.getPanel().remove(button);

        while (!players.isEmpty()){
            view.getPanel().remove(players.get(0));
            players.remove(0);
        }

        /** Feltesszuk a dolgokat a pane-re
         */
        for (int i = 0; i < cistern.players.size(); ++i){
            JLabel player = new JLabel(cistern.players.get(i).getId());
            player.setFont(new Font("Arial", Font.BOLD, 10));
            if (i < 3)
                player.setBounds(x+1+i*16, y, 16, 16);
            else
                player.setBounds(x+1+(i-3)*16, y+32, 16, 16);
            players.add(player);
            view.getPanel().add(player, Integer.valueOf(2));
        }

        view.getPanel().add(button, Integer.valueOf(1));

        view.getPanel().remove(part);

        /** Ha van pumpa a ciszternan, azt is feltesszuk
         */
        if(cistern.hasPump()) {
            part.setBounds(x + 40, y + 40, 23, 23);
            part.setBackground(new Color(89, 158, 227,255));
            part.setEnabled(false);
            view.getPanel().add(part, Integer.valueOf(2));
        }

    }

    /**
     * getter fügvény ID-re
     * @return ID-t adja vissza
     */
    @Override
    public String getID(){
        return cistern.getId();
    }

    /**
     * ActionListener interface megvalósítása
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
       action(cistern.getId());
    }
}
