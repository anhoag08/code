package bomman.tiles;

import bomman.entity.Flame;
import bomman.manager.Sprite;
import bomman.manager.SpriteSheet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Tiles that can be destroyed by flame.
 */
public class BreakableTiles extends CommonTiles {

	private int count = 0;



	public BreakableTiles(int xUnit, int yUnit) {
		super(xUnit, yUnit, Sprite.newBrick.getFxImage());
	}

	@Override
	public void update(){

	}

	@Override
	public void render(GraphicsContext gc, double t) {
		double frame = (int) (t / 0.083) % 12 % 3;
		if (!isExist) {
			this.setImg(Sprite.newFloor.getFxImage());
		}
		if (isRendering) {
			count++;
			if (count == 10) this.setImg(Sprite.newBrick1.getFxImage());
			if (count == 20) this.setImg(Sprite.newBrick2.getFxImage());
			if (count == 30) this.setImg(Sprite.newBrick3.getFxImage());
			if (count == 40) this.setImg(Sprite.newBrick4.getFxImage());
			if (count == 50) this.setImg(Sprite.newBrick5.getFxImage());
			if (count == 60) this.setImg(Sprite.newBrick6.getFxImage());
			if (count == 70) this.setImg(Sprite.newBrick5.getFxImage());
			if (count == 80) this.setImg(Sprite.newBrick4.getFxImage());
			if (count == 90) this.setImg(Sprite.newBrick3.getFxImage());
			if (count == 100) this.setImg(Sprite.newBrick2.getFxImage());
			if (count == 110) this.setImg(Sprite.newBrick1.getFxImage());
			if (count == 111) count = 0;
		} else {
			count = 0;
		}
		gc.drawImage(getImg(), getXTile(), getYTile());
	}
}
