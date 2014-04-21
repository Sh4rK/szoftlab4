package szoftlab4;
/**
 * Párokat tároló generikus osztály.
 * 
 * @author Nusser Ádám
 *
 * @param <K>
 * @param <V>
 */
public class Pair <K,V>{
	K a;
	V b;
	
	public Pair(K a, V b){
		this.a = a;
		this.b = b;
	}
	K getK(){
		return a;
	}
	V getV(){
		return b;
	}
}
