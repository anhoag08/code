package bomman.tiles.buffs;

import bomman.MainGame;
import bomman.entity.MainCharacter;
import bomman.manager.SoundManager;
import bomman.manager.Sprite;
import bomman.tiles.CommonTiles;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Speed the character up.
 */
public class SpeedUp extends Buff {

	public static boolean isExecuting = false;

	public static void executeBuff(MainCharacter mainCharacter) {
		isExecuting = true;
		SoundManager.speed.play();
		mainCharacter.setCharacterVelocity(mainCharacter.getCharacterVelocity() * 2);
	}

	public SpeedUp(int xUnit, int yUnit) {
		super(xUnit, yUnit, Sprite.powerup_speed.getFxImage());
		this.setCoolDown(100);
	}

	@Override
	public void update() {
		this.setCoolDown(this.getCoolDown() - 1);
		if (this.getCoolDown() == 0) {
			this.setCoolDown(0);
			MainCharacter.setCharacterVelocity(MainCharacter.getCharacterVelocity() / 2);
			SoundManager.speed.stop();
			this.isExecuting = false;
		}
		if (this.getCoolDown() < 0) {
			this.setCoolDown(0);
		}
	}
}
