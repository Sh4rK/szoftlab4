package szoftlab4;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * A játék menüjét valósítja meg.
 */
@SuppressWarnings({"rawtypes", "unchecked", "serial"})
public class Menu {
	JPanel panel = new JPanel();
	Window window;

	JList mapList, missionList;

	/**
	 * Betölti a pálya listába a pályákat.
	 */
	AbstractListModel mapListModel = new AbstractListModel() {
		ArrayList<String> values = new ArrayList<String>();
		boolean arrayFilled;

		private void fillArray() {
			File mapsDirectory = new File("maps");

			File[] mapFiles = mapsDirectory.listFiles();
			if (mapFiles != null) {
				for (File f : mapFiles) {
					if (f.isFile() && f.getName().endsWith(".map")) {
						String name = f.getName();
						name = name.substring(0, name.length() - 4);
						values.add(name);
					}
				}
			}

			this.fireContentsChanged(this, 0, values.size());
			arrayFilled = true;
		}

		public int getSize() {
			if (!arrayFilled) fillArray();
			return values.size();
		}

		public String getElementAt(int index) {
			if (!arrayFilled) fillArray();
			return values.get(index);
		}
	};

	/**
	 * Betölti a misszió listába a kiválasztott pályához tartozó missziókat.
	 */
	private class MissionListModel extends AbstractListModel {
		ArrayList<String> values = new ArrayList<String>();

		public void fillArray(String mapName) {
			values.clear();

			File missionDirectory = new File("missions");

			File[] missionFiles = missionDirectory.listFiles();
			if (missionFiles != null) {
				for (File f : missionFiles) {
					if (f.isFile() && f.getName().endsWith(".mission") && f.getName().startsWith(mapName + "_")) {
						String name = f.getName();
						name = name.substring(mapName.length() + 1, name.length() - 8);
						values.add(name);
					}
				}
			}

			this.fireContentsChanged(this, 0, values.size());
		}

		public int getSize() {
			return values.size();
		}

		public String getElementAt(int index) {
			return values.get(index);
		}
	}

	MissionListModel missionListModel = new MissionListModel();

	/**
	 * Újratölti a misszió listát ha másik pálya lett kiválasztva.
	 */
	private void onMapSelected() {
		missionListModel.fillArray((String) mapList.getSelectedValue());
	}

	/**
	 * Elindít egy új játékot a kiválasztott pályával és misszióval.
	 */
	public void newGame() {
		Game game = new Game((String) mapList.getSelectedValue(), (String) missionList.getSelectedValue());
		window.setGame(game);
	}

	/**
	 * Kilép a programból.
	 */
	public void exit() {
		window.setVisible(false);
		window.dispose();
		System.exit(0);
	}

	/**
	 * Leellenőrzi, hogy pálya és misszió is ki van-e választva.
	 */
	private boolean everythingSelected() {
		int mapi = mapList.getSelectedIndex();
		int missioni = missionList.getSelectedIndex();
		return mapi >= 0 && mapi < mapList.getModel().getSize() && missioni >= 0 && missioni < missionList.getModel().getSize();
	}

	/**
	 * Felépíti a menüt.
	 */
	public Menu(Window w) {
		window = w;

		panel.setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue_1);

		final JButton btnNewButton = new JButton("Játék indítása");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		btnNewButton.setEnabled(false);
		btnNewButton.setBackground(new Color(204, 204, 204));
		buttonPanel.add(btnNewButton);

		Component horizontalGlue = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue);

		JButton btnNewButton_1 = new JButton("Kilépés");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		btnNewButton_1.setBackground(new Color(204, 204, 204));
		buttonPanel.add(btnNewButton_1);

		Component horizontalGlue_2 = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue_2);

		JPanel listPanel = new JPanel();
		panel.add(listPanel, BorderLayout.CENTER);
		listPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel mapListPanel = new JPanel();
		mapListPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "P\u00E1lya kiv\u00E1laszt\u00E1sa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		listPanel.add(mapListPanel);
		mapListPanel.setLayout(new GridLayout(0, 1, 0, 0));

		mapList = new JList();
		mapList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				onMapSelected();
			}
		});

		mapList.setModel(mapListModel);

		mapList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mapList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				missionList.setSelectedIndex(0);
				btnNewButton.setEnabled(everythingSelected());
			}
		});

		mapListPanel.add(mapList);

		JPanel missionListPanel = new JPanel();
		missionListPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Misszi\u00F3 kiv\u00E1laszt\u00E1sa", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		listPanel.add(missionListPanel);
		missionListPanel.setLayout(new GridLayout(0, 1, 0, 0));

		missionList = new JList();
		missionListPanel.add(missionList);
		missionList.setModel(missionListModel);

		missionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		missionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				btnNewButton.setEnabled(everythingSelected());
			}
		});
		mapList.setSelectedIndex(0);
		missionList.setSelectedIndex(0);
		btnNewButton.setEnabled(everythingSelected());
	}
}
