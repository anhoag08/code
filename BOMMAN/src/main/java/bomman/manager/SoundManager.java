package bomman.manager;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

/**
 * This class will manage all the sounds in the game.
 */
public class SoundManager {
    /**
     * bg music.
     */
    public static MediaPlayer bgMusic;

    /**
     * entities sfx.
     */

    public static AudioClip bomb;
    public static AudioClip buff;
    public static AudioClip speed;

    /**
     * bomber sfx.
     */
    public static AudioClip walk1;
    public static AudioClip walk2;
    public static AudioClip dead;

    /**
     * all audio path.
     */
    public static String bomb_p = "/bomman/sfx/explosion.mp3";
    public static String buff_p = "/bomman/sfx/buff.mp3";
    public static String speed_p = "/bomman/sfx/speed.mp3";
    public static String walk_1_p = "/bomman/sfx/walk1.mp3";
    public static String walk_2_p = "/bomman/sfx/walk2.mp3";
    public static String dead_p = "/bomman/sfx/dead.mp3";

    public static String bgMusic_p = "/bomman/sfx/bg.mp3";

    public static void init() {
        try {
            bomb = new AudioClip(SoundManager.class.getResource(bomb_p).toURI().toString());
            buff = new AudioClip(SoundManager.class.getResource(buff_p).toURI().toString());
            speed = new AudioClip(SoundManager.class.getResource(speed_p).toURI().toString());
            walk1 = new AudioClip(SoundManager.class.getResource(walk_1_p).toURI().toString());
            walk2 = new AudioClip(SoundManager.class.getResource(walk_2_p).toURI().toString());
            dead = new AudioClip(SoundManager.class.getResource(dead_p).toURI().toString());

            Media bg_music = new Media(SoundManager.class.getResource(bgMusic_p).toString());
            bgMusic = new MediaPlayer(bg_music);
            bgMusic.setCycleCount(MediaPlayer.INDEFINITE);

            bgMusic.setVolume(0.05);
            bomb.setVolume(0.2);
            buff.setVolume(0.2);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void setSfxVolume(double d) {
        bomb.setVolume(d);
        buff.setVolume(d);
        walk1.setVolume(d);
        walk2.setVolume(d);
        speed.setVolume(d);
        dead.setVolume(d);
    }


}