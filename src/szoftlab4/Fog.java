package szoftlab4;
/**
 * A Fog osztály statikus metódusokkal biztosítja a köd ki- és bekacsolását,
 * illetve lekérdezését. 
 * 
 * @author Tallér Bátor
 **/
public class Fog {
	private static boolean isSet = false;
	
	/**
	 * Attól függõen, hogy be van-e kapcsolva a köd, 
	 * visszaad egy látótávolság szorzót 
	 **/
	public static double getRangeMultiplier(){
		if (isSet)
			return 0.6;
		
		return 1.0;
	}

	public static void setFog(boolean fog){
		isSet = fog;
	}
}