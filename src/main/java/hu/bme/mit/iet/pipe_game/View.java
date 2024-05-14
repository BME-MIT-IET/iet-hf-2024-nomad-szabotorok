package hu.bme.mit.iet.pipe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class View extends JPanel{
    private ArrayList<ViewBase> fields;
    private GUIControl guiControl;
    private JFrame frame;
    private JComboBox<String> commandSelect;
    private JLayeredPane pane;
    private JTextArea statusTextBox;
    private JLabel command;
    private ArrayList<String > tempCommand = new ArrayList<>(); //a több parancsból állokhoz
    private ArrayList<Line2D> lines = new ArrayList<>();

    /**
     * Konstruktor
     * a játékmező létrehozása
     * JComboBox létrehozása a commandokhoz
     * @param control
     */
    public View(GUIControl control) {
        guiControl = control;
        frame = guiControl.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JLayeredPane();
        pane.setBounds(0,0,1000, 855);

        statusTextBox = new JTextArea();
        statusTextBox.setEnabled(false);
        statusTextBox.setDisabledTextColor(Color.BLACK);
        statusTextBox.setFont(new Font("Arial", Font.BOLD, 16));
        statusTextBox.setBounds(800, 20, 180, 250);

        commandSelect = new JComboBox<String>();
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

        commandSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commitCommand((String) commandSelect.getSelectedItem());
            }
        });

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

        ArrayList<Line2D> lines = this.getlines();
        for (int i = 0; i < lines.size(); ++i) {
            g2d.draw(lines.get(i));
        }

    }

    /**
     * grafikusan frissíti a teljes játékmezőt minden elemével együtt
     */
    public void UpdateAll() {
        String str = guiControl.getStatus();
        statusTextBox.setText(str);
        lines.clear();
        for (ViewBase field :fields ) {
                field.Update();
        }
        frame.repaint();//test
        frame.revalidate();
    }

    /**
     * JComboBoxban kiválasztott command aktiválása
     * ,majd az új állapot szerint teljes grafikus frissítés
     * @param input
     */
    public void ManageCommand(String input){
        tempCommand.add(input);
        guiControl.commitCommand(tempCommand);
        tempCommand.clear();
        UpdateAll();
    }
    public void ManageLongCommand(){
        guiControl.commitCommand(tempCommand);
        tempCommand.clear();
        UpdateAll();
    }
    public void commitCommand(String input) {
        switch (input){
            case "Break pipe":
                tempCommand.clear();
                ManageCommand("breakpipe");
                break;
            case "Carry pipe"://több infó kel egynél
                tempCommand.clear();
                tempCommand.add("carrypipe");
                break;
            case "Carry pump":
                tempCommand.clear();
                ManageCommand("carrypump");
                break;
            case "Lay pipe":
                tempCommand.clear();
                ManageCommand("laypipe");
                break;
            case "Lay pump":
                tempCommand.clear();
                ManageCommand("laypump");
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
                ManageCommand("repair");
                break;
            case "Make pipe slippery":
                tempCommand.clear();
                ManageCommand("makeslippery");
                break;
            case "Glue pipe":
                tempCommand.clear();
                ManageCommand("makegluey");
                break;
            case "Pass":
                tempCommand.clear();
                ManageCommand("pass");
                break;
            default:
            break;
        }
    }


    /**
     * setter függvény
     * @param things
     */
    public void setFields(ArrayList<ViewBase> things) {
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
    public ArrayList<ViewBase> getFields(){
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
    public ArrayList<Line2D> getlines(){
        return lines;
    }

    /**
     * getter függvény
     * @return
     */
    public ArrayList<String> getTempCommand(){ return tempCommand; }

    /**
     * új command hozzáadása a tempCommand temporary commandokat tartalmazó listához
     * @param x
     */
    public void addCommand(String x) {tempCommand.add(x);}
}
