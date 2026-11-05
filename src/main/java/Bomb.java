/**
 * 
 * @author 
 */
public class Bomb extends Sprite {

	private boolean destroyed;

	/*
	 * Constructor
	 */
	Bomb(int x, int y) {
		setDestroyed(true);
		this.x = x;
		this.y = y;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
