package bomman.manager;

import bomman.manager.GameManager;
import bomman.manager.Sprite;
import bomman.tiles.*;
import bomman.tiles.buffs.DestroyedMode;
import bomman.tiles.buffs.IncreaseBomb;
import bomman.tiles.buffs.IncreaseRange;
import bomman.tiles.buffs.SpeedUp;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will manage all the entities' action.
 * Add, Remove, ...
 */
public class TilesManager {

	public static CommonTiles[][] gameTiles = new CommonTiles[GameManager.GAME_HEIGHT][GameManager.GAME_WIDTH];

	public static void createTiles() {
		for (int i = 0; i < GameManager.GAME_HEIGHT; i++) {
			for (int j = 0; j < GameManager.GAME_WIDTH; j++) {
				if (GameManager.map[i][j] == 1) {
					gameTiles[i][j] = new UnbreakableTiles(j, i);
				} else if (GameManager.map[i][j] == 2) {
					gameTiles[i][j] = new BreakableTiles(j, i);
				} else if (GameManager.map[i][j] == 3) {
					gameTiles[i][j] = new Portal(j, i);
				} else if (GameManager.map[i][j] == 4) {
					gameTiles[i][j] = new SpeedUp(j, i);
				} else if (GameManager.map[i][j] == 5) {
					gameTiles[i][j] = new IncreaseRange(j, i);
				} else if (GameManager.map[i][j] == 6) {
					gameTiles[i][j] = new IncreaseBomb(j, i);
				} else if (GameManager.map[i][j] == 7) {
					gameTiles[i][j] = new DestroyedMode(j, i);
				} else {
					gameTiles[i][j] = new Floor(j, i);
				}
			}
		}
	}

	public static void update() {
		for (int i = 0; i < GameManager.GAME_HEIGHT; i++) {
			for (int j = 0; j < GameManager.GAME_WIDTH; j++) {
				gameTiles[i][j].update();
			}
		}
	}
}
