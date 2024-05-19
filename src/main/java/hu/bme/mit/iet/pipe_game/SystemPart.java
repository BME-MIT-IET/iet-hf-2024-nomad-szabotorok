package hu.bme.mit.iet.pipe_game;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Sivatagi vizhalozat
//  @ File Name : SystemPart.java
//  @ Date : 14/04/2023
//  
//
//

import java.util.ArrayList;
import java.util.List;

/**
 * Az elemek absztrakt ősosztálya.
 * Minden függvényt tartalmaz de ezek közül nem mindent valósít meg.
 * SystemPart minden elem a vízrendszerben.
 * Ezekre az elemekre ráléphetnek a játkosok és különböző akciókat hajthatnak végre rajtuk.
 * Fő szerepe, hogy egységesen lehessen kezelni a vízrendszer különböző elemeit.
 * Maga az osztály nem csinál sokat, legtöbb függvényét leszármazottai valósítják meg az alapján,
 * hogy éppen mit lehet az adott leszármazottra lépve csinálni.
 */
public abstract class SystemPart {
	protected String id;
	protected ArrayList<Player> players = new ArrayList<>();
	protected ArrayList<SystemPart> neighbours = new ArrayList<>();

	/**
	 * visszaadja a szomszédokat (controlnak kell csak)
	 * @return
	 */
	public List<SystemPart> getNeighbours(){return neighbours;}
	/**
	 * Elfogadja a rálépő játékost
	 * A Pipe-ban felül van definiálva, hogy csak egy ember állhasson az elemen
	 * @param mover A player ami szeretne az elemre lepni
	 * @return ezt a fuggvenyt felulirjak a leszarmazottak igy mindig false-ot ad vissza
	 */
	public boolean acceptPlayer(Player mover) {
		players.add(mover);
		return true;
	}

	/**
	 * Lelépteti az elemen álló játékost
	 * @param mover az elemen allo jatokosok listajabol torlendo objektum
	 */
	public void removePlayer(Player mover) {
		players.remove(mover);
	}

	/**
	 * @param from Az uj cso amibol vizet sziv a pumpa
	 * @param to az uj cso amibe vizet tol a pumpa
	 * @return leszarmazott felulirja, mivel itt nem csinal semmit false-al ter vissza.
	 */
	public boolean acceptNewFlow(Pipe from, Pipe to) {
		return false;
	}

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit
	 */
	public void breakPipe() { }

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit
	 */
	public void repair() { }

	/**
	 * Az aktív elemből felveszünk egy csövet
	 * A Pipe-ban felüldefiniáljuk mert ott mást csinál
	 * @param whichEnd melyik csövet vesszük fel
	 */
	public boolean carryPipeEnd(SystemPart whichEnd) {
		if (!whichEnd.players.isEmpty()) {
			return false;
		}

		this.removeNeighbour(whichEnd);
		whichEnd.removeNeighbour(this);
		whichEnd.setCarried(true);
		return true;
	}
	/**
	 * Egy cső lehelyezése egy aktív elemre
	 * Beállítjuk őket egymás szomszédainak, ha letehető a cső
	 * Az aktív elemeknél valósítjuk meg őket
	 * @param pipe az a cső amit lehelyezünk
	 * @return sikeres volt-e a lehelyezés
	 */
	public boolean layPipe(SystemPart pipe) {
		if(neighbours.size() >= 4) {
			return false;
		}
		if (neighbours.contains(pipe)) {
			return false;
		}
		addNeighbour(pipe);
		pipe.addNeighbour(this);
		pipe.setCarried(false);
		return true;
	}

	/**
	 * Kitörli a szomszédai közül a paraméterként kapott Systempartot
	 * @param remove ezt SystemPart-ot eltavolitja a neighbours listabol
	 */
	public void removeNeighbour(SystemPart remove) {
		neighbours.remove(remove);
	}
	/**
	 * Felveszi a paraméterként kapott SystemPartot szomszédnak
	 * @param newPart ezt a SystemPart objektumot hozzaadja a neighbours listahoz
	 */
	public void addNeighbour(SystemPart newPart) {
		neighbours.add(newPart);
	}

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit
	 * @return
	 */
	public Pump carryPump() {
		return null;
	}

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit
	 * @param pump a lerakandó pumpa.
	 * @return
	 */
	public boolean layPump(SystemPart pump) {
		return false;
	}

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit, -1-el (hiba) tér vissza
	 * @return
	 */
	public int pushWater() {
		return 0;
	}

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit, -1-el (hiba) tér vissza
	 * @return
	 */
	public int pullWater() {
		return 0;
	}

	/**
	 * leszarmazott felulirja, igy itt nem csinal semmit
	 */
	public void breakPump() {
	}

	/**
	 * Kulonbozo setterek es getter innentol
	 */
	public int getWater(){
		return -1;
	}
	public void setWater(int w){
	}
	public int getCapacity(){
		return -1;
	}
	public void setCarried(boolean c){
	}
	void setGlued() {}
	void setSlippery() {}
	public String getId() {return id;}

	/**
	 * A PIPE getterek setterek hogy láthatóak legyenek
	 */
	public boolean isBroken() {
		return false;
	}

	public String getFrom(){
		return null;
	}
	public String getTo(){
		return null;
	}
}
