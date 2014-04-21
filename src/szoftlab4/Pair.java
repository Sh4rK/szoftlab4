package szoftlab4;

/**
 * Párokat tároló generikus osztály.
 *
 * @param <K>
 * @param <V>
 * @author Nusser Ádám
 */
public class Pair<K, V> {
	public K a;
	public V b;

	public Pair(K a, V b) {
		this.a = a;
		this.b = b;
	}
}
