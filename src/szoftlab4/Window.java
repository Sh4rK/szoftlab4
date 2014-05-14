package szoftlab4;

import javax.swing.*;

public class Window extends JFrame {
	Menu menu;
	private volatile Game game = null;

	/**
	 * Beállítja a játékot, majd értesít, hogy el lehet indítani a játékot
	 */
	public synchronized void setGame(Game game) {
		this.setContentPane(game.getView().getPanel());
		this.game = game;
		this.pack();
		this.setLocationRelativeTo(null);
		/* very notify */
		this.notify();
	}

	/**
	 * Elindítja a játékot
	 */
	public void runGame() {
		game.run();
		setSize(500, 200);
		this.setLocationRelativeTo(null);
		this.setContentPane(menu.panel);
	}

	public Window() {
		setSize(500, 200);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new Menu(this);

		add(menu.panel);

	}
}
