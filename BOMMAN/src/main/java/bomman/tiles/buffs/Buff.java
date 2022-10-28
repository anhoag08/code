package bomman.tiles.buffs;

import bomman.entity.Flame;
import bomman.tiles.CommonTiles;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Buff extends CommonTiles {

	public static int coolDown = 500;

	public static List<Buff> buffs = new ArrayList<Buff>();

	public static int getCoolDown() {
		return coolDown;
	}

	public static void setCoolDown(int coolDown) {
		Buff.coolDown = coolDown;
	}

	public Buff(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
	}

	@Override
	public void update() {

	}
}
