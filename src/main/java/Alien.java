/**
 * 
 * @author 
 */
public class Alien extends Sprite {

    private Bomb bomb;

    /*
     * Constructor
     */
    Alien(int x, int y, Bomb bomb) {
        this.x = x;
        this.y = y;
        this.bomb = bomb;
    }

    public void act(int direction) {
        this.x += direction;
    }

    /*
     * Getters & Setters
     */
    
	public Bomb getBomb() {
		return bomb;
	}

}
