/**
 * A forrás, ahonnan folyamatosan jön a víz. Ezt továbbítja a szomszédos csövekent.
 */
public class WaterSource extends SystemPart {
	protected static int idNum = 0;

	/**
	 * Konstruktor, ami beállítja az id-t az osztály nevére és egy eltérő számra
	 */
	public WaterSource() {
		id = "watersource " + idNum++;
	}

	/**
	 * Az összes szomszédos csőbe a max capacitásának megfelelő vizet pumpál
	 * A lyukas csöveket kifolyt vizet adja vissza
	 * @return a vizmennyiseg, ami a lyuaks csöveket kifolyt
	 */
	@Override
	public int PushWater() {
		int points = 0;
		for (SystemPart pipe: neighbours) {
			if (pipe.isBroken()) {
				points += pipe.getCapacity();
			} else {
				pipe.setWater(pipe.getCapacity());
			}
		}
		return points;
	}
}
