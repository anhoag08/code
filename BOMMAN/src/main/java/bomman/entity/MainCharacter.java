package bomman.entity;

import bomman.MainGame;
import bomman.event.EventHandling;
import bomman.manager.*;
import bomman.tiles.CommonTiles;
import bomman.tiles.Portal;
import bomman.manager.TilesManager;
import bomman.tiles.buffs.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * This will control the main character =))))))) like, obviously =))))))))
 */
public class MainCharacter extends CommonEntity {

    // Start point of main character (can be changed so not 'final' type)
    public static int PLAYER_START_X = 1;
    public static int PLAYER_START_Y = 1;

    private int count = 0;

    // Speed of the main character
    private static int characterVelocity = 2;
    private static int explosionTimeCharacter = 300;

    private List<CommonEntity.DIRECTION> pathToDoor;
    private List<CommonEntity.DIRECTION> pathToTiles;

    private static AStarAlgorithm aStar = new AStarAlgorithm();

    public MainCharacter(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    /**
     * Getters.
     */

    @Override
    public int getXPosition() {
        return super.getXPosition();
    }

    @Override
    public int getYPosition() {
        return super.getYPosition();
    }

    /**
     * Getters and Setters.
     */
    public static int getCharacterVelocity() {
        return characterVelocity;
    }

    public static void setCharacterVelocity(int characterVelocity) {
        MainCharacter.characterVelocity = characterVelocity;
    }

    public static void setPlayerStartX(int playerStartX) {
        PLAYER_START_X = playerStartX;
    }

    public static void setPlayerStartY(int playerStartY) {
        PLAYER_START_Y = playerStartY;
    }

    public void collideMainCharacter(MainCharacter mainCharacter, int[][] map, CommonTiles[][] tiles) {
        for (int i = 0; i < GameManager.GAME_HEIGHT; i++) {
            for (int j = 0; j < GameManager.GAME_WIDTH; j++) {
                if (map[i][j] != 0 && map[i][j] != 9 && collisionWithTiles(mainCharacter, tiles[i][j])) {
                    int value = map[i][j];
                    if (value == 3) {
                        GameManager.map[i][j] = 0;
                        GameManager.currentStage++;
                        GameManager.nextStage = true;
                        if (GameManager.currentStage == GameManager.STAGE_NUMBER) {
                            GameManager.won = true;
                        }
                        break;
                    } else if (value == 4) {
                        GameManager.map[i][j] = 0;
                        TilesManager.gameTiles[i][j].setImg(Sprite.newFloor.getFxImage());
                        Buff.buffs.add(new SpeedUp(j, i));
                        SpeedUp.executeBuff(mainCharacter);
                    } else if (value == 5) {
                        GameManager.map[i][j] = 0;
                        TilesManager.gameTiles[i][j].setImg(Sprite.newFloor.getFxImage());
                        Buff.buffs.add(new IncreaseRange(j, i));
                        IncreaseRange.executeBuff(mainCharacter);
                    } else if (value == 6) {
                        GameManager.map[i][j] = 0;
                        TilesManager.gameTiles[i][j].setImg(Sprite.newFloor.getFxImage());
                        Buff.buffs.add(new IncreaseBomb(j, i));
                        IncreaseBomb.executeBuff(mainCharacter);
                    } else if (value == 7) {
                        GameManager.map[i][j] = 0;
                        TilesManager.gameTiles[i][j].setImg(Sprite.newFloor.getFxImage());
                        Buff.buffs.add(new DestroyedMode(j, i));
                        DestroyedMode.executeBuff(mainCharacter);
                    } else {
                        mainCharacter.setDirect(DIRECTION.COLLIDE);
                    }
                }
                for (CommonEntity e : EntityManager.entities) {
                    if (collisionWithEntity(mainCharacter, e)) {
                        mainCharacter.setDirect(DIRECTION.COLLIDE);
                        setAlive(1);
                    }
                }
                for (Flame f: Flame.flames) {
                    if (collisionWithFlame(mainCharacter, f)) {
                        GameManager.lost = true;
                    }
                }
            }
        }
    }

    public void findPath(int[][] gridGame, int startX, int startY, int desX, int desY) {
        aStar.aStarSearch(gridGame, startX, startY, desX, desY);
        pathToDoor = aStar.getPathToDes();
    }

    public void findPathToTiles(int[][] gridGame, int startX, int startY, int desX, int desY) {
        aStar.aStarSearch(gridGame, startX, startY, desX, desY);
        pathToTiles = aStar.getPathToDes();
    }

    public int distance(int x, int y, int difX, int difY) {
        return Math.abs(x - difX) + Math.abs(y - difY);
    }

//    public
//
//    public void findPlantingSpot() {
//
//    }

    public void destroyNearestBreakableTile(){
        for (CommonEntity e: EntityManager.entities) {
            this.findPathToTiles(GameManager.map,
                    this.getYPosition() / Sprite.SCALED_SIZE,
                    this.getXPosition() / Sprite.SCALED_SIZE,
                    e.getYPosition() / Sprite.SCALED_SIZE - 1, e.getXPosition() / Sprite.SCALED_SIZE);
            if (!pathToTiles.isEmpty()){
                this.setDirect(pathToTiles.get(0));
            } else {
                this.setDirect(DIRECTION.COLLIDE);
                Bomb.bombs.add(new Bomb(this.getXPosition()/Sprite.SCALED_SIZE, this.getYPosition()/Sprite.SCALED_SIZE,
                        Sprite.bomb.getFxImage(), 20));
            }
        }
    }

    public void aiAutoPlay() {
        this.findPath(GameManager.map,
                this.getYPosition() / Sprite.SCALED_SIZE,
                this.getXPosition() / Sprite.SCALED_SIZE,
                19, 29);
        if (getXPosition() % Sprite.SCALED_SIZE == 0 && getYPosition() % Sprite.SCALED_SIZE == 0) {
            if (!pathToDoor.isEmpty()) {
                this.setDirect(pathToDoor.get(0));
            } else {
                destroyNearestBreakableTile();
                this.setDirect(DIRECTION.COLLIDE);
            }
        }
        this.move(this.getDirect(), characterVelocity);
        collideMainCharacter(this, GameManager.map, TilesManager.gameTiles);
        pathToDoor.clear();
    }

    public void moveEvent() {
        if (getAlive() == 0) {
            if (EventHandling.currentlyActiveKeys.contains("LEFT")) {
                this.setDirect(DIRECTION.LEFT);
                collideMainCharacter(MainCharacter.this, GameManager.map, TilesManager.gameTiles);
                this.move(this.getDirect(), characterVelocity);
            }
            if (EventHandling.currentlyActiveKeys.contains("RIGHT")) {
                this.setDirect(DIRECTION.RIGHT);
                collideMainCharacter(MainCharacter.this, GameManager.map, TilesManager.gameTiles);
                this.move(this.getDirect(), characterVelocity);
            }
            if (EventHandling.currentlyActiveKeys.contains("UP")) {
                this.setDirect(DIRECTION.UP);
                collideMainCharacter(MainCharacter.this, GameManager.map, TilesManager.gameTiles);
                this.move(this.getDirect(), characterVelocity);
            }
            if (EventHandling.currentlyActiveKeys.contains("DOWN")) {
                this.setDirect(DIRECTION.DOWN);
                collideMainCharacter(MainCharacter.this, GameManager.map, TilesManager.gameTiles);
                this.move(this.getDirect(), characterVelocity);
            }
            if (EventHandling.currentlyActiveKeys.contains(("R"))) {
                this.aiAutoPlay();
            }
            if (EventHandling.currentlyActiveKeys.contains(("P"))) {
                MainGame.pauseGame = true;
            }
        }
    }

    public void plantBomb() {
        if (EventHandling.currentlyActiveKeys.contains("SPACE")) {
            int xPos = this.getXPosition() / Sprite.SCALED_SIZE;
            int yPos = this.getYPosition() / Sprite.SCALED_SIZE;
            if (!EntityManager.hasBomb(xPos, yPos) && Bomb.bombs.size() < Bomb.bombLimit) {
                Bomb.bombs.add(new Bomb(xPos, yPos, Sprite.bomb.getFxImage(), explosionTimeCharacter));
                GameManager.map[yPos][xPos] = 9;
            }
        }
    }

    public void autocorrect() {
        if (getDirect() == DIRECTION.COLLIDE) {
            if (EventHandling.currentlyActiveKeys.contains("UP") || EventHandling.currentlyActiveKeys.contains("DOWN")) {
                int delta = getXPosition() % Sprite.SCALED_SIZE;
                if (delta < 20) {
                    setXPosition(getXPosition() - delta);
                }
                if (delta > 44) {
                    setXPosition(getXPosition() + (64 - delta));
                }
            } else if (EventHandling.currentlyActiveKeys.contains("LEFT") || EventHandling.currentlyActiveKeys.contains("RIGHT")) {
                int delta = getYPosition() % Sprite.SCALED_SIZE;
                if (delta < 20) {
                    setYPosition(getYPosition() - delta);
                }
                if (delta > 44) {
                    setYPosition(getYPosition() + (64 - delta));
                }
            }
        }
    }

    public void coolDownBuff() {
        Buff.buffs.forEach(Buff::update);
        List<Buff> removeBuffs = new ArrayList<Buff>();
        for (Buff b : Buff.buffs) {
            if (Buff.getCoolDown() <= 0) {
                removeBuffs.add(b);
            }
        }
        for (Buff b : removeBuffs) {
            Buff.buffs.remove(b);
        }
    }

    @Override
    public void update() {
        moveEvent();
        autocorrect();
        plantBomb();
        coolDownBuff();
    }

    @Override
    public void render(GraphicsContext gc, double t) {
        double frame = (int) (t / 0.083) % 12 % 3;
        if (EventHandling.currentlyActiveKeys.size() != 0 && getDirect() != DIRECTION.COLLIDE) {
            if (frame == 0) count++;
            if (count == 13) {
                SoundManager.walk1.play();
            }
            if (count == 26) {
                SoundManager.walk2.play();
                count = 0;
            }
        }

        if (this.getDirect() == DIRECTION.LEFT) {
            if (EventHandling.currentlyActiveKeys.size() == 0) {
                this.setImg(Sprite.newPlayer_left.getFxImage());
            } else {
                if (frame == 0) this.setImg(Sprite.newPlayer_left.getFxImage());
                if (frame == 1) this.setImg(Sprite.newPlayer_left_1.getFxImage());
                if (frame == 2) this.setImg(Sprite.newPlayer_left_2.getFxImage());
            }
        }
        if (getAlive() == 1) {
            if (frame == 0) count++;
            if (count == 1) {
                this.setImg(Sprite.player_dead1.getFxImage());
            }
            if (count == 20) {
                this.setImg(Sprite.player_dead2.getFxImage());
            }
            if (count == 30) {
                this.setImg(Sprite.player_dead3.getFxImage());
            }
            if (count == 40) {
                GameManager.lost = true;
            }
        } else {
            if (this.getDirect() == DIRECTION.RIGHT) {
                if (EventHandling.currentlyActiveKeys.size() == 0) {
                    this.setImg(Sprite.newPlayer_right.getFxImage());
                } else {
                    if (frame == 0) this.setImg(Sprite.newPlayer_right.getFxImage());
                    if (frame == 1) this.setImg(Sprite.newPlayer_right_1.getFxImage());
                    if (frame == 2) this.setImg(Sprite.newPlayer_right_2.getFxImage());
                }
            }
            if (this.getDirect() == DIRECTION.UP) {
                if (EventHandling.currentlyActiveKeys.size() == 0) {
                    this.setImg(Sprite.newPlayer_up.getFxImage());
                } else {
                    if (frame == 0) this.setImg(Sprite.newPlayer_up.getFxImage());
                    if (frame == 1) this.setImg(Sprite.newPlayer_up_1.getFxImage());
                    if (frame == 2) this.setImg(Sprite.newPlayer_up_2.getFxImage());
                }
            }
            if (this.getDirect() == DIRECTION.DOWN) {
                if (EventHandling.currentlyActiveKeys.size() == 0) {
                    this.setImg(Sprite.newPlayer_down.getFxImage());
                } else {
                    if (frame == 0) this.setImg(Sprite.newPlayer_down.getFxImage());
                    if (frame == 1) this.setImg(Sprite.newPlayer_down_1.getFxImage());
                    if (frame == 2) this.setImg(Sprite.newPlayer_down_2.getFxImage());
                }
            }
        }
        gc.drawImage(getImg(), getXPosition(), getYPosition());
    }
}
