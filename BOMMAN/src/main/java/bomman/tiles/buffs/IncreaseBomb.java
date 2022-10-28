package bomman.tiles.buffs;

import bomman.entity.Bomb;
import bomman.entity.MainCharacter;
import bomman.manager.SoundManager;
import bomman.manager.Sprite;
import bomman.tiles.CommonTiles;
import javafx.scene.image.Image;

/**
 * Increase damage to the bomb.
 */
public class IncreaseBomb extends Buff {

	public static void executeBuff(MainCharacter mainCharacter) {
		SoundManager.buff.play();
		Bomb.bombLimit++;
	}

	public IncreaseBomb(int xUnit, int yUnit) {
		super(xUnit, yUnit, Sprite.powerup_bombs.getFxImage());
	}

	@Override
	public void update() {

	}
}
