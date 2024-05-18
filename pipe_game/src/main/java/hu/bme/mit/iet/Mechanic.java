package hu.bme.mit.iet;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Sivatagi vizhalozat
//  @ File Name : Mechanic.java
//  @ Date : 14/04/2023
//  
//
//

/**
 * A szerelő meg tudja javítani a csövet amin áll,
 * fel tudja venni egy csőnek az egyik végét,
 * le tudja azt tenni valahol,
 * fel tudja venni pumpát a ciszternánál,
 * illetve le is tudja azt tenni egy csövön amin éppen áll.
 * Eltárolja, hogy van-e nála pipe, illetve pumpa, illetve azt a dolgot amit éppen visz.
 */
public class Mechanic extends Player {
	protected static int idNum = 0;
	private boolean hasPipe;
	private boolean hasPump;
	private SystemPart carriedPart;

	/**
	 * Konstruktor, ami beállítja az id-t az osztály nevére és egy eltérő számra
	 */
	public Mechanic() {
		id = "M" + idNum++;
	}

	/**
	 * Ad egy pumpát a szerelőnek
	 * Csak tesztesetek inicializálásánál használjuk
	 * @param pump ez lesz a vitt pumpa
	 */
	@Override
	void setPump(SystemPart pump) {
		carriedPart = pump;
		hasPump = true;
	}
	/**
	 * Megjavítja a csövet ahol áll.
	 */
	@Override
	public void Repair() {
		position.Repair();
	}

	/**
	 * A szerelő felveszi a paraméterként megadott cső végét.
	 * @param pipe a cső, aminek az egyik végét felveszi a szerelő.
	 */
	@Override
	public void CarryPipeEnd(SystemPart pipe) {
		if(position.CarryPipeEnd(pipe)) {
			carriedPart = pipe;
			hasPipe = true;
		}
	}

	/**
	 * A szerelő leteszi a csövet oda, ahol éppen tartózkodik.
	 * @return Visszaadja, hogy sikerült-e letenni a csövet.
	 */
	@Override
	public boolean LayPipe() {
		if (!hasPipe) return false;
		if (position.LayPipe(carriedPart)) {
			hasPipe = false;
			carriedPart = null;
			return true;
		}
		return false;
	}

	/**
	 * A szerelő felveszi a generált pumpát, onnan, ahol éppen tartózkodik.
	 */
	@Override
	public void CarryPump() {
		carriedPart = position.CarryPump();
		hasPump = true;
	}

	/**
	 * A szerelő leteszi a pumpát oda, ahol éppen tartózkodik.
	 * @return Visszaadja, hogy sikerült-e letenni a pumpát.
	 */
	@Override
	public SystemPart LayPump() {
		if (carriedPart == null || !hasPump) return null;
		position.LayPump(carriedPart);
		hasPump = false;
		SystemPart pump = carriedPart;
		carriedPart = null;
		return pump;
	}

	/**
	 * Visszaadjuk a cipelt eszkozt
	 * @return
	 */
	@Override
	public String getPart() {
		if (carriedPart == null)
			return "nothing";
		return carriedPart.id;
	}
}