package bomman.screen;

import bomman.MainGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WinScreen {
    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Menu.fxml")));
        Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(MainGame.class.getResource("/bomman/css/menu.css")).toExternalForm();
        scene.getStylesheets().add(css);
        window.setScene(scene);
        window.show();
    }
}
