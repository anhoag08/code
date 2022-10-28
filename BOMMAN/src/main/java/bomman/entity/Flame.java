package bomman.entity;

import bomman.manager.GameManager;
import bomman.manager.Sprite;
import bomman.manager.TilesManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will control the flame created from the bomb.
 */
public class Flame extends CommonEntity{
	private int xPos;
	private int yPos;
	private int timeExplode = 50;
	private int cnt = 0;

	public static List<Flame> flames = new ArrayList<Flame>();

	public Flame(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
//		this.timeExplode = timeExplode;
	}

	/**
	 * Getters and setters
	 */
	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getTimeExplode() {
		return timeExplode;
	}

	public void setTimeExplode(int timeExplode) {
		this.timeExplode = timeExplode;
	}

	public static int generateRandom() {
		int max = 20;
		int min = 1;
		return (int)Math.floor(Math.random()*(max-min+1)+min);
	}

	public static boolean checkFlameValid(int xPos, int yPos) {
		// if (GameManager.getGameManager().map[yPos][xPos] == 1) return false;
		int chances = generateRandom();
		if (yPos <= 0 || yPos >= 20 || xPos <= 0 || xPos >= 30) return false;
		else if (GameManager.map[yPos][xPos] == 2) {
			if (chances == 1) {
				GameManager.map[yPos][xPos] = 4;
				TilesManager.gameTiles[yPos][xPos].isExist = true;
				TilesManager.gameTiles[yPos][xPos].isRendering = false;
				TilesManager.gameTiles[yPos][xPos].setImg(Sprite.powerup_speed.getFxImage());
				return true;
			} else if (chances == 2) {
				GameManager.map[yPos][xPos] = 5;
				TilesManager.gameTiles[yPos][xPos].isExist = true;
				TilesManager.gameTiles[yPos][xPos].isRendering = false;
				TilesManager.gameTiles[yPos][xPos].setImg(Sprite.powerup_flames.getFxImage());
				return true;
			} else if (chances == 3) {
				GameManager.map[yPos][xPos] = 6;
				TilesManager.gameTiles[yPos][xPos].isExist = true;
				TilesManager.gameTiles[yPos][xPos].isRendering = false;
				TilesManager.gameTiles[yPos][xPos].setImg(Sprite.powerup_bombs.getFxImage());
				return true;
			} else if (chances == 4) {
				GameManager.map[yPos][xPos] = 7;
				TilesManager.gameTiles[yPos][xPos].isExist = true;
				TilesManager.gameTiles[yPos][xPos].isRendering = false;
				TilesManager.gameTiles[yPos][xPos].setImg(Sprite.powerup_detonator.getFxImage());
				return true;
			} else {
				GameManager.map[yPos][xPos] = 0;
				TilesManager.gameTiles[yPos][xPos].setImg(Sprite.newFloor.getFxImage());
				TilesManager.gameTiles[yPos][xPos].isExist = false;
				TilesManager.gameTiles[yPos][xPos].isRendering = false;
				return true;
			}
		} else if (GameManager.map[yPos][xPos] != 0) {
			return false;
		}
		else return true;
	}

	public static void flameCountdown() {
		List<Flame> removingFlame = new ArrayList<>();
		for (Flame f: flames) {
			f.setTimeExplode(f.timeExplode - 1);
			if (f.timeExplode == 0) {
				removingFlame.add(f);
			}
		}
		for (Flame f: removingFlame) {
			flames.remove(f);
		}
	}

	@Override
	public void update() {

	}

	@Override
	public void render(GraphicsContext gc, double t) {
		double frame = (int) (t / 0.083) % 27 % 9;
		if (timeExplode >= 0) {
			cnt++;
			if (cnt == 5) this.setImg(Sprite.newBomb_exploded.getFxImage());
			if (cnt == 10) this.setImg(Sprite.newBomb_exploded1.getFxImage());
			if (cnt == 15) this.setImg(Sprite.newBomb_exploded2.getFxImage());
			if (cnt == 20) this.setImg(Sprite.newBomb_exploded3.getFxImage());
			if (cnt == 25) this.setImg(Sprite.newBomb_exploded4.getFxImage());
			if (cnt == 30) this.setImg(Sprite.newBomb_exploded5.getFxImage());
			if (cnt == 35) this.setImg(Sprite.newBomb_exploded6.getFxImage());
			if (cnt == 40) this.setImg(Sprite.newBomb_exploded7.getFxImage());
			if (cnt == 45) this.setImg(Sprite.newBomb_exploded8.getFxImage());

//			if (frame == 0) this.setImg(Sprite.newBomb_exploded.getFxImage());
//			if (frame == 1) this.setImg(Sprite.newBomb_exploded1.getFxImage());
//			if (frame == 2) this.setImg(Sprite.newBomb_exploded2.getFxImage());
//			if (frame == 3) this.setImg(Sprite.newBomb_exploded3.getFxImage());
//			if (frame == 4) this.setImg(Sprite.newBomb_exploded4.getFxImage());
//			if (frame == 5) this.setImg(Sprite.newBomb_exploded5.getFxImage());
//			if (frame == 6) this.setImg(Sprite.newBomb_exploded6.getFxImage());
//			if (frame == 7) this.setImg(Sprite.newBomb_exploded7.getFxImage());
//			if (frame == 8) this.setImg(Sprite.newBomb_exploded8.getFxImage());
		}
		gc.drawImage(getImg(), getXPosition(), getYPosition());
	}
}
