package xyz.upperlevel.graphicengine.gamelauncher.api;

import lombok.*;

import java.util.jar.JarFile;

@NoArgsConstructor
public abstract class Game {

    GameIdentity identity;
    JarFile jar;
    ClassLoader classLoader;

    @Getter
    GameLooper looper;

    /**
     * Gets game identity.
     */
    public final GameIdentity getIdentity() {
        return identity;
    }

    /**
     * Gets game jar file.
     */
    public final JarFile getJar() {
        return jar;
    }

    /**
     * Gets game class loader.
     */
    public final ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Called on game looper starting.
     */
    public void start() {
    }

    /**
     * Called on game looper looping.
     */
    public void loop() {
    }

    /**
     * Called on game looper close.
     */
    public void close() {
    }
}