package hu.bme.mit.iet.pipe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class View extends JPanel{
    private transient List<ViewBase> fields;
    private GUIControl guiControl;
    private JFrame frame;
    private JComboBox<String> commandSelect;
    private JLayeredPane pane;
    private JTextArea statusTextBox;
    private JLabel command;
    private List<String > tempCommand = new ArrayList<>(); //a több parancsból állokhoz
    private transient List<Line2D> lines = new ArrayList<>();

    /**
     * Konstruktor
     * a játékmező létrehozása
     * JComboBox létrehozása a commandokhoz
     * @param control
     */
    public View(GUIControl control) {
        guiControl = control;
        frame = guiControl.getFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pane = new JLayeredPane();
        pane.setBounds(0,0,1000, 855);

        statusTextBox = new JTextArea();
        statusTextBox.setEnabled(false);
        statusTextBox.setDisabledTextColor(Color.BLACK);
        statusTextBox.setFont(new Font("Arial", Font.BOLD, 16));
        statusTextBox.setBounds(800, 20, 180, 250);

        commandSelect = new JComboBox<>();
        commandSelect.setBounds(800, 330, 180, 20);

        commandSelect.addItem("Break pipe");
        commandSelect.addItem("Carry pipe");
        commandSelect.addItem("Carry pump");
        commandSelect.addItem("Lay pipe");
        commandSelect.addItem("Lay pump");
        commandSelect.addItem("Move");
        commandSelect.addItem("Change pump flow");
        commandSelect.addItem("Repair");
        commandSelect.addItem("Make pipe slippery");
        commandSelect.addItem("Glue pipe");
        commandSelect.addItem("Pass");

        command = new JLabel("Choose an action:");
        command.setBounds(800,290,200,50);
        command.setFont(new Font("Arial", Font.BOLD, 18));

        pane.add(statusTextBox, Integer.valueOf(0));
        pane.add(command, Integer.valueOf(0));
        pane.add(commandSelect, Integer.valueOf(0));

        commandSelect.addActionListener(e -> commitCommand((String) commandSelect.getSelectedItem()));

        this.setLayout(null);
        this.setOpaque(true);
        this.add(pane);

        frame.add(this);

        frame.repaint();
        frame.revalidate();
    }

    /**
     * játékmező és összeköttetések kirajzolása
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < lines.size(); ++i) {
            g2d.draw(lines.get(i));
        }

    }

    /**
     * grafikusan frissíti a teljes játékmezőt minden elemével együtt
     */
    public void updateAll() {
        String str = guiControl.getStatus();
        statusTextBox.setText(str);
        lines.clear();
        for (ViewBase field :fields ) {
                field.update();
        }
        frame.repaint();//test
        frame.revalidate();
    }

    /**
     * JComboBoxban kiválasztott command aktiválása
     * ,majd az új állapot szerint teljes grafikus frissítés
     * @param input
     */
    public void manageCommand(String input){
        tempCommand.add(input);
        guiControl.commitCommand(tempCommand);
        tempCommand.clear();
        updateAll();
    }
    public void manageLongCommand(){
        guiControl.commitCommand(tempCommand);
        tempCommand.clear();
        updateAll();
    }
    public void commitCommand(String input) {
        switch (input){
            case "Break pipe":
                tempCommand.clear();
                manageCommand("breakpipe");
                break;
            case "Carry pipe"://több infó kel egynél
                tempCommand.clear();
                tempCommand.add("carrypipe");
                break;
            case "Carry pump":
                tempCommand.clear();
                manageCommand("carrypump");
                break;
            case "Lay pipe":
                tempCommand.clear();
                manageCommand("laypipe");
                break;
            case "Lay pump":
                tempCommand.clear();
                manageCommand("laypump");
                break;
            case "Move"://több infó is kellene majd
                tempCommand.clear();
                tempCommand.add("move");
                break;
            case "Change pump flow"://több infó is kellene majd
                tempCommand.clear();
                tempCommand.add("pumpchangeflow");
                break;
            case "Repair":
                tempCommand.clear();
                manageCommand("repair");
                break;
            case "Make pipe slippery":
                tempCommand.clear();
                manageCommand("makeslippery");
                break;
            case "Glue pipe":
                tempCommand.clear();
                manageCommand("makegluey");
                break;
            case "Pass":
                tempCommand.clear();
                manageCommand("pass");
                break;
            default:
            break;
        }
    }


    /**
     * setter függvény
     * @param things
     */
    public void setFields(List<ViewBase> things) {
        fields = things;
    }

    /**
     * getter függvény
     * @return
     */
    public JLayeredPane getPanel(){
        return pane;
    }

    /**
     * getter függvény
     * @return
     */
    public JFrame getFrame(){
        return frame;
    }

    /**
     * getter függvény
     * @return
     */
    public List<ViewBase> getFields(){
        return fields;
    }

    /**
     * új vonal hozzáadása a lines ArrayListhez
     * @param line
     */
    public void addLine(Line2D line) {
        lines.add(line);
    }

    /**
     * getter függvény
     * @return
     */
    public List<Line2D> getlines(){
        return lines;
    }

    /**
     * getter függvény
     * @return
     */
    public List<String> getTempCommand(){ return tempCommand; }

    /**
     * új command hozzáadása a tempCommand temporary commandokat tartalmazó listához
     * @param x
     */
    public void addCommand(String x) {tempCommand.add(x);}
}
