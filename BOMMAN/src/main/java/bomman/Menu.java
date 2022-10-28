package bomman;

import bomman.manager.SoundManager;
import bomman.manager.Sprite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Menu extends Application {
    static Scene mainScene;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 30;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Menu.fxml")));
        Scene scene = new Scene(root, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        String css = Objects.requireNonNull(MainGame.class.getResource("/bomman/css/menu.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        SoundManager.init();
        SoundManager.bgMusic.play();
    }
}
