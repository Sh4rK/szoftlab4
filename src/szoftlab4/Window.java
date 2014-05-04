package szoftlab4;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	Menu menu;
	private volatile Game game = null; 
	
	public synchronized void setGame(Game game) {
		this.setContentPane(game.view.getPanel());
		this.game = game;
		this.pack();
		/* very notify */
		this.notify();
	}
	
	public void runGame(){
		game.run();
	}
	
	public Window() {
		setSize(500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu = new Menu(this);
		
		add(menu.panel);
		
	}
}
