package bomman.tiles;

import bomman.manager.Sprite;
import bomman.manager.SpriteSheet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Tiles that are unbreakable by the flame (of the bomb).
 */
public class UnbreakableTiles extends CommonTiles {



    public UnbreakableTiles(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.newWall.getFxImage());
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc, double t) {
        super.render(gc, t);
    }
}
