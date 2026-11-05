public class PlayerFactory extends SpriteFactory<Player> {

	@Override
	protected Player createSprite(int x, int y) {
		return new Player(x, y);
	}

	@Override
	protected String imagePath() {
		return "/img/craft.png";
	}

	@Override
	protected void graphicAssigned(Player player, SpriteGraphic graphic) {
		player.setWidth(graphic.getWidth());
	}
}
