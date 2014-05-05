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

public class Main {

	public static void main(String[] args) {
		try {
			Resources.load();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "I can't load the images!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		Window window = new Window();
		while(true){
			window.setResizable(false);
			window.setVisible(true);
			synchronized(window){
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
