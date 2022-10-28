package bomman.tiles.buffs;

import bomman.entity.Bomb;
import bomman.entity.MainCharacter;
import bomman.manager.SoundManager;
import bomman.manager.Sprite;

public class DestroyedMode extends Buff {

	public static boolean isExecuting = false;

	public static void executeBuff(MainCharacter mainCharacter) {
		isExecuting = true;
		Bomb.bombLimit = Bomb.bombLimit*10;
		Bomb.explosionRadius = Bomb.explosionRadius*3;
		SoundManager.buff.play();
		MainCharacter.setCharacterVelocity(MainCharacter.getCharacterVelocity() * 2);
	}

	public DestroyedMode(int xUnit, int yUnit) {
		super(xUnit, yUnit, Sprite.powerup_detonator.getFxImage());
		this.setCoolDown(1000);
	}

	@Override
	public void update() {
		this.setCoolDown(this.getCoolDown() - 1);
		if (this.getCoolDown() == 0) {
			this.setCoolDown(0);
			MainCharacter.setCharacterVelocity(MainCharacter.getCharacterVelocity() / 2);
			Bomb.bombLimit = Bomb.bombLimit/10;
			Bomb.explosionRadius = Bomb.explosionRadius/3;
			this.isExecuting = false;
		}
		if (this.getCoolDown() < 0) {
			this.setCoolDown(0);
		}
	}
}
