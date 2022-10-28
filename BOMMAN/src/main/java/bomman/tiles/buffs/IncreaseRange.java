package bomman.tiles.buffs;

import bomman.entity.Bomb;
import bomman.entity.MainCharacter;
import bomman.manager.SoundManager;
import bomman.manager.Sprite;

/**
 * Increase the range of the bomb.
 */
public class IncreaseRange extends Buff {

    public static void executeBuff(MainCharacter mainCharacter) {
        SoundManager.buff.play();
        Bomb.explosionRadius++;
    }

    public IncreaseRange(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_flames.getFxImage());
    }

    @Override
    public void update() {

    }
}
