package xyz.upperlevel.graphicengine.gamelauncher.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameLoader {

    @SuppressWarnings("unchecked")
    public static Game load(File file) throws Exception {
        JarFile jar = new JarFile(file);
        Optional<String> eName = GameNeed.GAME_YAML.getValues()
                .stream()
                .filter(name -> jar.getJarEntry(name) != null)
                .findAny();
        if (!eName.isPresent())
            throw new IOException("Game file not found.");
        JarEntry e = jar.getJarEntry(eName.get());
        Map<String, Object> infoData = (Map<String, Object>) new Yaml().load(jar.getInputStream(e));
        String tmp;
        tmp = (String) infoData.get("main");
        if (tmp == null)
            throw new IOException("'main' field not found in game file.");
        URLClassLoader loader = URLClassLoader.newInstance(new URL[]{new URL("jar:file:" + file.getPath() + "!/")});
        Class<?> tmpClass = loader.loadClass(tmp);
        if (!Game.class.isAssignableFrom(tmpClass))
            throw new IllegalArgumentException("Game main class must extend " + Game.class.getName());
        Class<? extends Game> gameClass = tmpClass.asSubclass(Game.class);
        Constructor<? extends Game> gameCon;
        try {
            gameCon = gameClass.getDeclaredConstructor();
        } catch (NoSuchMethodException exception) {
            throw new IllegalStateException("Game main class constructor cannot have arguments.", exception);
        }
        gameCon.setAccessible(true);
        Game game;
        try {
            game = gameClass.newInstance();
        } catch (InstantiationException exception) {
            throw new IllegalArgumentException("Game main class cannot be initialized.", exception);
        }
        game.identity = GameIdentity.load(infoData);
        game.jar = jar;
        game.classLoader = loader;
        return game;
    }
}
