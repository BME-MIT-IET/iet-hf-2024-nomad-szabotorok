package hu.bme.mit.iet.pipe_game;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class PipeView extends ViewBase {
    private Pipe pipe;
    private JButton button;
    private JLabel nev;

    /**
     * Konstruktor, ami létrehozza az osztály grafikus megjelenéséhez szükséges swing elemeket
     * @param view
     * @param pipe
     */
    public PipeView(View view, Pipe pipe){
        super(view);
        this.pipe = pipe;
        button = new JButton();
        button.addActionListener(this);
        nev = new JLabel();
    }

    public void drawFields(ArrayList<ViewBase> fields, String id, int i) {
        for (ViewBase f: fields) {
            if (id.equals(f.getID())){
                int vX = x-f.getX();
                int vY = y-f.getY();
                double hossz = sqrt((x-f.getX())*(x-f.getX())+(double)(y-f.getY())*(y-f.getY()));
                int eX = (int) ((vX/hossz)*26);
                int eY = (int) ((vY/hossz)*26);
                Line2D line = new Line2D.Float(x+25-(float)eX,y+25-(float)eY,f.getX()+25+(float)eX,f.getY()+25+(float)eY);
                view.addLine(line);

                String[] splitted = id.split(" ");
                if (splitted[0].equals("pump")) {
                    SystemPart pump = pipe.getNeighbours().get(i);

                    if (pump.getFrom() != null && pump.getFrom().equals(pipe.getId())) {
                        Line2D lineend1 = new Line2D.Float(f.getX() + 25 + (float)eX, f.getY() + 25 + (float)eY, f.getX() + 25 + 2 * eX - (float)eY / 2, f.getY() + 25 + 2 * eY + (float)eX / 2);
                        Line2D lineend2 = new Line2D.Float(f.getX() + 25 + 2 * eX + (float)eY / 2, f.getY() + 25 + 2 * eY - (float)eX / 2, f.getX() + 25 + (float)eX, f.getY() + 25 + (float)eY);
                        view.addLine(lineend1);
                        view.addLine(lineend2);
                    }
                    else if (pump.getTo() != null && pump.getTo().equals(pipe.getId())) {
                        Line2D lineend1 = new Line2D.Float(x + 25 - (float)eX, y + 25 - (float)eY, x+ 25 - 2 * eX - (float)eY / 2, y+ 25 - 2 * eY + (float)eX / 2);
                        Line2D lineend2 = new Line2D.Float(x + 25 - 2 * eX + (float)eY / 2, y + 25 - 2 * eY - (float)eX / 2, x + 25 - (float)eX, y + 25 - (float)eY);
                        view.addLine(lineend1);
                        view.addLine(lineend2);
                    }
                }
            }
        }
    }

    public void drawLinesAndArrows() {
        ArrayList<ViewBase> fields = view.getFields();
        for (int i = 0; i < pipe.neighbours.size(); ++i){
            String id = pipe.getNeighbours().get(i).getId();
            drawFields(fields, id, i);
        }
    }

    /**
     * A példány jelenlegi állapotához kötődő grafikus frissítés
     * effectek beállítása
     * játékosok helyes feltétele a jelenlegi állapotnak megfelelően
     */
    @Override
    public void Update() {
        button.setBounds(x, y, 50, 50);
        button.setBackground(Color.LIGHT_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setMargin(new Insets(0,0,0,0));
        view.getPanel().remove(button);

        if (pipe.isBroken() && pipe.isSlippery())
            button.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, new Color(80, 238, 238,255)), BorderFactory.createMatteBorder(0, 0, 3, 3, new Color(255, 140, 0,255))));
        else if (pipe.isBroken() && pipe.isGlued())
            button.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, new Color(50, 96, 21,255)), BorderFactory.createMatteBorder(0, 0, 3, 3, new Color(255, 140, 0,255))));
        else if(pipe.isBroken())
            button.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0,255),3));
        else if (pipe.isSlippery())
            button.setBorder(BorderFactory.createLineBorder(new Color(80, 238, 238,255),3));
        else if (pipe.isGlued())
            button.setBorder(BorderFactory.createLineBorder(new Color(50, 96, 21,255),3));
        else {
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }

        view.getPanel().remove(nev);
        if (pipe.players.isEmpty())
            button.setText(pipe.getId());
        else {
            button.setText(pipe.getId());
            nev = new JLabel(pipe.players.get(0).getId());
            nev.setFont(new Font("Arial", Font.BOLD, 10));
            nev.setBounds(x+14, y+2, 20, 20);
            view.getPanel().add(nev, Integer.valueOf(2));
        }
        view.getPanel().add(button, Integer.valueOf(1));

        /**
         * Vonalak es nyilak kirajzolasa
         */
        drawLinesAndArrows();
    }

    /**
     * ActionListener interface megvalósítása
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e){
       action(pipe.getId());
    }

    /**
     * getter függvény
     * @return
     */
    @Override
    public String getID(){
        return pipe.getId();
    }
}
