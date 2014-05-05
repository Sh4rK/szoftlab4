package szoftlab4;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Resources {
	public static Image TowerImage;
	public static Image ObstacleImage;
	public static Image RedGemImage;
	public static Image GreenGemImage;
	public static Image BlueGemImage;
	public static Image YellowGemImage;
	public static Image OrangeGemImage;
	public static Image HumanImage;
	public static Image DwarfImage;
	public static Image ElfImage;
	public static Image HobbitImage;
	public static Image BackgroundImage;
	public static Image MountainsImage;
	public static Image ProjectileImage;
	public static Image SplitterProjectileImage;
	public static Image LZImage;

	public static void load() throws IOException {
		TowerImage = ImageIO.read(new File("icons/tower.png"));
		ObstacleImage = ImageIO.read(new File("icons/obstacle.png"));
		RedGemImage = ImageIO.read(new File("icons/red_gem.png"));
		GreenGemImage = ImageIO.read(new File("icons/green_gem.png"));
		BlueGemImage = ImageIO.read(new File("icons/blue_gem.png"));
		YellowGemImage = ImageIO.read(new File("icons/yellow_gem.png"));
		OrangeGemImage = ImageIO.read(new File("icons/orange_gem.png"));
		HumanImage = ImageIO.read(new File("icons/human.png"));
		DwarfImage = ImageIO.read(new File("icons/dwarf.png"));
		ElfImage = ImageIO.read(new File("icons/elf.png"));
		HobbitImage = ImageIO.read(new File("icons/hobbit.png"));
		BackgroundImage = ImageIO.read(new File("icons/background.png"));
		MountainsImage = ImageIO.read(new File("icons/saurontower.png"));
		ProjectileImage = ImageIO.read(new File("icons/projectile.png"));
		SplitterProjectileImage = ImageIO.read(new File("icons/splitter_projectile.png"));
		LZImage = ImageIO.read(new File("icons/LZF.png"));
	}
}
