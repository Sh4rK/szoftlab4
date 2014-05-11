package szoftlab4;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A játék grafikus felületének fő osztálya, a kirajzoláshoz szükséges grafikus elemeket és a kirajzolandó
 * játékobjektumokat tartalmazza. Kezeli a modelben történő változásokat.
 */
@SuppressWarnings("serial")
public class View {
	JPanel panel;
	JPanel mapPanel;
	JPanel mainPanel;
	JPanel menuPanel;
	JPanel magicPanel;
	List<Drawable> drawables;
	Drawable placing;

	/**
	 * Konstruktor. Létrehozza az összes a képernyőn megjelenő gombot, panelt.
	 * Beállítja ezeknek az eseménykezelőit.
	 */
	public View(Game game, Map map){
		Controller c = new Controller(game, this);

		JButton buildTower = new JButton();
		JButton buildObstacle = new JButton();
		JButton pause = new JButton(" P ");
		GemButton redGem = new GemButton(TowerGem.red);
		GemButton greenGem = new GemButton(TowerGem.green);
		GemButton blueGem = new GemButton(TowerGem.blue);
		GemButton yellowGem = new GemButton(ObstacleGem.yellow);
		GemButton orangeGem = new GemButton(ObstacleGem.orange);
		JLabel magic = new JLabel("Magic: ");
		drawables = new ArrayList<Drawable>();
		drawables.add(new GraphicMap(map));
		menuPanel = new JPanel();
		mainPanel = new JPanel();
		magicPanel = new JPanel();

		/* wow such anonymous class */
		mapPanel = new JPanel(){
			List<Drawable> d;
			public JPanel init(List<Drawable> dr){
				d = dr;
				return this;
			}

			/**
			 * Kirajzolja az összes játékban lévő objektumot
			 */
			public void paintComponent(Graphics g) {
				/* very thread-safe */
				synchronized(d){
				    for (Drawable dr : d)
				    	dr.draw(g);
					if (placing != null) {
						Graphics2D g2 = (Graphics2D)g;
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
						placing.draw(g);
					}
				}
			}
		}.init(drawables);
		mapPanel.setPreferredSize(new Dimension(800, 600));

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(mainPanel, BorderLayout.PAGE_START);
		panel.add(mapPanel, BorderLayout.CENTER);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(menuPanel);
		mainPanel.add(magicPanel, BorderLayout.EAST);

		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setPreferredSize(new Dimension(0, 55));
		menuPanel.add(buildTower);
		menuPanel.add(redGem);
		menuPanel.add(greenGem);
		menuPanel.add(blueGem);

		menuPanel.add(buildObstacle);
		menuPanel.add(yellowGem);
		menuPanel.add(orangeGem);

		menuPanel.add(pause);

		magicPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));


		Font magicFont = new Font("Consolas",Font.PLAIN,26);
		magic.setFont(magicFont);
		magicPanel.add(magic);

		redGem.setToolTipText("<html><center>Piros varázskő<br/>\nTöbb sebezés<br/>\n" + TowerGem.red.getCost() + " VE</center></html>");
		greenGem.setToolTipText("<html><center>Zöld varázskő<br/>\nNagyobb hatókör<br/>\n" + TowerGem.green.getCost() + " VE</center></html>");
		blueGem.setToolTipText("<html><center>Kék varázskő<br/>\nGyorsabb tüzelés<br/>\n" + TowerGem.blue.getCost() + " VE</center></html>");

		yellowGem.setToolTipText("<html><center>Sárga varázskő<br/>\nNagyobb hatókör<br/>\n" + ObstacleGem.yellow.getCost() + " VE</center></html>");
		orangeGem.setToolTipText("<html><center>Narancssárga varázskő<br/>\nJobb lassítás<br/>\n" + ObstacleGem.orange.getCost() + " VE</center></html>");

		buildTower.setToolTipText("<html><center>Torony építése<br/>\n" + Tower.cost + " VE</center></html>");
		buildObstacle.setToolTipText("<html><center>Akadály építése<br/>\n" + Obstacle.cost + " VE</center></html>");


		buildTower.addMouseListener(c.new BuildTowerMouseEvent());
		redGem.addMouseListener(c.new EnchantMouseEvent());
		greenGem.addMouseListener(c.new EnchantMouseEvent());
		blueGem.addMouseListener(c.new EnchantMouseEvent());

		buildObstacle.addMouseListener(c.new BuildObstacleMouseEvent());
		yellowGem.addMouseListener(c.new EnchantMouseEvent());
		orangeGem.addMouseListener(c.new EnchantMouseEvent());

		mapPanel.addMouseListener(c.new MapMouseEvent());
		mapPanel.addMouseMotionListener(c.new MapMouseEvent());
		
		/* comeatmebro cheat */
		mapPanel.addKeyListener(new KeyAdapter(){
			String typed = "";
			public void keyPressed(KeyEvent e){
				typed += e.getKeyChar();
				if (typed.contains("comeatmebro")){
					if (Tower.comeatmebro == false)
						Tower.comeatmebro = true;
					else
						Tower.comeatmebro = false;
					
					typed = "";
				}
			}
		});

		menuPanel.addMouseListener(c.new MenuPanelMouseEvent());
		
		pause.addMouseListener(c.new pauseMouseEvent());
		pause.setFont(magicFont);
		setButtonLook(pause, null);

		buildTower.setBackground(menuPanel.getBackground());
		setButtonLook(buildTower, new ImageIcon("icons/tower.png"));

		buildObstacle.setBackground(menuPanel.getBackground());
		setButtonLook(buildObstacle, new ImageIcon("icons/obstacle.png"));

		redGem.setBackground(menuPanel.getBackground());
		setButtonLook(redGem, new ImageIcon("icons/red_gem.png"));

		greenGem.setBackground(menuPanel.getBackground());
		setButtonLook(greenGem, new ImageIcon("icons/green_gem.png"));

		blueGem.setBackground(menuPanel.getBackground());
		setButtonLook(blueGem, new ImageIcon("icons/blue_gem.png"));

		yellowGem.setBackground(menuPanel.getBackground());
		setButtonLook(yellowGem, new ImageIcon("icons/yellow_gem.png"));

		orangeGem.setBackground(menuPanel.getBackground());
		setButtonLook(orangeGem, new ImageIcon("icons/orange_gem.png"));
		
		mapPanel.requestFocusInWindow();
	}

	/**
	 * Beállítja egy JButton kinézetét
	 */
	private void setButtonLook(JButton b, ImageIcon img){
		b.setFocusPainted(false);
		b.setMargin(new Insets(1,1,1,1));
		b.setContentAreaFilled(false);
		b.setIcon(img);
		b.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	}

	/**
	 * Bármilyen drawable hozzáadásához.
	 * Anonymous class-okkal lehet használni.
	 * FPS számlálóhoz kell.
	 */
	public void addDrawable(Drawable d){
		synchronized(drawables){
			drawables.add(d);
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	public JPanel getPanel(){
		return panel;
	}

	/**
	 * Hozzáad egy ellenséget a kirajzolandó objektumokhoz.
	 */
	public void enemyAdded(Enemy en) {
		synchronized(drawables){
			drawables.add(new GraphicEnemy(en));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	/**
	 * Kitöröl egy már célba ért lövedéket a kirajzolandó objektumok közül.
	 */
	public void projectileExploded(Projectile p) {
		synchronized(drawables){
			drawables.remove(new GraphicProjectile(p));
		}
	}

	/**
	 * Hozzáad egy lövedéket a kirajzolandó objektumokhoz.
	 */
	public void projectileAdded(Projectile p) {
		synchronized(drawables){
			drawables.add(new GraphicProjectile(p));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	/**
	 * Kitöröl egy ellenséget a kirajzolandó objektumok közül.
	 */
	public void enemyDied(Enemy en) {
		synchronized(drawables){
			drawables.remove(new GraphicEnemy(en));
		}
	}

	/**
	 * Hozzáad egy tornyot a kirajzolandó objektumokhoz.
	 */
	public void towerAdded(Tower t) {
		synchronized(drawables){
			drawables.add(new GraphicTower(t));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	/**
	 * Hozzáad egy akadályt a kirajzolandó objektumokhoz.
	 */
	public void obstacleAdded(Obstacle o) {
		synchronized(drawables){
			drawables.add(new GraphicObstacle(o));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	/**
	 * Hozzáad egy gem-et egy már a kirajzolandó listában lévő toronyhoz.
	 */
	public void towerEnchanted(Tower t){
		synchronized(drawables){
			GraphicTower gt = (GraphicTower)drawables.get(drawables.indexOf(new GraphicTower(t)));
			gt.setGem();
		}
	}

	/**
	 * Hozzáad egy gem-et egy már a kirajzolandó listában lévő akadályhoz.
	 */
	public void obstacleEnchanted(Obstacle o){
		synchronized(drawables){
			GraphicObstacle go = (GraphicObstacle)drawables.get(drawables.indexOf(new GraphicObstacle(o)));
			go.setGem();
		}
	}

	/**
	 * Beállítja az éppen lerakásra váró kirajzolható objektumot.
	 */
	public void setPlacing(Drawable d) {
		placing = d;
	}

	/**
	 * Kirajzolja a kirajzolandó objektumokat.
	 */
	public void drawAll() {
		mapPanel.repaint();
	}

	/**
	 * Frissíti a kiírt varázserő mennyiségét.
	 * @param magic Az új érték.
	 */
	public void magicChange(int magic){
		JLabel magicLabel = (JLabel)magicPanel.getComponent(0);
		magicLabel.setText("Magic: " + magic);
	}

	/**
	 * Kiír a képernyőre egy szöveget és kirak egy képet
	 * A mapPanel-re való kattintás után visszatér a metódus 
	 */
	private void winLoseScreen(String message, Image img) {
		synchronized(drawables){
			drawables.add(new Drawable(){
					String msg;
					public Drawable init(String st, Image i){
						msg = st;
						img = i;
						z_index = 10;

						return this;
					}
					public void draw(Graphics g) {
						Graphics2D g2 = (Graphics2D)g;
						g2.setColor(Color.white);
						g2.setFont(new Font("Consolas", Font.BOLD, 36));

						g2.setRenderingHint(
						        RenderingHints.KEY_TEXT_ANTIALIASING,
						        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

						FontMetrics fm = g2.getFontMetrics();
				        Rectangle2D r = fm.getStringBounds(msg, g2);

				        int x = (800 - (int) r.getWidth()) / 2;
				        int y = ((600 - (int) r.getHeight()) / 3 + fm.getAscent());

						g2.drawString(msg, x, y);

						if (img != null)
							g2.drawImage(img, 800/2, 600/2, null);

						g2.setRenderingHint(
						        RenderingHints.KEY_TEXT_ANTIALIASING,
						        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
					}
				}.init(message, img));
		}

		drawAll();
		Object lock = new Object(); // szinkronizációs objektum
		mapPanel.addMouseListener(new MouseAdapter(){
			Object lock;
			public MouseListener init(Object o){
				lock = o;
				return this;
			}
			/**
			 * Gombnyomásra értesíti a lock objektumot 
			 */
			public void mousePressed(MouseEvent e){
				synchronized(lock){
					lock.notify();
				}
			}
		}.init(lock));
		/* vár amíg meg nem nyomunk egy egérgombot */
		synchronized(lock){
			try {
				lock.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Kirajzolja a vesztes képernyőt 
	 */
	public void gameLost() {
		winLoseScreen("Sajnálom kollega, vesztett", null);
	}

	/**
	 * Kirajzolja a nyertes képernyőt 
	 */
	public void gameWon() {
		winLoseScreen("Kíváló munka, kollega!", Resources.LZImage);
	}
}
