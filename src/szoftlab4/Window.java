package szoftlab4;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	Menu menu;
	
	public void setGame(Game game) {
		this.setContentPane(game.view.getPanel());
		this.pack();
	}
	
	public Window() {
		setSize(500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu = new Menu(this);
		
		add(menu.panel);
		
	}
}
