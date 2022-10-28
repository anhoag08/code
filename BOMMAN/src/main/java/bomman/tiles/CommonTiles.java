package bomman.tiles;

import bomman.entity.CommonEntity;
import bomman.entity.MainCharacter;
import bomman.manager.GameManager;
import bomman.manager.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class will control all common attributes, functions and operations of tiles.
 */
public abstract class CommonTiles {


	public int xTile;
	public int yTile;

	public boolean isExist = true;
	public boolean isRendering = true;

	private Image img;

	public Image getImg() {
		return this.img;
	}

	public int getXTile() {
		return xTile;
	}

	public int getYTile() {
		return yTile;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public void setXTile(int xTile) {
		this.xTile = xTile;
	}

	public void setYTile(int yTile) {
		this.yTile = yTile;
	}

	public CommonTiles (int xUnit, int yUnit, Image img) {
		this.xTile = xUnit * Sprite.SCALED_SIZE;
		this.yTile = yUnit * Sprite.SCALED_SIZE;
		this.img = img;
	}

	public void render(GraphicsContext gc, double t) {
		gc.drawImage(img, xTile, yTile);
	}

	public abstract void update();

}
