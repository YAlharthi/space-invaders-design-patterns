public class ShotFactory extends SpriteFactory<Shot> {

	@Override
	protected Shot createSprite(int x, int y) {
		return new Shot(x, y);
	}

	@Override
	protected String imagePath() {
		return "/img/shot.png";
	}
}
