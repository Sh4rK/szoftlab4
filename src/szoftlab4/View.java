package szoftlab4;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View {
	JPanel panel;
	JPanel mapPanel;
	JPanel menuPanel;
	List<Drawable> drawables;
	
	public View(Game game, Map map){
		Controller c = new Controller(game);
		
		JButton buildTower = new JButton();
		JButton buildObstacle = new JButton();
		GemButton redGem = new GemButton(TowerGem.red);
		GemButton greenGem = new GemButton(TowerGem.green);
		GemButton blueGem = new GemButton(TowerGem.blue);
		GemButton yellowGem = new GemButton(ObstacleGem.yellow);
		GemButton orangeGem = new GemButton(ObstacleGem.orange);
		drawables = new ArrayList<Drawable>();
		drawables.add(new GraphicMap(map));
		menuPanel = new JPanel();
		/* wow such anonymous class */
		mapPanel = new JPanel(){
			List<Drawable> d;
			public JPanel init(List<Drawable> dr){
				d = dr;
				return this;
			}
			public void paintComponent(Graphics g) {
				/* very thread-safe */
				synchronized(d){
				    for (Drawable dr : d)
				    	dr.draw(g);
				}
			}
		}.init(drawables);
		mapPanel.setPreferredSize(new Dimension(800, 600));
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(menuPanel, BorderLayout.PAGE_START);
		panel.add(mapPanel, BorderLayout.CENTER);
		
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setPreferredSize(new Dimension(0, 55));
		menuPanel.add(buildTower);
		menuPanel.add(redGem);
		menuPanel.add(greenGem);
		menuPanel.add(blueGem);
		
		menuPanel.add(buildObstacle);
		menuPanel.add(yellowGem);
		menuPanel.add(orangeGem);
		
		buildTower.addMouseListener(c.new BuildTowerMouseEvent());
		redGem.addMouseListener(c.new EnchantMouseEvent());
		greenGem.addMouseListener(c.new EnchantMouseEvent());
		blueGem.addMouseListener(c.new EnchantMouseEvent());
		
		buildObstacle.addMouseListener(c.new BuildObstacleMouseEvent());
		yellowGem.addMouseListener(c.new EnchantMouseEvent());
		orangeGem.addMouseListener(c.new EnchantMouseEvent());
		
		mapPanel.addMouseListener(c.new MapMouseEvent());

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

	}
	
	private void setButtonLook(JButton b, ImageIcon img){
		/*b.setOpaque(false);
		b.setIcon(img);
		b.setContentAreaFilled(false);*/
		/*b.setForeground(new Color(0, 0, 0));
		b.setBackground(new Color(255, 255, 255));*/
		//b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setMargin(new Insets(1,1,1,1));
		b.setContentAreaFilled(false);
		b.setIcon(img);
		b.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public void enemyAdded(Enemy en) {
		synchronized(drawables){
			drawables.add(new GraphicEnemy(en));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	public void projectileExploded(Projectile p) {
		synchronized(drawables){
			drawables.remove(new GraphicProjectile(p));
		}
	}

	public void projectileAdded(Projectile p) {
		synchronized(drawables){
			drawables.add(new GraphicProjectile(p));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	public void enemyDied(Enemy en) {
		synchronized(drawables){
			drawables.remove(new GraphicEnemy(en));
		}
	}

	public void towerAdded(Tower t) {
		synchronized(drawables){
			drawables.add(new GraphicTower(t));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	public void obstacleAdded(Obstacle o) {
		synchronized(drawables){
			drawables.add(new GraphicObstacle(o));
			Collections.sort(drawables, Collections.reverseOrder());
		}
	}

	public void drawAll() {
		mapPanel.repaint();
	}

}
