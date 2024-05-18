package hu.bme.mit.iet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeListener;
import java.nio.channels.Pipe;
import java.util.ArrayList;

public class GUIControl extends JFrame{
    private Control gameControl;
    private View gameField;
    private  int rounds, mechCount, sabCount;

    /**
     * a menu létrehozása exit és start gombokkal
     * a gombok műkődésének beállítása
     */
    public void Run(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 855);
        this.setTitle("Sivatagi Vízhálózat");
        this.setResizable(false);

        final JPanel  menu = new JPanel();

        menu.setLayout(null);
        menu.setPreferredSize(new Dimension(500,500));

        JButton start = new JButton("Játék indítása");
        start.setFont(new Font("Arial", Font.BOLD, 32));
        start.setBounds((1000-300)/2, 250, 300, 100);

        JButton exit = new JButton("Kilépés");
        exit.setBounds((1000-300)/2, 400, 300, 100);
        exit.setFont(new Font("Arial", Font.BOLD, 32));

        JLabel title = new JLabel("Sivatagi Vízhálózat", SwingConstants.CENTER);
        title.setBounds(0, 50, 1000, 150);
        title.setFont(new Font("Arial", Font.BOLD, 60));

        menu.add(title);
        menu.add(start);
        menu.add(exit);

        this.add(menu);
        this.setVisible(true);

