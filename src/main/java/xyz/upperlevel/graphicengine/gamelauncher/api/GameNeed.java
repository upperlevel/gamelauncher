package xyz.upperlevel.graphicengine.gamelauncher.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GameNeed {

	/**
	 * The game yaml is a file that describe the game its self.
	 * It's a must in each game.
	 */
	public static final GameNeed GAME_YAML = new GameNeed("game.yaml", "game.yml");

	/**
	 * The game main is the main class of the game. It must extends
	 * Game interface.
	 */
	public static final GameNeed GAME_MAIN = new GameNeed("main");


	/**
	 * The game group id is the domain of the developers of the game.
	 * Should be unique for each group.
	 */
	public static final GameNeed GAME_GROUPID = new GameNeed("groupId");

	/**
	 * The game id is the id of the game. Must be unique for each game.
	 */
	public static final GameNeed GAME_ID = new GameNeed("id");

	/**
	 * The game version is the version of the game.
	 */
	public static final GameNeed GAME_VERSION = new GameNeed("version");

	/**
	 * The game name is the display name of the game. This will be shown
	 * into the graphic engine gui as game name.
	 */
	public static final GameNeed GAME_DISPLAYNAME = new GameNeed("displayName");

	public final List<String> values;

	private GameNeed(String... fields) {
		if (fields.length == 0)
			throw new IllegalArgumentException("Cannot create a GameNeed with any value");
		values = Collections.unmodifiableList(Arrays.asList(fields));
	}

	/**
	 * Gets the first value.
	 */
	public String getOnly() {
		return values.size() > 0 ? values.get(0) : null;
	}

	/**
	 * Returns an unmodifiable list of all values this field have.
	 */
	public List<String> getValues() {
		return values;
	}
}