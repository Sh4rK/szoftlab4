package szoftlab4;

import javax.swing.JButton;
import javax.swing.JPanel;

public class View {
	JPanel panel;
	
	public View(Game game){
		Controller c = new Controller(game);
		JButton buildTower = new JButton();
		JButton buildObstacle = new JButton();
		JButton redGem = new JButton();
		JButton greenGem = new JButton();
		JButton blueGem = new JButton();
		JButton yellowGem = new JButton();
		JButton orangeGem = new JButton();
		JPanel menuPanel = new JPanel();
		JPanel mapPanel = new JPanel();
		
		panel = new JPanel();
		
		panel.add(menuPanel);
		panel.add(mapPanel);
		
		menuPanel.add(buildTower);
		menuPanel.add(buildObstacle);
		menuPanel.add(redGem);
		menuPanel.add(greenGem);
		menuPanel.add(blueGem);
		menuPanel.add(yellowGem);
		menuPanel.add(orangeGem);
		
		buildTower.addMouseListener(c.new BuildTowerMouseEvent());
		buildObstacle.addMouseListener(c.new BuildObstacleMouseEvent());
		redGem.addMouseListener(c.new EnchantMouseEvent());
		greenGem.addMouseListener(c.new EnchantMouseEvent());
		blueGem.addMouseListener(c.new EnchantMouseEvent());
		yellowGem.addMouseListener(c.new EnchantMouseEvent());
		orangeGem.addMouseListener(c.new EnchantMouseEvent());
		
		mapPanel.addMouseListener(c.new MapMouseEvent());
	}
	
	public void enemyAdded(Enemy en) {
		// TODO Auto-generated method stub
		
	}

	public void projectileExploded(Projectile p) {
		// TODO Auto-generated method stub
		
	}

	public void projectileAdded(Projectile p) {
		// TODO Auto-generated method stub
		
	}

	public void enemyDied(Enemy en) {
		// TODO Auto-generated method stub
		
	}

	public void towerAdded(Tower t) {
		// TODO Auto-generated method stub
		
	}

	public void obstacleAdded(Obstacle o) {
		// TODO Auto-generated method stub
		
	}

	public void drawAll() {
		// TODO Auto-generated method stub
		
	}

}
