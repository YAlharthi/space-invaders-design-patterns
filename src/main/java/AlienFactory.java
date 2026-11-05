public class AlienFactory extends SpriteFactory<Alien> {

	private final BombFactory bombFactory = new BombFactory();

	@Override
	protected Alien createSprite(int x, int y) {
		return new Alien(x, y, bombFactory.spawn(x, y));
	}

	@Override
	protected String imagePath() {
		return "/img/alien.png";
	}
}
