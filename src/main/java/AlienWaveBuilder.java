import java.util.ArrayList;
import java.util.List;

/**
 * Builder: collects a wave's layout one named step at a time, checks it, and
 * only then creates the aliens. All five settings are ints, so a plain
 * constructor call would let them be swapped without any compile error.
 */
public class AlienWaveBuilder {

	private final SpriteFactory<Alien> alienFactory;
	private int rows = 1;
	private int columns = 1;
	private int originX;
	private int originY;
	private int spacing = 18;

	public AlienWaveBuilder(SpriteFactory<Alien> alienFactory) {
		this.alienFactory = alienFactory;
	}

	public AlienWaveBuilder rows(int rows) {
		this.rows = rows;
		return this;
	}

	public AlienWaveBuilder columns(int columns) {
		this.columns = columns;
		return this;
	}

	public AlienWaveBuilder origin(int x, int y) {
		this.originX = x;
		this.originY = y;
		return this;
	}

	public AlienWaveBuilder spacing(int spacing) {
		this.spacing = spacing;
		return this;
	}

	public AlienWave build() {
		if (rows < 1 || columns < 1 || spacing < 1) {
			throw new IllegalStateException(
					"A wave needs at least one row, one column and a positive spacing");
		}
		List<Alien> aliens = new ArrayList<>();
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				aliens.add(alienFactory.spawn(originX + spacing * column, originY + spacing * row));
			}
		}
		return new AlienWave(aliens);
	}
}
