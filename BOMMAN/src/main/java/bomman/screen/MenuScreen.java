package bomman.screen;

import bomman.MainGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuScreen {
    @FXML
    private Button quit;

    @FXML
    private Button settings;

    public static boolean startGame = false;

    public void switchToGame(javafx.event.ActionEvent actionEvent) throws IOException {
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

    public void quit(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You are exitting the game");
        alert.setContentText("Are you sure to exit ?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            window.close();
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
