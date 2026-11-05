public class BombFactory extends SpriteFactory<Bomb> {

	@Override
	protected Bomb createSprite(int x, int y) {
		return new Bomb(x, y);
	}

	@Override
	protected String imagePath() {
		return "/img/bomb.png";
	}
}
