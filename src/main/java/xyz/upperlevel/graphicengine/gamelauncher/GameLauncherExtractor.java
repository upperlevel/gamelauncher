package xyz.upperlevel.graphicengine.gamelauncher;

import lombok.Getter;
import xyz.upperlevel.graphicengine.gamelauncher.util.Extractor;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GameLauncherExtractor {

    public static final GameLauncherExtractor $ = new GameLauncherExtractor();

    @Getter
    public final Extractor extractor;

    public GameLauncherExtractor() {
        extractor = new Extractor();
        extractor.load(getClass().getClassLoader().getResourceAsStream("extractor.yml"));
    }

    public void extract() throws IOException {
        extractor.extract();
    }

    private Extractor.Resource getPresentRes(String id) {
        Optional<Extractor.Resource> res = extractor.getResource(id);
        if (!res.isPresent())
            throw new IllegalStateException("Resource not found for: " + id);
        return res.get();
    }

    public File getLauncherGuiFXML() {
        return getPresentRes("launcher_gui_fxml").getFile();
    }

    public File getLauncherGuiCSS() {
        return getPresentRes("launcher_gui_css").getFile();
    }

    public File getMainPresentationIndexFile() {
        return getPresentRes("main_presentation").getFile();
    }

    public File getGamePresentationIndexFile() {
        return getPresentRes("game_presentation").getFile();
    }

    public static GameLauncherExtractor $() {
        return $;
    }
}