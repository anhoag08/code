package bomman.manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

    private String _path;
    public final int SIZE;
    public int[] _pixels;
    public BufferedImage image;

    public static SpriteSheet tiles = new SpriteSheet("/bomman/gfx/textures/classic.png", 256);
    //public static SpriteSheet grassTiles = new SpriteSheet("/bomman/gfx/textures/grass.png", 256);
    public static SpriteSheet dungeonTiles = new SpriteSheet("/bomman/gfx/textures/dungeon.png", 256);
    public static SpriteSheet explosion = new SpriteSheet("/bomman/gfx/textures/explosion.png", 256);
    public static SpriteSheet entities = new SpriteSheet("/bomman/gfx/textures/entity.png", 256);


    public SpriteSheet(String path, int size) {
        _path = path;
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            URL a = SpriteSheet.class.getResource(_path);
            if(a != null) {
                image = ImageIO.read(a);
                int w = image.getWidth();
                int h = image.getHeight();
                image.getRGB(0, 0, w, h, _pixels, 0, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}