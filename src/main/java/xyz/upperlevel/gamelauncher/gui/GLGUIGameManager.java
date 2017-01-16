package xyz.upperlevel.gamelauncher.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.gamelauncher.api.Game;
import xyz.upperlevel.gamelauncher.api.LauncherControlPanel;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class GLGUIGameManager {

    @Getter
    public final TabPane pane;
    private final Map<Tab, Game> games = new HashMap<>();

    public void setup(Game game) {
        Tab tab = new Tab();
        tab.setText(game.getIdentity().getId());
        {
            WebView view = new WebView();
            String url;
            {
                URL pres = game.getClassLoader().getResource("presentation/index.html");
                url = pres != null ? pres.toExternalForm() : getClass().getClassLoader().getResource("resources/gui/presentations/game/index.html").toExternalForm();
                System.out.println("Loading presentation from: " + url);
            }
            view.getEngine().load(url);
            tab.setContent(view);
        }
        pane.getTabs().add(tab);
        games.put(tab, game);
    }

    public void setup() {
        LauncherControlPanel.instance().getGames().forEach(this::setup);
    }

    public void load(File file) throws Exception {
        LauncherControlPanel.instance().load(file);
        setup();
    }

    public Optional<Game> getGame(Tab tab) {
        return Optional.ofNullable(games.get(tab));
    }

    public Collection<Game> getGames() {
        return games.values();
    }
}