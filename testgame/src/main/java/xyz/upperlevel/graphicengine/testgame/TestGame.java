package xyz.upperlevel.graphicengine.testgame;

import xyz.upperlevel.graphicengine.gamelauncher.api.Game;

public class TestGame extends Game {
    @Override
    public void start() {
        System.out.println("STARTING");
    }

    @Override
    public void loop() {
        System.out.println("LOOPING");
    }

    @Override
    public void close() {
        System.out.println("CLOSING");
    }
}
