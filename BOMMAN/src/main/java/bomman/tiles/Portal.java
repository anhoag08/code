package bomman.tiles;

import bomman.manager.Sprite;
import bomman.manager.SpriteSheet;
import javafx.scene.image.Image;

/**
 * Portal to move to another map.
 */
public class Portal extends CommonTiles {

	public Portal(int xUnit, int yUnit) {
		super(xUnit, yUnit, (new Sprite(Sprite.DEFAULT_SIZE, 6, 5, SpriteSheet.dungeonTiles, 16, 16)).getFxImage());
	}

	@Override
	public void update() {

	}
}
