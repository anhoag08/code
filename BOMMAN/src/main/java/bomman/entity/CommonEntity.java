package bomman.entity;

import bomman.manager.GameManager;
import bomman.manager.Sprite;
import bomman.tiles.CommonTiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class will control the common attributes, operations and functions of all entities.
 */
public abstract class CommonEntity {
    // Character size
    public static final int CHARACTER_SIZE = 64;

    // Variables for identifying the position of an entity
    private int xPosition;
    private int yPosition;

    private int alive = 0;

    private DIRECTION direct;

    private Image img;

    public CommonEntity(int xUnit, int yUnit, Image img) {
        this.xPosition = xUnit * Sprite.SCALED_SIZE;
        this.yPosition = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.direct = DIRECTION.DOWN;
    }

    // Enum class to decide which direction the living entities choose to move.
    public enum DIRECTION {
        // Enum for direction
        DOWN(0, 1),
        UP(0, -1),
        RIGHT(1, 0),
        LEFT(-1, 0),
        COLLIDE(0, 0);

        // Private int for declaration
        public int moveX;
        public int moveY;

        // Constructor for direction
        DIRECTION(final int _moveX, final int _moveY) {
            moveX = _moveX;
            moveY = _moveY;
        }
    }

    public static boolean collisionWithTiles(CommonEntity entity, CommonTiles tile) {
        int entityLeft = entity.getXPosition() + entity.getDirect().moveX;
        int entityRight = entity.getXPosition() + entity.getDirect().moveX + Sprite.SCALED_SIZE;
        int entityTop = entity.getYPosition() + entity.getDirect().moveY;
        int entityBottom = entity.getYPosition() + entity.getDirect().moveY + Sprite.SCALED_SIZE;

        int tileLeft = tile.xTile;
        int tileRight = tile.xTile + Sprite.SCALED_SIZE;
        int tileTop = tile.yTile;
        int tileBottom = tile.yTile + Sprite.SCALED_SIZE;

        if (entityBottom <= tileTop || entityTop >= tileBottom || entityRight <= tileLeft || entityLeft >= tileRight)
            return false;
        return true;

    }

    public static boolean collisionWithEntity(CommonEntity entity1, CommonEntity entity2) {

        int entity1Left = entity1.getXPosition() + entity1.getDirect().moveX;
        int entity1Right = entity1.getXPosition() + entity1.getDirect().moveX + Sprite.SCALED_SIZE;
        int entity1Top = entity1.getYPosition() + entity1.getDirect().moveY;
        int entity1Bottom = entity1.getYPosition() + entity1.getDirect().moveY + Sprite.SCALED_SIZE;

        int entity2Left = entity2.getXPosition() + entity2.getDirect().moveX;
        int entity2Right = entity2.getXPosition() + entity2.getDirect().moveX + Sprite.SCALED_SIZE;
        int entity2Top = entity2.getYPosition() + entity2.getDirect().moveY;
        int entity2Bottom = entity2.getYPosition() + entity2.getDirect().moveY + Sprite.SCALED_SIZE;

        if (entity1Bottom < entity2Top || entity1Top > entity2Bottom || entity1Right < entity2Left || entity1Left > entity2Right)
            return false;
        return true;
    }

    public static boolean collisionWithFlame(CommonEntity entity, Flame flame) {

        int entity1Left = entity.getXPosition() + entity.getDirect().moveX;
        int entity1Right = entity.getXPosition() + entity.getDirect().moveX + Sprite.SCALED_SIZE;
        int entity1Top = entity.getYPosition() + entity.getDirect().moveY;
        int entity1Bottom = entity.getYPosition() + entity.getDirect().moveY + Sprite.SCALED_SIZE;

        int entity2Left = flame.getXPosition() + 20;
        int entity2Right = flame.getXPosition() + Sprite.SCALED_SIZE - 20;
        int entity2Top = flame.getYPosition() + 20;
        int entity2Bottom = flame.getYPosition() + Sprite.SCALED_SIZE - 20;

        if (entity1Bottom < entity2Top || entity1Top > entity2Bottom || entity1Right < entity2Left || entity1Left > entity2Right)
            return false;
        return true;
    }

    public static boolean collideBlocks (CommonEntity entity, int[][] map, CommonTiles[][] tiles) {
        for (int i = 0; i < GameManager.GAME_HEIGHT; i++) {
            for (int j = 0; j < GameManager.GAME_WIDTH; j++) {
                if (map[i][j] != 0 && collisionWithTiles(entity, tiles[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    // Moving function for the entities.
    public void move(DIRECTION direct, int velocity) {
        xPosition += direct.moveX * velocity;
        yPosition += direct.moveY * velocity;
        this.direct = direct;
    }

    /**
     * Getters and Setters.
     */
    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public DIRECTION getDirect() {
        return direct;
    }

    public DIRECTION getOppositeDirect() {
        if (this.getDirect() == DIRECTION.UP) {
            return DIRECTION.DOWN;
        }
        if (this.getDirect() == DIRECTION.DOWN) {
            return DIRECTION.UP;
        }
        if (this.getDirect() == DIRECTION.RIGHT) {
            return DIRECTION.LEFT;
        }
        if (this.getDirect() == DIRECTION.LEFT) {
            return DIRECTION.DOWN;
        }
        return DIRECTION.COLLIDE;
    }

    public void setDirect(DIRECTION direction) {
        this.direct = direction;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc, double t) {
        gc.drawImage(img, xPosition, yPosition);
    }

    public abstract void update();

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
