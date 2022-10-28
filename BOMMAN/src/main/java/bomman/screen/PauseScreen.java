package bomman.screen;

import bomman.MainGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static bomman.screen.MenuScreen.startGame;

public class PauseScreen {
    public void switchToGame(javafx.event.ActionEvent actionEvent) throws IOException {
        MainGame.pauseGame = false;
        if(!startGame) {
            Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            MainGame game = new MainGame();
            game.start(window);
        } else {
            Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Scene scene = new Scene(MainGame.root);
            window.setScene(scene);
            window.show();
        }
    }

    public void switchToSettings(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Settings.fxml")));
        Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(MainGame.class.getResource("/bomman/css/settings.css")).toExternalForm();
        scene.getStylesheets().add(css);
        window.setScene(scene);
        window.show();
    }
}
