/**
 * Factory Method: the creator. spawn() is the one route for putting a sprite in
 * the game. It handles the steps every sprite shares and leaves the choice of
 * concrete class to a subclass through createSprite().
 */
public abstract class SpriteFactory<T extends Sprite> {

	public final T spawn(int x, int y) {
		T sprite = createSprite(x, y);
		SpriteGraphic graphic = SpriteGraphicPool.get(imagePath());
		sprite.setGraphic(graphic);
		graphicAssigned(sprite, graphic);
		return sprite;
	}

	/** The factory method. Each subclass decides which sprite gets built. */
	protected abstract T createSprite(int x, int y);

	/** The picture this kind of sprite starts with. */
	protected abstract String imagePath();

	/** Optional step for a sprite that needs to read its picture. */
	protected void graphicAssigned(T sprite, SpriteGraphic graphic) {
	}
}
