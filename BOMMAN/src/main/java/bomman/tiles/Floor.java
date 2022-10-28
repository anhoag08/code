package bomman.tiles;

import bomman.manager.Sprite;
import bomman.manager.SpriteSheet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Floor extends CommonTiles{

//	public static int generateRandomX() {
//		int max = 7;
//		int min = 0;
//		return (int)Math.floor(Math.random()*(max-min+1)+min);
//	}
//
//	public static int generateRandomY() {
//		int max = 15;
//		int min = 0;
//		return (int)Math.floor(Math.random()*(max-min+1)+min);
//	}

//	public Floor(int xUnit, int yUnit) {
//		super(xUnit, yUnit, (new Sprite(Sprite.DEFAULT_SIZE, generateRandomX(), generateRandomY(), SpriteSheet.grassTiles, 16, 16)).getFxImage());
//	}

	//public Floor(int xUnit, int yUnit) {
	//	super(xUnit, yUnit, Sprite.grass.getFxImage());
	//}

	public static int generateRandom() {
		int max = 28;
		int min = 0;
		return (int)Math.floor(Math.random()*(max-min+1)+min);
	}

	public Floor(int xUnit, int yUnit) {
		super(xUnit, yUnit, Sprite.newFloor.getFxImage());
		int value = generateRandom();
		if (value == 0) {
			this.setImg(Sprite.newFloor.getFxImage());
		}
		if (value == 1) {
			this.setImg(Sprite.newFloor1.getFxImage());
		}
		if (value == 2) {
			this.setImg(Sprite.newFloor2.getFxImage());
		}
		if (value == 3) {
			this.setImg(Sprite.newFloor3.getFxImage());
		}
		if (value == 4) {
			this.setImg(Sprite.newFloor4.getFxImage());
		}
		if (value == 5) {
			this.setImg(Sprite.newFloor5.getFxImage());
		}
	}

	@Override
	public void update() {

	}

	@Override
	public void render(GraphicsContext gc, double t) {
		super.render(gc, t);
	}
}