        /**
         * ActionListener-ek a gombokhoz
         * kilepes es start
         */
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(GUIControl.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings(menu);
            }
        });

    }

    /**
     * beállítások menu
     * beállítási opciókat JLabeleken kiírva létrehozza
     * létrehoz JComboBoxokat a kiválasztható értékekkel
     * a start gomb megnyomására a kiválasztott beállítások beállítása
     * @param oldMenu
     */
    public void Settings(JPanel oldMenu) {
        this.remove(oldMenu);

        final JPanel menu = new JPanel();
        menu.setLayout(null);
        menu.setPreferredSize(new Dimension(500, 500));

        JLabel title = new JLabel("Beállítások", SwingConstants.CENTER);
        title.setBounds(0, 20, 1000, 150);
        title.setFont(new Font("Arial", Font.BOLD, 60));

        JLabel kor = new JLabel("Hány kör legyen?", SwingConstants.LEFT);
        kor.setBounds(150, 200, 500, 100);
        kor.setFont(new Font("Arial", Font.BOLD, 30));

        Integer[] szamok =  new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20};
        final JComboBox<Integer> roundNum = new JComboBox<>(szamok);
        roundNum.setBounds(650, 240, 50, 20);

        JLabel saboteur = new JLabel("Hány szabotőr legyen?", SwingConstants.LEFT);
        saboteur.setBounds(150, 300, 500, 100);
        saboteur.setFont(new Font("Arial", Font.BOLD, 30));

        final JComboBox<Integer> saboteurCount = new JComboBox<>(new Integer[]{2, 3, 4});
        saboteurCount.setBounds(650, 340, 50, 20);

        JLabel mechanic = new JLabel("Hány szerelő legyen?", SwingConstants.LEFT);
        mechanic.setBounds(150, 400, 500, 100);
        mechanic.setFont(new Font("Arial", Font.BOLD, 30));

        final JComboBox<Integer> mechanicCount = new JComboBox<>(new Integer[]{2, 3, 4});
        mechanicCount.setBounds(650, 440, 50, 20);

        JButton start = new JButton("Játék indítása");
        start.setFont(new Font("Arial", Font.BOLD, 32));
        start.setBounds((1000 - 300) / 2, 500, 300, 100);

        menu.add(title);
        menu.add(kor);
        menu.add(roundNum);
        menu.add(saboteur);
        menu.add(saboteurCount);
        menu.add(mechanic);
        menu.add(mechanicCount);
        menu.add(start);

        /**
         * 3 comboboxhoz ActionListener
         */
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rounds = (int)roundNum.getSelectedItem();
                sabCount = (int)saboteurCount.getSelectedItem();
                mechCount = (int)mechanicCount.getSelectedItem();
                remove(menu);
                StartGame();
            }
        });

        this.add(menu);
        this.revalidate();
    }

    /**
     * Játék indítása
     * játékmező beállítása és kirajzolása
     */
    public void StartGame() {
        gameField = new View(this);
        gameControl= new Control();
        gameControl.Init(rounds,mechCount,sabCount);

        ArrayList<ViewBase> things = new ArrayList<ViewBase>();

        for (int i = 0; i < gameControl.getPipes().size(); ++i){
            PipeView thing = new PipeView(gameField, gameControl.getPipes().get(i));
            things.add(thing);
        }
        for (int i = 0; i < gameControl.getPumps().size(); ++i){
            PumpView thing = new PumpView(gameField, gameControl.getPumps().get(i));
            things.add(thing);
        }
        for (int i = 0; i < gameControl.getCisterns().size(); ++i){
            CisternView thing = new CisternView(gameField, gameControl.getCisterns().get(i));
            things.add(thing);
        }
        for (int i = 0; i < gameControl.getWaterSources().size(); ++i){
            WaterSourceView thing = new WaterSourceView(gameField, gameControl.getWaterSources().get(i));
            things.add(thing);
        }

        /**
         * Beallitjuk a grafikus helyzetuket a dolgoknak
         */
        // Pipes
        things.get(0).setCoords(325, 0);
        things.get(1).setCoords(425, 0);
        things.get(2).setCoords(375, 350);
        things.get(3).setCoords(325, 750);
        things.get(4).setCoords(425, 750);
        // Pumps
        things.get(5).setCoords(50, 350);
        things.get(6).setCoords(700, 350);
        // Cisterns
        things.get(7).setCoords(0, 750);
        things.get(8).setCoords(750, 750);
        // Watersources
        things.get(9).setCoords(0, 0);
        things.get(10).setCoords(750, 0);

        gameField.setFields(things);
        gameField.UpdateAll();
    }

    /**
     * Egy commandot vegrehajt, azaz atad a modellnek, h vegrehajtsa az akciot.
     * Ha letettunk egy pumpat, akkor azt felveszi a jatekterbe
     * @param commands
     */
    public void commitCommand(ArrayList<String> commands) {
        int ertek = gameControl.PlayerAction(commands);

        /**
         * ha ertek <0 ,akkor nem volt pumpaletetel
         * ha ertek >= 0, akkro volt, es ertek a pumps-ban az idnexet adja vissza
         */
        if ( ertek>= 0) {
            Pump pump = gameControl.getPumps().get(ertek);
            PumpView pumpView = new PumpView(gameField, pump);
            PipeView pipeView = new PipeView(gameField, gameControl.getPipes().get(gameControl.getPipes().size() - 1));

            ViewBase posPipe = null;
            for (int i = 0; i < gameField.getFields().size(); ++i) {

                if (gameControl.getLastPlayer().getPosition().getId().equals(gameField.getFields().get(i).getID())) {
                    posPipe = gameField.getFields().get(i);
                    break;
                }
            }

            int n1X = 0;
            int n1Y = 0;
            int n2X = 0;
            int n2Y = 0;

            SystemPart posp = pump.getNeighbours().get(0);
            SystemPart newp = pump.getNeighbours().get(1);

            String n1 = posp.getNeighbours().get(0).getId();
            String n2 = newp.getNeighbours().get(1).getId();

            ArrayList<ViewBase> fields = gameField.getFields();
            for (ViewBase f : fields) {
                String id = f.getID();
                if (id.equals(n1)) {
                    n1X = f.getX();
                    n1Y = f.getY();
                }
                if (id.equals(n2)) {
                    n2X = f.getX();
                    n2Y = f.getY();
                }
            }

            pumpView.setCoords(posPipe.x, posPipe.y);
            pipeView.setCoords((n2X + posPipe.x) / 2, (n2Y + posPipe.y) / 2);
            posPipe.setCoords((n1X + posPipe.x) / 2, (n1Y + posPipe.y) / 2);

            fields.add(pumpView);
            fields.add(pipeView);
        }

        ArrayList<Cistern> cisterns = gameControl.getCisterns();
        for (int i = 0; i < cisterns.size(); ++i){

            if ( !cisterns.get(i).hasPump() && cisterns.get(i).hasP() ==true) {
                ArrayList<ViewBase> fields = gameField.getFields();
                PipeView pw = new PipeView(gameField, cisterns.get(i).getPipe());
                int x = 0;
                int y = 0;
                for (int j = 0; j < fields.size(); ++j) {
                    if (fields.get(j).getID().equals(cisterns.get(i).getId())){
                        x = fields.get(j).getX();
                        y = fields.get(j).getY();
                    }

                }
                pw.setCoords(x, y-100*cisterns.get(i).getpCount());
                fields.add(pw);
                System.out.println("generalodott");
            }
        }
        /**
         * Vege van e a jateknak
         */
        CheckState();
    }

    /**
     * Ellenorizzuk, hogy vege van e a jateknak
     */
    public void CheckState() {
        if(gameControl.GameOver()) {
            int mech = gameControl.getMechanicPoints();
            int sab = Control.getSaboteurPoints();
            String end = "Vége a Játéknak!\nSzerelők pontjai: " + mech +
                    "\nSzabotőrök pontjai: "+ sab;
            if (mech>sab)
                end += "\nA szerelők nyertek!";
            else if (mech<sab)
                end += "\nA szabotőrök nyertek!";
            else
                end += "\nDöntetlen";

            this.remove(gameField);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            JTextArea textArea = new JTextArea(end);
            textArea.setFont(new Font("Arial", Font.BOLD, 30));
            textArea.setBounds(200,200,500,200);


            panel.add(textArea);
            add(panel);

            repaint();
            revalidate();
        }
    }

    /**
     * A framet adjuk vissza
     * @return frame
     */
    public JFrame getFrame(){
        return this;
    }

    /**
     * A statuszt kerjuk el, es adjuk vissza
     * @return status
     */
    public String getStatus(){
        return gameControl.getStatus();
    }

}
