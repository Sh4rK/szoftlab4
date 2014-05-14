/**
 * ─────────▄──────────────▄
 * ────────▌▒█───────────▄▀▒▌
 * ────────▌▒▒▀▄───────▄▀▒▒▒▐
 * ───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
 * ─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
 * ───▄▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀██▀▒▌
 * ──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌
 * ──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌
 * ─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌
 * ─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐
 * ▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒▒▒░░░░░░▒▒▒▒▌
 * ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐
 * ─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐
 * ──▀▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌
 * ────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀
 * ───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀
 * ──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀
 *
 * such team
 * very towers
 *
 */

package szoftlab4;

import javax.swing.*;
import java.io.IOException;

/**
 * Az alkalmazást indító osztály.
 */
public class Main {

	/**
	 * A program fő belépési pontja. Létrehozza a grafikus ablakot, és megjeleníti azt.
	 *
	 * @param args Parancssori argumentumok.
	 */
	public static void main(String[] args) {
		try {
			Resources.load();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "I can't load the images!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		if (args != null) {
			for (String s : args) {
				if (s.equalsIgnoreCase("--showfps") || s.equalsIgnoreCase("-f"))
					Game.countFPS = true;

				if (s.equalsIgnoreCase("--antialiasing") || s.equalsIgnoreCase("-aa"))
					Game.AA = true;
			}
		}

		final Window window = new Window();
		while (true) {
			window.setResizable(false);
			window.setVisible(true);
			synchronized (window) {
				try {
					window.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			window.runGame();
		}
	}

}
