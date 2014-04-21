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
	public K a;
	public V b;
	
	public Pair(K a, V b){
		this.a = a;
		this.b = b;
	}
}
