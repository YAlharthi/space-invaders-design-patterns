public class WonFactory extends SpriteFactory<Won> {

	@Override
	protected Won createSprite(int x, int y) {
		return new Won(x, y);
	}

	@Override
	protected String imagePath() {
		return "/img/won.jpg";
	}
}
