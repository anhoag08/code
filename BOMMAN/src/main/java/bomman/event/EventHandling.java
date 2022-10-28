package bomman.event;

import bomman.MainGame;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Scanner;

/**
 * This class will handle all the events that occur in the game.
 * Collision, ...
 */
public class EventHandling {
    public static HashSet<String> currentlyActiveKeys;
    public static String keyPressed;

    public static void prepareActionHandlers(Scene mainScene) {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }
}
