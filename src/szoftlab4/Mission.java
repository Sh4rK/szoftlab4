package szoftlab4;

/**
 * Az ellens�get �temez�s�t megval�s�t� oszt�ly.
 * @author Adam
 *
 */
public class Mission {
	
	/**
	 * A kapott �tvonalr�l bet�lti a Mission-t.
	 * @param str
	 */
	public Mission(String str){
		printEnter(this);

		printExit(this);
	}
	/**
	 * MegK�rdezi, hogy legyen-e k�vetkez� ellens�g.
	 * @return Visszat�r a list�ban t�rolt k�vetkez� ellens�ggel,vagy null �rt�kkel.
	 */
	public Enemy getNextEnemy() {
		printEnter(this);
		
		if(printYesNoQuestion("Legyen k�vetkez� ellens�g?")){
			printExit(this);
			return new Enemy();
		}
		else{
			printExit(this);
			return null;
		}	
	}

}
