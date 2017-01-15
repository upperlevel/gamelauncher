package xyz.upperlevel.graphicengine.gamelauncher.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import xyz.upperlevel.graphicengine.gamelauncher.GameLauncherExtractor;

import java.io.FileInputStream;
import java.io.IOException;

public class GameLauncherMainGUI extends Application {

    @Getter
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        try {
            GameLauncherExtractor.$().extract();
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot setup resources");
            alert.setHeaderText("Error during resources creation");
            alert.setContentText(e.toString());
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new GameLauncherMainGUIController(this));
        Parent root = loader.load(new FileInputStream(GameLauncherExtractor.$().getLauncherGuiFXML()));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}