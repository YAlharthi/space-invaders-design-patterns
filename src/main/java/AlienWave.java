import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Builder: the product. A finished formation of aliens. Only AlienWaveBuilder
 * makes one, and its size cannot change afterwards.
 */
public class AlienWave implements Iterable<Alien> {

	private final List<Alien> aliens;

	AlienWave(List<Alien> aliens) {
		this.aliens = Collections.unmodifiableList(new ArrayList<>(aliens));
	}

	public int size() {
		return aliens.size();
	}

	@Override
	public Iterator<Alien> iterator() {
		return aliens.iterator();
	}
}
