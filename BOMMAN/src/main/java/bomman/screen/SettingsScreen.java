package bomman.screen;

import bomman.MainGame;
import bomman.manager.SoundManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsScreen implements Initializable {
    @FXML
    private Slider sfx;

    @FXML
    private Slider bgm;

    @FXML
    private Label sfxl;

    @FXML
    private Label bgml;

    @FXML
    private Button back;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SoundManager.bgMusic.setVolume(bgm.getValue() * 0.01);
        bgml.setText(Integer.toString((int) bgm.getValue()) + " %");

        SoundManager.setSfxVolume(sfx.getValue() * 0.01);
        sfxl.setText(Integer.toString((int) sfx.getValue()) + " %");

        bgm.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                SoundManager.bgMusic.setVolume(bgm.getValue() * 0.01);
                bgml.setText(Integer.toString((int) bgm.getValue()) + " %");
            }
        });
        sfx.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                SoundManager.setSfxVolume(sfx.getValue() * 0.01);
                sfxl.setText(Integer.toString((int) sfx.getValue()) + " %");
            }
        });
    }

    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println(MainGame.pauseGame);
        if(!MainGame.pauseGame) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Menu.fxml")));
            Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            String css = Objects.requireNonNull(MainGame.class.getResource("/bomman/css/menu.css")).toExternalForm();
            scene.getStylesheets().add(css);
            window.setScene(scene);
            window.show();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainGame.class.getResource("/bomman/fxml/Pause.fxml")));
            Stage window = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Scene scene = new Scene(root);
            String css = Objects.requireNonNull(MainGame.class.getResource("/bomman/css/menu.css")).toExternalForm();
            scene.getStylesheets().add(css);
            window.setScene(scene);
            window.show();
        }
    }
}
