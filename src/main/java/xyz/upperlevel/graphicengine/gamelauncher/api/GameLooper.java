package xyz.upperlevel.graphicengine.gamelauncher.api;

import lombok.Getter;
import xyz.upperlevel.graphicengine.api.window.GLFW;
import xyz.upperlevel.graphicengine.api.window.Window;

public class GameLooper {

    @Getter
    public final Game game;

    @Getter
    public final Window window;

    private Window newWindow() {
        Window window = GLFW.createWindow(750, 500, "Initializing window...");
        window.centerPosition();
        return window;
    }

    public GameLooper(Game game) {
        this.game = game;
        game.looper = this;
        window = newWindow();
    }

    /**
     * Starts a new loop for the referring game.
     */
    public void start() {
        window.contextualize();
        window.show();
        game.start();
        do {
            game.loop();
            window.update();
        } while (!window.isClosed());
        game.close();
        window.close();
    }

    /**
     * Closes a this loop for the referring game.
     */
    public void close() {
        window.close();
    }

    public static GameLooper detach(Game game) {
        return new GameLooper(game);
    }
}