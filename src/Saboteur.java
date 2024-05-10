//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Sivatagi vizhalozat
//  @ File Name : Saboteur.java
//  @ Date : 14/04/2023
//  
//
//

/**
 * A szabotőr csúszóssá tudja tenni a csövet.
 * Ezen kívül a mozgás és a pumpa állítás, csőlyukasztás a Player osztályban már meg van valósítva.
 */
public class Saboteur extends Player {
	protected static int idNum = 0;
	/**
	 * Konstruktor, ami beállítja az id-t az osztály nevére és egy eltérő számra
	 */
	public Saboteur() {
		id = "S" + idNum++;
	}
	/**
	 * A szabotőr csúszóssá teszi a csövet amin áll.
	 */
	@Override
	public void MakePipeSlippery() {
		position.setSlippery();
	}
}
