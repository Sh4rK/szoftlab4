package szoftlab4;

import javax.swing.JButton;

/**
 * Sima JButton azzal a kiegészítéssel, hogy eltárol egy Gem-et 
 */
public class GemButton extends JButton {
	private Gem type;
	
	public GemButton(Gem type) {
		this.type = type;
	}
	
	public Gem getGemType(){
		return type;
	}
}
