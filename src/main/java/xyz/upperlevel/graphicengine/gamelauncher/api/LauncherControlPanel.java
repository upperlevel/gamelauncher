package xyz.upperlevel.graphicengine.gamelauncher.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Graphic engine launcher control panel. Handles games and resources.
 * Useful for API usage.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LauncherControlPanel {

    public static final File GAMES_FOLDER = new File(FileUtils.getUserDirectory(), ".upperlevel/games");

    private static final LauncherControlPanel $ = new LauncherControlPanel().createResources();

    @Getter
    public final List<Game> games = new ArrayList<>();

    /**
     * Gets game from its groupId, id and version.
     */
    public Optional<Game> getGame(String groupId, String id, String version) {
        return getGame(new GameIdentity(groupId, id, version));
    }

    /**
     * Gets game from its identity.
     */
    public Optional<Game> getGame(GameIdentity identity) {
        return games.stream()
                .filter(game ->
                        identity.getGroupId().equals(identity.groupId) &&
                                identity.getId().equals(identity.id) &&
                                identity.getVersion().equals(identity.version))
                .findAny();
    }

    /**
     * Gets a list of games with the groupId equals to the one passed.
     */
    public List<Game> getGroupGames(String groupId) {
        return games.stream()
                .filter(game -> game.getIdentity().getGroupId().equals(groupId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a list of the same game versions.
     *
     * @param groupId The groupId of the game to get.
     * @param id      The id of the game to get.
     */
    public List<Game> getGameVersions(String groupId, String id) {
        return games.stream()
                .filter(game -> {
                    GameIdentity identity = game.getIdentity();
                    return identity.groupId.equals(groupId) &&
                            identity.id.equals(id);
                })
                .collect(Collectors.toList());
    }

    /**
     * Checks if the game from its identity
     */
    public boolean isLoaded(GameIdentity gameIdentity) {
        return getGame(gameIdentity).isPresent();
    }

    /**
     * Checks if the game is loaded.
     */
    public boolean isLoaded(Game game) {
        return games.contains(game);
    }

    /**
     * Checks if the found file can be loaded or not. Checks the contents of the jar file.
     */
    public boolean isLoadable(File file) {
        JarFile jar;
        try {
            jar = new JarFile(file);
        } catch (IOException ignored) {
            return false;
        }
        return GameNeed.GAME_YAML.getValues()
                .stream()
                .anyMatch(value -> jar.getJarEntry(value) != null);
    }

    /**
     * Loads all files inside a file (if directory recall himself while find a file).
     */
    public void load(File file) throws Exception {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File sub : file.listFiles(pathname ->
                    !pathname.isDirectory() && FilenameUtils.getExtension(pathname.getName()).equals("jar")))
                load(sub);
            return;
        }
        games.add(GameLoader.load(file));
    }

    public void load() throws Exception {
        load(GAMES_FOLDER);
    }

    /**
     * Creates a new looper for that game and sets game's looper field to the created one.
     */
    public GameLooper detachLooper(Game game) {
        return GameLooper.detach(game);
    }

    private void delete(File file) {
        if (file.isDirectory()) {
            for (File sub : file.listFiles())
                delete(sub);
            file.delete();
        }
        file.delete();
    }

    /**
     * Creates graphic engine launcher directory if doesn't exist.
     */
    public LauncherControlPanel createResources() {
        GAMES_FOLDER.mkdirs();
        return this;
    }

    public static LauncherControlPanel g() {
        return $;
    }
}