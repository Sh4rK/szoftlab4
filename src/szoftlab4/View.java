package szoftlab4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class View {
	JPanel panel;
	
	public View(Game game){
		game.view = this; // bocsi, ezt én kontárkodtam ide - Török
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
		panel.setLayout(new BorderLayout());
		
		panel.add(menuPanel, BorderLayout.PAGE_START);
		panel.add(mapPanel, BorderLayout.CENTER);
		
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setMargin(new Insets(1,1,1,1));
		b.setContentAreaFilled(false);
		b.setIcon(img);
	}
	
	public JPanel getPanel(){
		return panel;
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
