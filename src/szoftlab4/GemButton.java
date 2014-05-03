package szoftlab4;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class GemButton extends JButton {
	private Gem type;
	
	public GemButton(Gem type) {
		this.type = type;
	}
	
	public Gem getGemType(){
		return type;
	}
}
