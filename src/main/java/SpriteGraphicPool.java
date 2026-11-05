import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ImageIcon;

/**
 * Flyweight factory: hands out one shared SpriteGraphic per image path. The
 * game loop thread and the Swing thread both ask for graphics, so the map has
 * to be safe for concurrent use.
 */
public final class SpriteGraphicPool {

	private static final Map<String, SpriteGraphic> POOL = new ConcurrentHashMap<>();

	private SpriteGraphicPool() {
	}

	public static SpriteGraphic get(String path) {
		return POOL.computeIfAbsent(path, SpriteGraphicPool::load);
	}

	static int size() {
		return POOL.size();
	}

	private static SpriteGraphic load(String path) {
		return new SpriteGraphic(new ImageIcon(SpriteGraphicPool.class.getResource(path)).getImage());
	}
}
