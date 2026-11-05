/**
 * Small direct checks of the pattern classes. Gameplay itself is covered by
 * BehaviourCheck.
 */
public class PatternChecks {

	private static int passed;

	public static void main(String[] args) {
		factoriesBuildTheRightSprites();
		builderLaysOutTheWaveInReadingOrder();
		builderRejectsAnImpossibleWave();
		spritesOfOneKindShareAGraphic();
		System.out.println("PASS: " + passed + " pattern checks");
	}

	private static void factoriesBuildTheRightSprites() {
		SpriteFactory<Alien> aliens = new AlienFactory();
		Alien alien = aliens.spawn(10, 20);
		check(alien.getX() == 10 && alien.getY() == 20, "alien keeps its position");
		check(alien.getBomb() != null && alien.getBomb().isDestroyed(), "alien arrives with an idle bomb");

		SpriteFactory<? extends Sprite> screen = new WonFactory();
		check(screen.spawn(0, 0) instanceof Won, "won factory builds Won");
		screen = new GameOverFactory();
		check(screen.spawn(0, 0) instanceof GameOver, "game over factory builds GameOver");
	}

	private static void builderLaysOutTheWaveInReadingOrder() {
		AlienWave wave = new AlienWaveBuilder(new AlienFactory()).rows(2).columns(3).origin(100, 50).spacing(20)
				.build();
		check(wave.size() == 6, "2 rows of 3 gives 6 aliens");
		Alien last = null;
		for (Alien a : wave) {
			last = a;
		}
		check(last.getX() == 140 && last.getY() == 70, "last alien sits at the bottom right");
	}

	private static void builderRejectsAnImpossibleWave() {
		boolean rejected = false;
		try {
			new AlienWaveBuilder(new AlienFactory()).rows(0).columns(6).build();
		} catch (IllegalStateException expected) {
			rejected = true;
		}
		check(rejected, "a wave with no rows is refused");
	}

	private static void spritesOfOneKindShareAGraphic() {
		SpriteGraphic first = SpriteGraphicPool.get("/img/alien.png");
		int poolSize = SpriteGraphicPool.size();
		check(first == SpriteGraphicPool.get("/img/alien.png"), "same path returns the same flyweight");
		check(poolSize == SpriteGraphicPool.size(), "a repeat request adds nothing to the pool");
		check(first != SpriteGraphicPool.get("/img/bomb.png"), "a different path gets its own flyweight");
	}

	private static void check(boolean condition, String what) {
		if (!condition) {
			System.out.println("FAIL: " + what);
			System.exit(1);
		}
		passed++;
	}
}
