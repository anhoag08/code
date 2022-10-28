package bomman.manager;

import bomman.entity.Bomb;
import bomman.entity.CommonEntity;
import bomman.entity.MainCharacter;
import bomman.entity.enemy.*;
import bomman.manager.GameManager;
import bomman.manager.Sprite;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will manage all the entities' action.
 * Add, Remove, ...
 */
public class EntityManager {

    public static List<CommonEntity> entities = new ArrayList<CommonEntity>();
	public static int numberEntities = 6;
    public static MainCharacter bomberman;

    public EntityManager() {
        // entities = new ArrayList<CommonEntity>();
    }

    public static boolean hasBomb(int row, int col) {
        for (Bomb b : Bomb.bombs) {
            if (b.getXPosition() / Sprite.SCALED_SIZE == row && b.getYPosition() / Sprite.SCALED_SIZE == col) {
                System.out.println("true");
                return true;
            }
        }
        return false;
    }

    public static int generateRandomX() {
		int max = 29;
		int min = 1;
		return (int)Math.floor(Math.random()*(max-min+1)+min);
	}

	public static int generateRandomY() {
		int max = 19;
		int min = 1;
		return (int)Math.floor(Math.random()*(max-min+1)+min);
	}

	public void addEntities(int xPos, int yPos) {

	}

    public static void createEntity() {
		for (int i = 1; i <= numberEntities; i++) {
			int xRand = generateRandomX();
			int yRand = generateRandomY();
			while(GameManager.map[yRand][xRand] != 0) {
				xRand = generateRandomX();
				yRand = generateRandomY();
			}
			if (i == 1) entities.add(new FirstEnemy(xRand, yRand, Sprite.balloon_left1.getFxImage()));
			if (i == 2) entities.add(new SecondEnemy(xRand, yRand, Sprite.balloon_left1.getFxImage()));
			if (i == 3) entities.add(new DoubleLifeEnemy(xRand, yRand, Sprite.balloon_left1.getFxImage()));
			if (i == 4) entities.add(new SuicidalEnemy(xRand, yRand, Sprite.balloon_left1.getFxImage()));
			if (i == 5) entities.add(new SmartEnemy(xRand, yRand, Sprite.doll_left1.getFxImage()));
			if (i == 6) entities.add(new InvisibleEnemy(xRand, yRand, Sprite.balloon_left1.getFxImage()));
	    }
        //entities.add(new FirstEnemy(generateRandomX(), generateRandomY(), Sprite.balloon_left1.getFxImage()));
	    //entities.add(new SecondEnemy(generateRandomX(), generateRandomY(), Sprite.balloon_left1.getFxImage()));
	    //entities.add(new DoubleLifeEnemy(generateRandomX(), generateRandomY(), Sprite.balloon_left1.getFxImage()));
	    //entities.add(new SuicidalEnemy(generateRandomX(), generateRandomY(), Sprite.balloon_left1.getFxImage()));
        //entities.add(new SmartEnemy(generateRandomX(), generateRandomY(), Sprite.doll_left1.getFxImage()));
		//entities.add(new InvisibleEnemy(generateRandomX(), generateRandomY(), Sprite.balloon_left1.getFxImage()));
    }

    public static void createMainCharacter() {
        bomberman = new MainCharacter(MainCharacter.PLAYER_START_X, MainCharacter.PLAYER_START_Y, Sprite.newPlayer_right.getFxImage());
    }

    public static void removeDeathEntity() {
        entities.removeIf(i -> i.getAlive() == 2);
    }
}
