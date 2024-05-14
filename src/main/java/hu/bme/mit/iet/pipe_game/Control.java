package hu.bme.mit.iet.pipe_game;

import java.util.*;

public class Control {
    private static ArrayList<Mechanic> mechanics = new ArrayList<>();
    private static ArrayList<Saboteur> saboteurs = new ArrayList<>();
    private static ArrayList<Pump> pumps = new ArrayList<>();
    private static ArrayList<Cistern> cisterns = new ArrayList<>();
    private static ArrayList<WaterSource> waterSources = new ArrayList<>();
    private static ArrayList<Pipe> pipes = new ArrayList<>();
    private static int rounds;
    private static int maxRound;
    private static int saboteurPoints;
    private static int mechanicPoints;
    private static Player currentPlayer;
    private static Player lastPlayer;
    private static boolean gameOver = false;
    private static boolean randEnabled = true;
    private static int pumpBreak = 2;
    private static Random rnd = new Random();

    /**
     * A játék inicializálása:
     * Játékosok létrehozása
     * A hálózat elemeinek létrehozása, összekapcsolása
     * Játékosok lehelyezése a hálózatra
     * @param r az adott játék köreinek száma
     * @param mechanicCounter hány szerelő játszik
     * @param saboteurCounter hány szabotőr játszik
     */
    public static void init(int r, int mechanicCounter, int saboteurCounter) {
        maxRound = r;
        rounds = 1;

        /** Letrehozzuk a jatekosokat
         */
        for (int i = 0; i < mechanicCounter ;i++) {
            mechanics.add(new Mechanic());
        }
        for (int i = 0; i < saboteurCounter ;i++) {
            saboteurs.add(new Saboteur());
        }

        /** Letrehozzuk a mezoket
         */
        WaterSource ws1 = new WaterSource();
        WaterSource ws2 = new WaterSource();
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        Pipe pipe3 = new Pipe();
        Pipe pipe4 = new Pipe();
        Pipe pipe5 = new Pipe();
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();
        Cistern c1 = new Cistern();
        Cistern c2 = new Cistern();

        /** Letrehozzuk a szomszedsagokat
         */
        connect(ws1, pipe1);
        connect(ws2, pipe2);

        connect(pump1, pipe1);
        connect(pump1, pipe3);
        connect(pump1, pipe4);

        connect(pump2, pipe2);
        connect(pump2, pipe3);
        connect(pump2, pipe5);

        pump1.AcceptNewFlow(pipe1,pipe4);
        pump2.AcceptNewFlow(pipe2,pipe5);

        connect(c1, pipe4);
        connect(c2, pipe5);

        /** Pumpakra tesszuk a jatekosokat
         */
        for (int i = 0; i<mechanics.size();i++) {
            mechanics.get(i).setPos(pump1);
        }
        for (int i = 0; i<saboteurs.size();i++) {
            saboteurs.get(i).setPos(pump2);
        }

        waterSources.add(ws1);
        waterSources.add(ws2);
        cisterns.add(c1);
        cisterns.add(c2);
        pumps.add(pump1);
        pumps.add(pump2);
        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);
        pipes.add(pipe4);
        pipes.add(pipe5);
        currentPlayer = mechanics.get(0);
        currentPlayer.setActionPoints(3);
    }

    /**
     * A körök végén kerül meghívásra
     * Ilyenkor folyik a víz a csőrendszerben és frissülnek a csapatok pontszámai
     * Csökkenti az efektek időtartalmát
     */

    public static void handlePumps() {
        for (Pump p:pumps) {
            saboteurPoints += p.PushWater();
        }
        for (Pump p:pumps) {
            p.PullWater();
            // pump random eltörése
            pumpBreak--;
            if (randEnabled) {
                if(rnd.nextInt(10) > 7)
                    p.BreakPump();
            }
            else {
                if (pumpBreak == 0)
                    p.BreakPump();
            }
        }
    } 
    public static void roundOver() {
        /** ciszternák
         */
        for (Cistern c:cisterns) {
            mechanicPoints += c.PullWater();
            c.generate();
        }
        /** pumpák
         */
        handlePumps();

        /** Vízforrás
         */
        for (WaterSource ws:waterSources) {
            saboteurPoints += ws.PushWater();
        }
        /** Csövek
         */
        for (Pipe p:pipes) {
            p.decTimeTillNewBreak();
        }
        for (Mechanic m:mechanics) {
            m.decGlued();
        }
        for (Saboteur s:saboteurs) {
            s.decGlued();
        }
        if (rounds <= maxRound)
            rounds++;
        if (rounds > maxRound) {
            gameOver = true;
        }
    }

    public void pumpChangeFlow(List<String> command) {
        if (command.size() < 3) {
            return;
        }
        String[] s1 = command.get(1).split(" ");
        int p1 = Integer.parseInt(s1[1]);
        int p2 = Integer.parseInt(command.get(3));
        if (0 > p1 || p1 >= pipes.size() || 0 > p2 || p2 >= pipes.size() ){
            // Nem letezo elemek
            return;
        }

        Pipe pipe1 = pipes.get(p1);
        Pipe pipe2 = pipes.get(p2);
        boolean p1found = false;
        boolean p2found = false;

        for (int i = 0; i < currentPlayer.getPosition().getNeighbours().size();++i) {
            String id = currentPlayer.getPosition().getNeighbours().get(i).getId();
            if (!p1found && id.equals(pipe1.id))
                p1found = true;
            if (!p2found && id.equals(pipe2.id))
                p2found = true;
            if (p1found && p2found) {
                currentPlayer.ChangePumpFlow(pipe1, pipe2);
                break;
            }
        }
    }

    public int handlePlayerCommand(List<String> command) {
        int pumpLayed = -1;
        switch (command.get(0)){
            case "breakpipe":
                currentPlayer.BreakPipe();
                break;
            case "carrypipe":
                currentPlayer.CarryPipeEnd(pipes.get(Integer.parseInt(command.get(2))));
                break;
            case "carrypump":
                currentPlayer.CarryPump();
                break;
            case "laypipe":
                currentPlayer.LayPipe();
                break;
            case "laypump":
                SystemPart pump = currentPlayer.LayPump();
                if (pumps.contains(pump))
                    pumpLayed = pumps.indexOf(pump);
                break;
            case "move": {
                String id = command.get(1) + " " + command.get(2);
                SystemPart moveTo = null; // ha az elem elérheto a jatekostol
                for (SystemPart temp :
                        currentPlayer.getPosition().getNeighbours()) {
                    if (id.equals(temp.getId()))
                        moveTo = temp;
                }
                if (moveTo == null) {//ha nem erheto el breakelunk
                    break;
                }
                currentPlayer.Move(moveTo);//jatekos probal mozogni
                break;
            }
            case "pumpchangeflow":
                pumpChangeFlow(command);
                break;
            case "repair":
                currentPlayer.Repair();
                break;
            case "makeslippery":
                currentPlayer.MakePipeSlippery();
                break;
            case "makegluey":
                currentPlayer.GluePipe();
                break;
            case "pass":
                // passed the turn
                currentPlayer.setActionPoints(0);
                break;
            default:
                break;
        }
        return pumpLayed;
    }

    /**
     * A játékos parancsait feldolgozó és megvalósító metódus
     * @param "currentPlayer" az éppen soron lévő játékos
     */
    public int playerAction(List<String> command) {
        int pumpLayed = handlePlayerCommand(command);
        currentPlayer.decActionPoints();
        boolean roundOver = false;
        /** Ha nincs akciopontja a jelenlegi jatekosnak, iteralni kell egyet a jatekosok kozott
         */
        if (currentPlayer.getActionPoints() <= 0) {
            roundOver = iterateCurrentPlayer();
            currentPlayer.setActionPoints(3);
        }
        /** Ha kör vége van
         */
        if (roundOver){
            roundOver();
        }
        return pumpLayed;
    }

    public static void setMechanicPoints(int points) {
        mechanicPoints = points;
    }
    public int getMechanicPoints(){
        return mechanicPoints;
    }
    public static void setSaboteurPoints(int points) {
        saboteurPoints = points;
    }
    public static int getSaboteurPoints() {
        return saboteurPoints;
    }

    public static void addPump(Pump p) {
        Control.pumps.add(p);
    }
    public static void addPipe(Pipe p) {
        Control.pipes.add(p);
    }

    private static void connect(SystemPart s1, SystemPart s2) {
        s1.AddNeighbour(s2);
        s2.AddNeighbour(s1);
    }

    public static void setRand(boolean a) {
        randEnabled = a;
    }

    public List<Mechanic> getMechanics(){
        return mechanics;
    }

    public List<Saboteur> getSaboteurs(){
        return saboteurs;
    }

    public List<Pipe> getPipes(){
        return pipes;
    }

    public List<Pump> getPumps(){
        return pumps;
    }

    public List<Cistern> getCisterns(){
        return cisterns;
    }
    public List<WaterSource> getWaterSources(){
        return waterSources;
    }

    /**
     * Iteralofuggveny
     * Iteralja a jatekosokat
     * @return
     */
    public static boolean iterateCurrentPlayer(){
        if(mechanics.contains(currentPlayer)) {
            int mechIdx = mechanics.indexOf(currentPlayer);
            if (mechIdx == (mechanics.size() - 1)) {
                lastPlayer = currentPlayer;
                currentPlayer = saboteurs.get(0);
            }
            else {
                lastPlayer = currentPlayer;
                currentPlayer = mechanics.get(mechIdx + 1);
            }
        }
        else if(saboteurs.contains(currentPlayer)) {
            int sabIdx = saboteurs.indexOf(currentPlayer);
            if (sabIdx == (saboteurs.size() - 1)) {
                lastPlayer = currentPlayer;
                currentPlayer = mechanics.get(0);
                return true;
            }
            else {
                lastPlayer = currentPlayer;
                currentPlayer = saboteurs.get(sabIdx + 1);
            }
        }
        return false;
    }

    /**
     * Lekérdezi a Modell jelenglegi állapotát, amit majd kiírunk, hogy
     * tájékoztassuk a játékosokat
     * @return String status
     */
    public String getStatus() {
        StringBuilder mechanicPartsBuilder = new StringBuilder();
        for (Mechanic m : mechanics) {
            mechanicPartsBuilder.append(m.getId())
                                .append(" has: ")
                                .append(m.getPart())
                                .append("\n");
        }
        String mechanicParts = mechanicPartsBuilder.toString();
        return "Round " + rounds + "/" + maxRound + "\n" +
        "Mechanic points: " + mechanicPoints + "\n" +
        "Saboteur points: " + saboteurPoints + "\n" +
        "\n" +
        "Now playing: " + currentPlayer.id + "\n" +
        "Actions left: " + currentPlayer.getActionPoints() + "\n" +
        "\n" +
        mechanicParts ;
    }

    /**
     * Visszaadja az utolso cselekvo embert
     * @return player
     */
    public Player getLastPlayer() {
        if (currentPlayer.getActionPoints() >= 3)
            return lastPlayer;
        else return currentPlayer;
    }

    /**
     * Vege van-e a hateknak
     * @return vege van-e a jateknak
     */
    public boolean gameOver() {
        return gameOver;
    }
}
