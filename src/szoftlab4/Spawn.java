package szoftlab4;

/**
 * @author Szabó Antal
 */
public class Spawn {
    public Enemy enemy;
    public double timeToSpawn;

    public Spawn(Enemy enemy, double tts) {
        this.enemy = enemy;
        this.timeToSpawn = tts;
    }
}
