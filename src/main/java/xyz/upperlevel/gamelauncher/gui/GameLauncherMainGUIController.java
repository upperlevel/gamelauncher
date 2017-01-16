package xyz.upperlevel.gamelauncher.gui;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.gamelauncher.api.Game;
import xyz.upperlevel.gamelauncher.api.LauncherControlPanel;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class GameLauncherMainGUIController implements Initializable {

    @Getter
    public final GameLauncherMainGUI launcher;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab presentationTab;

    @FXML
    private Button startButton;

    @FXML
    private WebView webView;

    @FXML
    private void onLoadGame() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select game artifact");
        chooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Game", "*.jar"));
        File result = chooser.showOpenDialog(launcher.getStage());
        if (result == null)
            return;
        try {
            gameManager.load(result);
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Artifact content error");
            alert.setHeaderText("File corrupted");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    @FXML
    private void onStartButton() {
        System.out.println("Starting game...");
        Optional<Game> game = gameManager.getGame(getCurrentTab().getValue());
        if (game.isPresent()) {
            launcher.getStage().close();
            LauncherControlPanel.g().detachLooper(game.get()).start();
        }
    }

    @Getter
    private GLGUIGameManager gameManager;

    private ReadOnlyObjectProperty<Tab> getCurrentTab() {
        return tabPane.getSelectionModel().selectedItemProperty();
    }

    private void updateStartButton(Tab currentTab) {
        startButton.setDisable(presentationTab == currentTab);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameManager = new GLGUIGameManager(tabPane);
        webView.getEngine().load(getClass().getClassLoader().getResource("resources/gui/presentations/main/index.html").toExternalForm());
        getCurrentTab().addListener((observable, oldValue, newValue) -> updateStartButton(newValue));
        updateStartButton(getCurrentTab().getValue());
    }
}