package bomman;

import bomman.entity.Bomb;
import bomman.entity.CommonEntity;
import bomman.manager.*;
import bomman.entity.Flame;
import bomman.event.EventHandling;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainGame extends Application {
    public static Group root;
    static Scene mainScene;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 21;

    private GraphicsContext gc;
    private Canvas canvas;
    final long startNanoTime = System.nanoTime();

    public static boolean pauseGame = false;

    public static void main(String[] args) {
        launch(args);
    }

    public void  startGame(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root = new Group();
        // Tao root container
        root.getChildren().add(canvas);

        // Tao scene
        mainScene = new Scene(root);
        mainScene.setFill(Color.BLACK);
        EventHandling.prepareActionHandlers(mainScene);

        // Add scene to stage
        stage.setScene(mainScene);
        stage.show();

        GameManager.createMap();
        TilesManager.createTiles();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                GameManager.nextStage();
                if (GameManager.isLost()) {
                    SoundManager.dead.play();
                    this.stop();
                    GameManager.restart();
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Lost.fxml")));
                        Scene scene = new Scene(root, Sprite.SCALED_SIZE * 40, Sprite.SCALED_SIZE * 30);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (GameManager.isWon()) {
                    this.stop();
                    GameManager.restart();
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Win.fxml")));
                        Scene scene = new Scene(root, Sprite.SCALED_SIZE * 40, Sprite.SCALED_SIZE * 30);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(pauseGame) {
                    try {
                        this.stop();
                        Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Pause.fxml")));
                        Scene scene = new Scene(root, Sprite.SCALED_SIZE * 40, Sprite.SCALED_SIZE * 30);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                update();
                render(t);
            }
        };
        timer.start();


        EntityManager.createMainCharacter();
        //EntityManager.createEntity();
    }

    @Override
    public void start(Stage stage) throws IOException {
        startGame(stage);
    }

    public void update() {
        //Bomb.bombs.forEach(Bomb::countDown);
        Bomb.countDown();
        Bomb.countDown();
        Bomb.createFlame();
        Flame.flameCountdown();
        EntityManager.removeDeathEntity();
        EntityManager.bomberman.update();
        //Bomb.bombs.forEach(Bomb::update);
        EntityManager.entities.forEach(CommonEntity::update);
    }

    public void render(double t) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // gameManager.stillObjects.forEach(g -> g.render(gc, t));
        for (int i = 0; i < GameManager.GAME_HEIGHT; i++) {
            for (int j = 0; j < GameManager.GAME_WIDTH; j++) {
                TilesManager.gameTiles[i][j].render(gc, t);
            }
        }
        EntityManager.entities.forEach(g -> g.render(gc, t));
        Bomb.bombs.forEach(g -> g.render(gc, t));
        Flame.flames.forEach(g -> g.render(gc, t));
        //gameManager.gameTiles.forEach(g -> g.render(gc, t));
        EntityManager.bomberman.render(gc, t);
    }
}