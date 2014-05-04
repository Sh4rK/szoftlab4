package szoftlab4;

import javax.swing.JFrame;

public class Window extends JFrame {
	Menu menu;
	private volatile Game game = null; 
	
	public synchronized void setGame(Game game) {
		this.setContentPane(game.view.getPanel());
		this.game = game;
		this.pack();
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
