public class GameOverFactory extends SpriteFactory<GameOver> {

	@Override
	protected GameOver createSprite(int x, int y) {
		return new GameOver(x, y);
	}

	@Override
	protected String imagePath() {
		return "/img/gameover.png";
	}
}
