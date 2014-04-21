package szoftlab4;
/**
 * A Fog oszt�ly statikus met�dusokkal biztos�tja a k�d ki- �s bekacsol�s�t,
 * illetve lek�rdez�s�t. 
 * 
 * @author Tall�r B�tor
 **/
public class Fog {
	private static boolean isSet = false;
	
	/**
	 * Att�l f�gg�en, hogy be van-e kapcsolva a k�d, 
	 * visszaad egy l�t�t�vols�g szorz�t 
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