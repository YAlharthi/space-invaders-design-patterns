import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 * Flyweight: the part of a sprite that is the same for every sprite of a kind.
 * Instances hold only the picture and never change, so one object can safely
 * serve all 24 aliens. Position is not stored here. Each sprite passes its own
 * x and y in when drawing.
 */
public final class SpriteGraphic {

	private final Image image;

	SpriteGraphic(Image image) {
		this.image = image;
	}

	public void draw(Graphics g, int x, int y, ImageObserver observer) {
		g.drawImage(image, x, y, observer);
	}

	public int getWidth() {
		return image.getWidth(null);
	}
}
